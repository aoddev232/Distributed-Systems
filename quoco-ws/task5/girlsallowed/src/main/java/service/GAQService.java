package service;

import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

/**
 * Implementation of the Girls Aloud insurance quotation service.
 * 
 * @author Rem
 *
 */
@WebService(name="QuotationService",
		targetNamespace="http://core.service/",
		serviceName="QuotationService")
@SOAPBinding(style = SOAPBinding.Style.RPC, use=SOAPBinding.Use.LITERAL)
public class GAQService extends AbstractQuotationService {
	// All references are to be prefixed with an GA (e.g. GA001000)
	public static final String PREFIX = "GA";
	public static final String COMPANY = "Girls Allowed Inc.";
	
	/**
	 * Quote generation:
	 * 50% discount for being female
	 * 30% surcharge for being a mam
	 * upto 20% discount for being low BMI
	 * 20% penalty for high BMI
	 * 20% penalty for Medical Issues
	 * 40% penalty for smoking
	 */
	@Override
	@WebMethod
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1000
		double price = generatePrice(600, 400);
		
		// Automatic 50% discount for being female
		int discount = (info.gender == ClientInfo.FEMALE) ? 50:-30;
		
		// Add a points discount
		discount += bmiDiscount(info);

		// Apply a medical weighting
		discount += medicalWeighting(info);
			
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	public int bmiDiscount(ClientInfo info) {
		double bmi = this.bmi(info.weight, info.height);
		if (bmi < 18.5) return 20;
		if (bmi < 24.5) return 10;
		if (bmi < 30) return 0;
		if (bmi < 40) return -20;
		return 40;
	}

	public int medicalWeighting(ClientInfo info) {
		int weighting = 0;
		if (info.medicalIssues) weighting-=20;
		if (info.smoker) weighting -= 40;
		return weighting;
	}

	public void advertiseService() {
		try {
			JmDNS Jm_DNS = JmDNS.create(InetAddress.getLocalHost());
			ServiceInfo service_info = ServiceInfo.create("_quote._tcp.local.", "girlsallowed", 9003, "path=/quotations");
			Jm_DNS.registerService(service_info);
			System.out.println(service_info.getName() + " advertised on" + service_info.getPort());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
