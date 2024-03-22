import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.junit.*;
import service.broker.RemoteBrokerService;
import service.core.*;

public class RemoteBrokerServiceUnitTest {

  private static Registry registry;

  @BeforeClass
  public static void setup() {
    try {
      registry = LocateRegistry.createRegistry(1099);
      BrokerService bkrService = new RemoteBrokerService();
      registry.bind(Constants.BROKER_SERVICE, bkrService);
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }

  @Test
  public void connectionTest() throws NotBoundException, RemoteException {
    BrokerService service = (BrokerService) registry.lookup(
      Constants.BROKER_SERVICE
    );
    assertNotNull("BrokerService should not be null", service);
  }

  @Test
  public void getQuotationsTest() throws Exception {
    BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
    ClientInfo info = new ClientInfo(
      "Alex O'Donnell",
      ClientInfo.MALE,
      21,
      1.904,
      75,
      false,
      false
    );
    List<Quotation> quotations = brokerService.getQuotations(info);
    assertNotNull("The list of quotations should not be null", quotations);
    assertTrue(
      "The list of quotations should be empty if no services are registered.",
      quotations.isEmpty()
    );
  }

  @AfterClass
  public static void tearDown() throws Exception {
    registry = null;
    System.gc();
  }
}