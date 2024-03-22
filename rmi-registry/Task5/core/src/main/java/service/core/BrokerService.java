package service.core;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * Interface for defining the behaviours of the broker service
 * @author Rem
 *
 */
public interface BrokerService extends Remote {
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException;
	void registerService(String name, Remote service) throws java.rmi.RemoteException;
}
