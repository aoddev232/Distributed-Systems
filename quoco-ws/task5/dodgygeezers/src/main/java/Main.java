import javax.xml.ws.Endpoint;
import service.DGQService;

public class Main {
    public static void main(String[] args) {
        DGQService DGQService = new DGQService();
        Endpoint.publish("http://0.0.0.0:9002/quotation", DGQService);
        DGQService.advertiseService();
    }
}