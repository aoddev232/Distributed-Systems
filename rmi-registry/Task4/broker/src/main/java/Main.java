import service.core.BrokerService;
import service.broker.RemoteBrokerService;
import service.core.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Main {

	public static void main(String[] args) {
		
		BrokerService bkrService = new RemoteBrokerService();
		try {
			// Connect to the RMI Registry - creating the registry will be the 
			// responsibility of the broker
			Registry registry = null;
            if (args.length == 0) {
                registry = LocateRegistry.createRegistry(1099);
            } else {
                registry = LocateRegistry.getRegistry(args[0], 1099);
            }

			// Create the Remote Object
			BrokerService brokerService = (BrokerService) 
				UnicastRemoteObject.exportObject(bkrService, 0);

			// Register the object with the RMI Registry
			registry.bind(Constants.BROKER_SERVICE, brokerService);

			System.out.println("STOPPING SERVER SHUTDOWN");

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("Trouble: " + e);

		}

	}

}