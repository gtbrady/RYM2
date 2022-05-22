package Model;

public class Suite {
    int suiteID;
    int officeID;
    String suiteName;

    public Suite(int suiteID, int officeID, String suiteName) {
        this.suiteID = suiteID;
        this.officeID = officeID;
        this.suiteName = suiteName;
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
