package Model;

import DAO.DBOffice;

public class Office {
    int officeID;
    String buildingName;
    String buildingAddress;
    String city;
    String state;
    String zip;

    public Office(int officeID, String buildingName, String buildingAddress, String city, String state, String zip) {
        this.officeID = officeID;
        this.buildingName = buildingName;
        this.buildingAddress = buildingAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    public Office(String name) {
        for(Office o : DBOffice.getOffices()) {
            if(name.equals(o.getBuildingName())) {
            this.officeID = o.getOfficeID();
            this.buildingName = name;
            this.buildingAddress = o.getBuildingAddress();
            this.city = o.getCity();
            this.state = o.getState();
            this.zip = o.getZip();
            break;
            }
            else {
                this.officeID = -1;
                this.buildingName = name;
                this.buildingAddress = "buildingAddress";
                this.city = "city";
                this.state = "state";
                this.zip = "zip";
            }
        }

    }

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Office{" +
                "officeID=" + officeID +
                ", buildingName='" + buildingName + '\'' +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
