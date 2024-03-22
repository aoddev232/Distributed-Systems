import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.broker.RemoteBrokerService;
import service.core.*;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.junit.Assert.*;

public class RemoteBrokerServiceUnitTest {

    private static Registry registry;

    @BeforeClass
    public static void setup() {



        try {
            registry = LocateRegistry.createRegistry(1099);
            BrokerService brokerService = new RemoteBrokerService(registry);
            BrokerService stub = (BrokerService) UnicastRemoteObject.exportObject(brokerService, 0);
            registry.bind(Constants.BROKER_SERVICE, stub);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Test
    public void connectionTest() throws NotBoundException, RemoteException {
        BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
        assertNotNull("BrokerService should not be null", service);
    }



    @Test
    public void getQuotationsTest () throws Exception {

        Quotation testQuote = new Quotation("TestCompany", "123", 111);

        class TestBrokerService extends AbstractQuotationService {
            public Quotation generateQuotation(ClientInfo info)  {
                return testQuote;
            }
        }

        QuotationService testQuoteService = new TestBrokerService();
        QuotationService testStub = (QuotationService) UnicastRemoteObject.exportObject(testQuoteService, 0);

        try {
            registry.bind("TestCompany", testStub);
            BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
            ClientInfo info = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false);
            List<Quotation> quotes = service.getQuotations(info);
            Quotation testQuotation = quotes.get(0);

            assertEquals("TestCompany", testQuotation.company);
            assertEquals("123", testQuotation.reference);
            assertEquals(111, testQuotation.price, 0.01);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}
