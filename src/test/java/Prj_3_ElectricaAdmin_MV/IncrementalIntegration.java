package Prj_3_ElectricaAdmin_MV;

import Prj_3_ElectricaAdmin_MV.controller.ClientController;
import Prj_3_ElectricaAdmin_MV.model.Client;
import Prj_3_ElectricaAdmin_MV.model.Issue;
import Prj_3_ElectricaAdmin_MV.repository.DataManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

public class IncrementalIntegration extends TestCase {

    private ClientController controller;
    private DataManager dataManager;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IncrementalIntegration( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( IncrementalIntegration.class );
    }

    public void setUp() throws Exception {
        super.setUp();


        dataManager = new DataManager("testClient.txt", "testIssue.txt");
        controller = new ClientController(dataManager);
    }

    public void tearDown() throws Exception {
        super.tearDown();

        File clientFile = new File("testClient.txt");
        File issueFile = new File("testIssue.txt");

        if (clientFile.exists()) {
            clientFile.delete();
        }
        if (issueFile.exists()) {
            issueFile.delete();
        }
    }

    public void testA() {

        String result = controller.AddClient("ion", "8, Alee, Cluj", "0");

        assertNull(result);
        assertEquals(dataManager.Clients.size(), 1);

        Client client = dataManager.Clients.get(dataManager.Clients.size() - 1);
        assertEquals(client.idClient, "0");
        assertEquals(client.Name, "ion");
        assertEquals(client.Address, "8, Alee, Cluj");
    }

    public void testB() {

        controller.AddClient("ion", "8, Alee, Cluj", "0");
        Client c = new Client("ion", "8, Alee, Cluj", "0");

        String result1 = controller.AddClientIndex(c, 2018, 5, 200);
        String result2 = controller.AddClientIndex(c, 2018, 4, 200);

        assertNull(result1);
        assertNull(result2);
        assertEquals(dataManager.Issues.size(), 2);

        Issue i = dataManager.Issues.get(dataManager.Issues.size() - 1);
        assertEquals(i.Year, 2018);
        assertEquals(i.Month, 4);
        assertEquals((double) i.ToPay, (double) 200);
        assertEquals(i.Client, c);
    }

    public void testC() {

        Client c = new Client("ion", "8, Alee, Cluj", "0");

        controller.AddClient("ion", "8, Alee, Cluj", "0");
        controller.AddClientIndex(c, 2018, 5, 200);
        controller.AddClientIndex(c, 2018, 4, 200);

        String result = controller.ListIssue(c);

        assertNotNull(result);
    }

    public void testAB() {

        testA();
        testB();
        assertTrue(true);
    }

    public void testABC() {

        testA();
        testB();
        testC();
        assertTrue(true);
    }
}
