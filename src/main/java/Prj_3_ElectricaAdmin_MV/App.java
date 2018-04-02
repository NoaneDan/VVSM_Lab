package Prj_3_ElectricaAdmin_MV;

import Prj_3_ElectricaAdmin_MV.controller.ClientController;
import Prj_3_ElectricaAdmin_MV.repository.DataManager;
import Prj_3_ElectricaAdmin_MV.ui.ElectricaUI;

public class App {
	public static void main(String[] args) {

		DataManager dataManager = new DataManager();
		ClientController ctrl = new ClientController(dataManager);
		ElectricaUI console = new ElectricaUI(ctrl);

		console.Run();
	}
}
