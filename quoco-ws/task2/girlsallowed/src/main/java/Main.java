import javax.xml.ws.Endpoint;

import service.girlsallowed.GAQService;
public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://0.0.0.0:9003/quotation", new GAQService());
    }
   }