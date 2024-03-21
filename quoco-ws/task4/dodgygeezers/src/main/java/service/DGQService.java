package service;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Implementation of Quotation Service for Dodgy Geezers Insurance Company
 *  
 * @author Rem
 *
 */
@WebService(name="QuotationService",
		targetNamespace="http://core.service/",
		serviceName="QuotationService")
@SOAPBinding(style = SOAPBinding.Style.RPC, use=SOAPBinding.Use.LITERAL)
public class DGQService extends AbstractQuotationService {
	// All references are to be prefixed with an DG (e.g. DG001000)
	public static final String PREFIX = "DG";
	public static final String COMPANY = "Dodgy Geezers Corp.";
	
	/**
	 * Quote generation:
	 * 10% discount for smoking
	 * up to 20% discount for high BMI
	 * no penality for medical issues
	 */
	@Override
	@WebMethod
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 800 and 1000
		double price = generatePrice(800, 200);
		
		int discount = bmiDiscount(info);	
		if (info.smoker) discount += 10;
	
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}


	public int bmiDiscount(ClientInfo info) {
		double bmi = this.bmi(info.weight, info.height);
		if (bmi < 18.5) return 0;
		if (bmi < 24.5) return 5;
		if (bmi < 30) return 10;
		if (bmi < 40) return 15;
		return 20;
	}
}
