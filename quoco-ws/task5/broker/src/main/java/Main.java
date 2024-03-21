import javax.xml.ws.Endpoint;
import service.broker.WebBrokerService;

public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://0.0.0.0:9000/broker", new WebBrokerService());
    }
}
