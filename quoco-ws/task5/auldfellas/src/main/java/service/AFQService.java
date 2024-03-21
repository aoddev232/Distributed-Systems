package service;

import java.io.IOException;
import java.net.InetAddress;
import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */
@WebService(name="QuotationService",
		targetNamespace="http://core.service/",
		serviceName="QuotationService")
@SOAPBinding(style = SOAPBinding.Style.RPC, use=SOAPBinding.Use.LITERAL)
public class AFQService extends AbstractQuotationService {
	// All references are to be prefixed with an AF (e.g. AF001000)
	public static final String PREFIX = "AF";
	public static final String COMPANY = "Auld Fellas Ltd.";

	/**
	 * Quote generation:
	 * 30% discount for being male
	 * 20% increase for being female
	 * 10% discount for males over 50 
	 * additional 10% discount for males over 60
	 */

	@Override
	@WebMethod
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1200
		double price = generatePrice(600, 600);
		
		int discount = 0;
		if (info.gender == ClientInfo.MALE) {
			discount = 30;
			if (info.age > 50) discount += 10;
			if (info.age > 60) discount += 10;
		} else {
			discount = -20;
		}

		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	public void advertiseService() {
	try {
		JmDNS Jm_DNS = JmDNS.create(InetAddress.getLocalHost());
		ServiceInfo service_info = ServiceInfo.create("_quote._tcp.local.", "auldfellas", 9001, "path=/quotations");
		Jm_DNS.registerService(service_info);
		System.out.println(service_info.getName() + " advertised on" + service_info.getPort());
	} catch (IOException e) {
		throw new RuntimeException(e);
	}
    }
}
