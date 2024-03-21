import javax.xml.ws.Endpoint;
import service.broker.WebBrokerService;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        for (String s : args){
            linkedList.add(s);
        }
        Endpoint.publish("http://0.0.0.0:9000/broker", new WebBrokerService(linkedList));
    }
}
