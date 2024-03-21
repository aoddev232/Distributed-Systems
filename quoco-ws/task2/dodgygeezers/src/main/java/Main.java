import javax.xml.ws.Endpoint;

import service.dodgygeezers.DGQService;

public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://0.0.0.0:9002/quotation", new DGQService());
    }
   }