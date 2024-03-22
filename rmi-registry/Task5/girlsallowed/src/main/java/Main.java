import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import service.girlsallowed.GAQService;
import service.core.QuotationService;
import service.core.BrokerService;
import service.core.Constants;

public class Main {
    public static void main(String[] args) {
		QuotationService gaqService = new GAQService();
		try {
			Registry registry = LocateRegistry.getRegistry("broker", 1099);

			QuotationService stub = (QuotationService) UnicastRemoteObject.exportObject(gaqService, 0);

			BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
			brokerService.registerService(Constants.GIRLS_ALLOWED_SERVICE, stub);

			System.out.println("STOPPING SERVER SHUTDOWN");

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("Trouble: " + e);

		}

	}

}