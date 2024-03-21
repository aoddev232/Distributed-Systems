package service.broker;

import java.net.URL;
import java.util.LinkedList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

@WebService(name="BrokerService",
		targetNamespace="http://core.service/",
		serviceName="BrokerService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL)
public class WebBrokerService implements BrokerService {

	public LinkedList<String> urls = new LinkedList<>();

	public WebBrokerService(LinkedList<String> urls){
		this.urls = urls;
	}

	public WebBrokerService(){};


	@WebMethod
	public LinkedList<Quotation> getQuotations(ClientInfo info){
		LinkedList<Quotation> output = new LinkedList<>();
		for(String url: urls) {
			try{
				URL wsdlUrl = new URL(url);
				QName serviceName = new QName("http://core.service/", "QuotationService");
				Service service = Service.create(wsdlUrl, serviceName);
				QName portName = new QName("http://core.service/", "QuotationServicePort");
				QuotationService quotationService = service.getPort(portName, QuotationService.class);
				Quotation quotation = quotationService.generateQuotation(info);
				output.add(quotation);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return output;
	}
}