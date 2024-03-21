import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.junit.BeforeClass;
import org.junit.Test;
import service.DGQService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QuotationServiceUnitTest {
    @BeforeClass
    public static void setup() {
        Endpoint.publish("http://0.0.0.0:9001/quotation", new DGQService());
    }
    @Test
    public void connectionTest() throws Exception {
        URL wsdlUrl = new URL("http://localhost:9001/quotation?wsdl");
        QName serviceName =
                new QName("http://core.service/", "QuotationService");
        Service service = Service.create(wsdlUrl, serviceName);
        QName portName =
                new QName("http://core.service/", "QuotationServicePort");
        QuotationService quotationService =
                service.getPort(portName, QuotationService.class);
        Quotation quotation = quotationService
                .generateQuotation(new ClientInfo( 
                        "Alex O'Donnell", ClientInfo.MALE, 22,
                        1.95, 80, false, false));
        assertNotNull(quotation);
        assertEquals("DG", quotation.reference.substring(0,2));
    }
}