import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import service.dodgygeezers.DGQService;
import service.core.QuotationService;
import service.core.Constants;
import service.core.BrokerService;

public class Main {

	public static void main(String[] args) {
		
		QuotationService dgqService = new DGQService();
		try {
			Registry registry = LocateRegistry.getRegistry("broker", 1099);

			QuotationService stub = (QuotationService) UnicastRemoteObject.exportObject(dgqService, 0);

			BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
			brokerService.registerService(Constants.DODGY_GEEZERS_SERVICE, stub);
   
			System.out.println("STOPPING SERVER SHUTDOWN");

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("Trouble: " + e);

		}

	}

}