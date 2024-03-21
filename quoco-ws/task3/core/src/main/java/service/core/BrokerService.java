package service.core;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface for defining the behaviours of the broker service
 * @author Rem
 *
 */
@WebService
public interface BrokerService extends java.rmi.Remote {
  @WebMethod
  public List<Quotation> getQuotations(ClientInfo info)
    throws Exception;
}
