package service.broker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

public class RemoteBrokerService implements BrokerService {

  private Registry registry;

    public RemoteBrokerService(Registry registry) {
      this.registry = registry;
    }

  public List<Quotation> getQuotations(ClientInfo clientInfo)
    throws RemoteException {
    try {
      Registry registry = LocateRegistry.getRegistry("broker", 1099);
      List<Quotation> quotations = new LinkedList<>();
      for (String name : registry.list()) {
        if (name.startsWith("qs-")) {
          QuotationService service = (QuotationService) registry.lookup(name);
          quotations.add(service.generateQuotation(clientInfo));
        }
      }
      return quotations;
    } catch (Exception e) {
      System.out.println("Error: " + e);
      return null;
    }
  }

  public void registerService(String name, Remote service)
    throws RemoteException {
    try {
      registry.bind(name, service);
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }
}