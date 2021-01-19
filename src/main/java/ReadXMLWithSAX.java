import java.util.*;

public class ReadXMLWithSAX {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

        String fileName = "D:\\SAXParserXML\\src\\main\\resources\\customers.xml";

        SAXCustomerHandler saxCustomerHandler = new SAXCustomerHandler();
        List<Customer> data = saxCustomerHandler.readDataFromXML(fileName);
        System.out.println("Number customer: " + data.size());
        for (Customer customer : data) {
            System.out.println(customer);
        }
    }
}
