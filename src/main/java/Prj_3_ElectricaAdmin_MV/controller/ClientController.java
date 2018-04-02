package Prj_3_ElectricaAdmin_MV.controller;

import Prj_3_ElectricaAdmin_MV.model.Client;
import Prj_3_ElectricaAdmin_MV.model.Issue;
import Prj_3_ElectricaAdmin_MV.repository.DataManager;


public class ClientController {

    private DataManager _dataManager;
    
    public ClientController(DataManager _dataManager){

        this._dataManager = _dataManager;
    }
    
    private String ValidateClient(String name, String address, String id){

        if(!name.equals("") && !address.equals("") && !id.equals("")){
            if (name.length() > 256) {
                return "Name length is over 256 characters!";
            }

            if (!address.matches("(\\d)+,(\\s)?(\\w)+,(\\s)?(\\w)+")) {
                return "Invalid address!";
            }

            for(int i=0; i<name.length(); i++){
                if(!Character.isUpperCase(name.charAt(i)) && !Character.isLowerCase(name.charAt(i)) && !Character.isSpaceChar(name.charAt(i))) {
                    return "Invalid character: " + name.charAt(i);
                }
            }

            return null;
        }
        else {
            return "Name, address or id cannot be empty!";
        }
    }
    
    public String AddClient(String name, String address, String id){

        //validation
        String valid;
        if((valid = ValidateClient(name, address,id)) != null){
            return valid;
        }

        Client c = new Client(name, address,id);

        //uniqueness
        for(int j=0; j < _dataManager.Clients.size(); j++){
            if(_dataManager.Clients.get(j).equals(c)){
                return "Client already exists!";
            }
        }

        try {
            _dataManager.Clients.add(c);
            _dataManager.SaveChanges();
            return null;
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
    
    public String AddClientIndex(Client c, int year, int month, float toPay){

        if(year > 0){
            if(month > 0){
                if(toPay >= 0){

                    //validate client attributes
                    String valid;

                    if((valid = ValidateClient(c.Name, c.Address, c.idClient)) == null){
                        //check if client exist
                        Boolean exist = false;

                        for(int i=0; i<_dataManager.Clients.size(); i++){
                            if(_dataManager.Clients.get(i).equals(c)){
                                exist = true;
                                break;
                            }
                        }

                        if(exist) {
                            Issue i = new Issue(c, year, month, toPay, 0);

                            //uniqueness
                            for(int j=0; j<_dataManager.Issues.size(); j++){
                                if(_dataManager.Issues.get(j).equals(i)){
                                    return "Monthly index already exists!";
                                }
                            }
                        
                            _dataManager.Issues.add(i);
                            _dataManager.SaveChanges();

                            return null;
                        }
                        else {
                            return "Client does not exist!";
                        }
                    }
                    else {
                        return valid;
                    }
                }
                else {
                    return "Money to pay can't be less than 0!";
                }
            }
            else {
                return "Month can't be 0 or less!";
            }
        }
        else {
            return "Year can't be 0 or less!";
        }
    }
    
    public String ListIssue(Client c){

        StringBuilder s = new StringBuilder();
        String pen = "";

        Double total = 0.0;

        for(int i=0; i<_dataManager.Issues.size(); i++){
            Issue issue = _dataManager.Issues.get(i);

            if(issue.Client.equals(c)){
            	 pen += String.format("Year: %d, Month: %d, To pay: %2.0f, Paid: %2.0f\n", issue.Year, issue.Month, issue.ToPay, issue.Paid);
            	 s.append(pen);

            	 total += issue.ToPay - issue.Paid;
        	}            
        }

        s.append(String.format("Total left to pay: %2.0f", total));

        return s.toString();
    }
    
}
