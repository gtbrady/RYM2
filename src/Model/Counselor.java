package Model;

import DAO.DBCounselor;

import java.util.Objects;

public class Counselor {
    int counselorID;
    String counselorName;
    String counselorPhone;
    String counselorUsername;
    String counselorEmail;

    public Counselor(int counselorID, String counselorName, String counselorPhone, String counselorUsername, String counselorEmail) {
        this.counselorID = counselorID;
        this.counselorName = counselorName;
        this.counselorPhone = counselorPhone;
        this.counselorUsername = counselorUsername;
        this.counselorEmail = counselorEmail;
    }

    public Counselor(int counselorID) {

        for(Counselor c: DBCounselor.getCounselors()) {
            if(counselorID == c.getCounselorID()) {
                this.counselorID = counselorID;
                this.counselorName = c.getCounselorName();
                this.counselorPhone = c.getCounselorPhone();
                this.counselorUsername = c.getCounselorUsername();
                this.counselorEmail = c.getCounselorEmail();
                break;
            }
            else {
                this.counselorID = counselorID;
                this.counselorName = "counselorName";
                this.counselorPhone = "counselorPhone";
                this.counselorUsername = "counselorUsername";
                this.counselorEmail = "counselorEmail";
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

    @Override
    public String toString() {
        return this.counselorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counselor counselor = (Counselor) o;
        return getCounselorName().equals(counselor.getCounselorName()) && getCounselorPhone().equals(counselor.getCounselorPhone()) && getCounselorUsername().equals(counselor.getCounselorUsername()) && getCounselorEmail().equals(counselor.getCounselorEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCounselorName(), getCounselorPhone(), getCounselorUsername(), getCounselorEmail());
    }
}
