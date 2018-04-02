package Prj_3_ElectricaAdmin_MV;

import Prj_3_ElectricaAdmin_MV.controller.ClientController;
import Prj_3_ElectricaAdmin_MV.ui.ElectricaUI;

public class App {
	public static void main(String[] args) {

		ClientController ctrl = new ClientController();
		ElectricaUI console = new ElectricaUI(ctrl);

		console.Run();
	}
}
