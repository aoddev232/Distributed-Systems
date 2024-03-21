package service.core;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface to define the behaviour of a quotation service.
 *
 * @author Rem
 *
 */
@WebService
public interface QuotationService extends java.rmi.Remote {
  @WebMethod
  public Quotation generateQuotation(ClientInfo info)
    throws Exception;
}
