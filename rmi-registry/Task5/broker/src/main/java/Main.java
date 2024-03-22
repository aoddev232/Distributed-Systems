import service.core.BrokerService;
import service.broker.RemoteBrokerService;
import service.core.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Main {

	public static void main(String[] args) {

		try {
			Registry registry = args.length == 0 ? LocateRegistry.createRegistry(1099) : LocateRegistry.getRegistry(args[0], 1099);
			BrokerService brokerService = new RemoteBrokerService(registry);

			BrokerService stub = (BrokerService) UnicastRemoteObject.exportObject(brokerService, 0);
			registry.bind(Constants.BROKER_SERVICE, stub);

			System.out.println("STOPPING SERVER SHUTDOWN");

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("Trouble: " + e);

		}

	}

}