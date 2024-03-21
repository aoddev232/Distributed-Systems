package service.core;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface to define the behaviour of a quotation service.
 *
 * @author Rem
 *
 */
@WebService
public interface QuotationService {
  @WebMethod
  public Quotation generateQuotation(ClientInfo info) throws RemoteException;
}
