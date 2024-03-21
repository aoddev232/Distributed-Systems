package service.broker;

import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
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

	private Map<String, String> quoteURLs = new HashMap<>();

	private Map<String, String> getQuoteURLs(){return quoteURLs;}

	public WebBrokerService(){discoverServices();};

	@WebMethod
	public LinkedList<Quotation> getQuotations(ClientInfo info){
		LinkedList<Quotation> output = new LinkedList<>();
		for(Map.Entry<String, String> entry : getQuoteURLs().entrySet()) {
            String url = entry.getValue();
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

	private void discoverServices() {
	try {
		JmDNS Jm_DNS = JmDNS.create(InetAddress.getLocalHost());
		Jm_DNS.addServiceListener("_quote._tcp.local.", new ServiceListener() {
			
		@Override
		public void serviceAdded(ServiceEvent serviceEvent) {
		}

		@Override
		public void serviceRemoved(ServiceEvent serviceEvent) {
			String serviceName = serviceEvent.getInfo().getName();
			quoteURLs.remove(serviceName);
			System.out.println("Service: " + serviceName + " removed");

		}

		@Override
		public void serviceResolved(ServiceEvent serviceEvent) {
			String url = "http://" + serviceEvent.getInfo().getHostAddresses()[0] + ":" + serviceEvent.getInfo().getPort() + "/quotations";
			String serviceName = serviceEvent.getInfo().getName();
			quoteURLs.put(serviceName ,url);
			System.out.println("Service " + serviceName + " added at " + url);
		}
		});
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}