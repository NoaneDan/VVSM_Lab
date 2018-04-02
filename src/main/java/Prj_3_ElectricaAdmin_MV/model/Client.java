package Prj_3_ElectricaAdmin_MV.model;

import java.util.Objects;

public class Client {
	public String Name;
    public String Address;
    public String idClient;
    
    Client() {
        this.Name = "";
        this.Address = "";
        this.idClient="";
    }
    
    public Client(Client client) {
        this.Name = client.Name;
        this.Address = client.Address;
        this.idClient=client.idClient;       		
    }
    
    public Client(String name, String address, String idC) {
        this.Name = name;
        this.Address = address;
        this.idClient=idC;
    }
    
    @Override
    public String toString(){
        return String.format("%s, %s, %s", this.Name, this.Address, this.idClient);
    }
    
    @Override
    public boolean equals(Object object){

        if (object == null) {
            return false;
        }

        if (!(object instanceof Client)) {
            return false;
        }

        Client c = (Client) object;

        return (c.idClient.equals(this.idClient));

    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.Name);
        hash = 59 * hash + Objects.hashCode(this.Address);
        return hash;
    }
}
