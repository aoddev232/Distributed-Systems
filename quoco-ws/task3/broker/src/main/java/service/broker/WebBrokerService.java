package service.broker;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@WebService(name="BrokerService",
		targetNamespace="http://core.service/",
		serviceName="BrokerService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class WebBrokerService implements BrokerService {
	public LinkedList<String> urls = null;
	public WebBrokerService(LinkedList<String> urls){
		this.urls = urls;
	}

	public WebBrokerService(){};

	@WebMethod
	public List<Quotation> getQuotations(ClientInfo info) throws Exception{
		LinkedList<Quotation> output = new LinkedList<>();
		for(String url: urls) {
			URL wsdlUrl = new URL(url);
			QName serviceName = new QName("http://core.service/", "QuotationService");
			Service service = Service.create(wsdlUrl, serviceName);
			QName portName = new QName("http://core.service/", "QuotationServicePort");
			QuotationService quotationService = service.getPort(portName, QuotationService.class);
			Quotation quotation = quotationService.generateQuotation(info);
			output.add(quotation);
		}
		return output;
	}
}
// mvn compile exec:java -pl broker "-Dexec.args=http://0.0.0.0:9001/quotations http://0.0.0.0:9002/quotations http://0.0.0.0:9003/quotations"