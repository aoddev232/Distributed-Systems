import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import service.auldfellas.AFQService;
import service.core.QuotationService;
import service.core.BrokerService;
import service.core.Constants;

public class Main {
    public static void main(String[] args) {
		QuotationService afqService = new AFQService();
		try {
			Registry registry = LocateRegistry.getRegistry("broker", 1099);

			QuotationService stub = (QuotationService) UnicastRemoteObject.exportObject(afqService, 0);

			BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
			brokerService.registerService(Constants.AULD_FELLAS_SERVICE, stub);


			System.out.println("STOPPING SERVER SHUTDOWN");

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("Trouble: " + e);

		}
    }
}