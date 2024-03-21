import javax.xml.ws.Endpoint;
import service.AFQService;

public class Main {
    public static void main(String[] args) {
        AFQService AFQService = new AFQService();
        Endpoint.publish("http://0.0.0.0:9001/quotation", AFQService);
        AFQService.advertiseService();
    }
}