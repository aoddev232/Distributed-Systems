package service.broker;

import java.util.LinkedList;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {

	private Registry registry;
  
	public LocalBrokerService() {
	  try {
		// Assuming the RMI registry is running on the local host and default port
		registry = LocateRegistry.getRegistry();
	  } catch (RemoteException e) {
		e.printStackTrace();
	  }
	}
  
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
	  List<Quotation> quotations = new LinkedList<Quotation>();
  
	  try {
		for (String name : registry.list()) {
		  if (name.startsWith("qs-")) {
			QuotationService service = (QuotationService) registry.lookup(name);
			quotations.add(service.generateQuotation(info));
		  }
		}
	  } catch (NotBoundException e) {
		e.printStackTrace();
	  }
  
	  return quotations;
	}
  }
