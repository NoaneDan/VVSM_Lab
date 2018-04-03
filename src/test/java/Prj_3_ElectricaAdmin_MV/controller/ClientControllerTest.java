package Prj_3_ElectricaAdmin_MV.controller;

import Prj_3_ElectricaAdmin_MV.model.Client;
import Prj_3_ElectricaAdmin_MV.model.Issue;
import Prj_3_ElectricaAdmin_MV.repository.DataManager;
import junit.framework.TestCase;

import java.io.File;

public class ClientControllerTest extends TestCase {

    private ClientController controller;
    private DataManager dataManager;

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

    public void testAddClient() {

        String result = controller.AddClient("ion", "8, Alee, Cluj", "0");

        assertNull(result);
        assertEquals(dataManager.Clients.size(), 1);

        Client client = dataManager.Clients.get(dataManager.Clients.size() - 1);
        assertEquals(client.idClient, "0");
        assertEquals(client.Name, "ion");
        assertEquals(client.Address, "8, Alee, Cluj");
    }

    public void testAddClient_NoName() {

        String result = controller.AddClient("", "8, Alee, Cluj", "0");

        assertNotNull(result);
        assertEquals(dataManager.Clients.size(), 0);
    }

    public void testAddClient_NoAddress() {

        String result = controller.AddClient("ion", "", "0");

        assertNotNull(result);
        assertEquals(dataManager.Clients.size(), 0);
    }

    public void testAddClient_NoId() {

        String result = controller.AddClient("ion", "8, Alee, Cluj", "");

        assertNotNull(result);
        assertEquals(dataManager.Clients.size(), 0);
    }

    public void testAddClient_NameTooLong() {

        String result = controller.AddClient("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "8, Alee, Cluj", "0");

        assertNotNull(result);
        assertEquals(dataManager.Clients.size(), 0);
    }

    public void testAddClient_InvalidAddress() {

        String result = controller.AddClient("ion", "abc", "0");

        assertNotNull(result);
        assertEquals(dataManager.Clients.size(), 0);
    }

    public void testAddClient_SameId() {

        String result1 = controller.AddClient("ion", "8, Alee, Cluj", "0");
        String result2 = controller.AddClient("ghio", "9, Alee, Cluj", "0");

        assertNull(result1);
        assertNotNull(result2);

        assertEquals(dataManager.Clients.size(), 1);
    }

    public void testAddClient_InvalidName() {

        String result = controller.AddClient("2124afasd@!", "", "0");

        assertNotNull(result);
        assertEquals(dataManager.Clients.size(), 0);
    }

    public void testAddIndex() {

        controller.AddClient("ion", "8, Alee, Cluj", "0");
        Client c = new Client("ion", "8, Alee, Cluj", "0");

        String result = controller.AddClientIndex(c, 2018, 4, 200);

        assertNull(result);
        assertEquals(dataManager.Issues.size(), 1);

        Issue i = dataManager.Issues.get(dataManager.Issues.size() - 1);
        assertEquals(i.Year, 2018);
        assertEquals(i.Month, 4);
        assertEquals((double) i.ToPay, (double) 200);
        assertEquals(i.Client, c);
    }

    public void testAddIndex_IndexExists() {

        controller.AddClient("ion", "8, Alee, Cluj", "0");
        Client c = new Client("ion", "8, Alee, Cluj", "0");

        String result1 = controller.AddClientIndex(c, 2018, 4, 200);
        String result2 = controller.AddClientIndex(c, 2018, 4, 200);

        assertNull(result1);
        assertNotNull(result2);
        assertEquals(dataManager.Issues.size(), 1);
    }
}