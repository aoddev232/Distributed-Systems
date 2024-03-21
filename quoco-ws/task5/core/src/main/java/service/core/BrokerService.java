package service.core;

import java.util.LinkedList;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface for defining the behaviours of the broker service
 * @author Rem
 *
 */
@WebService
public interface BrokerService{
  @WebMethod
  public LinkedList<Quotation> getQuotations(ClientInfo info);
}
