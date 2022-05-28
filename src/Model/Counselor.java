package Model;

import DAO.DBCounselor;

import java.util.Objects;

public class Counselor {
    int counselorID;
    String counselorName;
    String counselorPhone;
    String counselorUsername;
    String counselorEmail;

    int officeID;

    int suiteID;


    public Counselor(int counselorID, String counselorName, String counselorPhone, String counselorUsername, String counselorEmail, int officeID, int suiteID) {
        this.counselorID = counselorID;
        this.counselorName = counselorName;
        this.counselorPhone = counselorPhone;
        this.counselorUsername = counselorUsername;
        this.counselorEmail = counselorEmail;
        this.officeID = officeID;
        this.suiteID = suiteID;
    }

    public Counselor(int counselorID) {

        for(Counselor c: DBCounselor.getCounselors()) {
            if(counselorID == c.getCounselorID()) {
                this.counselorID = counselorID;
                this.counselorName = c.getCounselorName();
                this.counselorPhone = c.getCounselorPhone();
                this.counselorUsername = c.getCounselorUsername();
                this.counselorEmail = c.getCounselorEmail();
                this.officeID = c.getOfficeID();
                this.suiteID = c.getSuiteID();
                break;
            }
            else {
                this.counselorID = counselorID;
                this.counselorName = "counselorName";
                this.counselorPhone = "counselorPhone";
                this.counselorUsername = "counselorUsername";
                this.counselorEmail = "counselorEmail";
                this.officeID = -1;
                this.suiteID = -1;
            }
        }
    }

     public int getCounselorID() {
        return counselorID;
    }

    public void setCounselorID(int counselorID) {
        this.counselorID = counselorID;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public String getCounselorPhone() {
        return counselorPhone;
    }

    public void setCounselorPhone(String counselorPhone) {
        this.counselorPhone = counselorPhone;
    }

    public String getCounselorUsername() {
        return counselorUsername;
    }

    public void setCounselorUsername(String counselorUsername) {
        this.counselorUsername = counselorUsername;
    }

    public String getCounselorEmail() {
        return counselorEmail;
    }

    public void setCounselorEmail(String counselorEmail) {
        this.counselorEmail = counselorEmail;
    }

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public int getSuiteID() {
        return suiteID;
    }

    public void setSuiteID(int suiteID) {
        this.suiteID = suiteID;
    }

    @Override
    public String toString() {
        return getCounselorName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counselor counselor = (Counselor) o;
        return getOfficeID() == counselor.getOfficeID() && getSuiteID() == counselor.getSuiteID() && Objects.equals(getCounselorName(), counselor.getCounselorName()) && Objects.equals(getCounselorPhone(), counselor.getCounselorPhone()) && Objects.equals(getCounselorUsername(), counselor.getCounselorUsername()) && Objects.equals(getCounselorEmail(), counselor.getCounselorEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCounselorName(), getCounselorPhone(), getCounselorUsername(), getCounselorEmail(), getOfficeID(), getSuiteID());
    }
}
