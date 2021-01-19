import org.xml.sax.*;
import org.xml.sax.helpers.*;

import javax.xml.parsers.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

@SuppressWarnings("unused")
public class SAXCustomerHandler extends DefaultHandler {

    private List<Customer> data;
    private Customer customer;
    private String currentElement = "";
    private StringBuilder currentText;
    private static final String XMLDATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public List<Customer> readDataFromXML(String filename) throws ParserConfigurationException, SAXException,
        IOException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newNSInstance();
        saxParserFactory.setNamespaceAware(true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(new File(filename), this);
        return data;
    }

    @Override
    public void startDocument() {

        data = new ArrayList<>();
    }

    @Override
    public void endDocument() {

        System.out.println("End document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        currentElement = localName;
        switch (currentElement) {
            case "customers":
                break;
            case "customer":
                if (uri.equals("http://www.example.org/customers")) {
                    customer = new Customer();
                    String idAsString = attributes.getValue(Customer.ID);
                    customer.setId(Integer.parseInt(idAsString));
                    data.add(customer);
                }
                break;
            default:
                currentText = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (currentElement.equals("customers") || currentElement.equals("customer") || !uri.equals("http://www.example.org/customers")) {
            return;
        }

        String content = currentText.toString();

        switch (currentElement) {
            case Customer.NAME:
                customer.setName(content);
                break;
            case Customer.PHONE:
                customer.setPhone(content);
                break;
            case Customer.ABOUT:
                customer.setAbout(content);
                break;
            case Customer.AGE:
                customer.setAge(Integer.parseInt(content));
                break;
            case Customer.ACTIVE:
                customer.setActive(Boolean.parseBoolean(content));
                break;
            case Customer.BALANCE:
                customer.setBalance(new BigDecimal(content));
                break;
            case Customer.JOINED:
                DateFormat df = new SimpleDateFormat(XMLDATEFORMAT);
                try {
                    customer.setJoined(df.parse(content));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {

        if (currentText != null) {
            currentText.append(ch, start, length);
        }
    }
}
