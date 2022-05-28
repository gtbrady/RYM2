package Model;

import DAO.DBSuite;

public class Suite {
    int suiteID;
    int officeID;
    String suiteName;

    public Suite(int suiteID, int officeID, String suiteName) {
        this.suiteID = suiteID;
        this.officeID = officeID;
        this.suiteName = suiteName;
    }

    public Suite(String name) {
        for(Suite s : DBSuite.getSuites()) {
            if(name.equals(s.getSuiteName())) {
                this.suiteID = s.getSuiteID();
                this.officeID = s.getOfficeID();
                this.suiteName = name;
                break;
            }
            else {
                this.suiteID = -1;
                this.officeID = -1;
                this.suiteName = "suiteName";
            }
        }

    }

    public Suite(int id) {
        for(Suite s : DBSuite.getSuites()) {
            if(id == s.suiteID) {
                this.suiteID = id;
                this.officeID = s.getOfficeID();
                this.suiteName = s.getSuiteName();
                break;
            }
            else {
                this.suiteID = -1;
                this.officeID = -1;
                this.suiteName = "suiteName";
            }
        }

    }

    public int getSuiteID() {
        return suiteID;
    }

    public void setSuiteID(int suiteID) {
        this.suiteID = suiteID;
    }

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    @Override
    public String toString() {
        return "Suite{" +
                "suiteID=" + suiteID +
                ", officeID=" + officeID +
                ", suiteName='" + suiteName + '\'' +
                '}';
    }
}
