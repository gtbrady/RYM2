package Model;

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
        return "Counselor{" +
                "counselorID=" + counselorID +
                ", counselorName='" + counselorName + '\'' +
                ", counselorPhone='" + counselorPhone + '\'' +
                ", counselorUsername='" + counselorUsername + '\'' +
                ", counselorEmail='" + counselorEmail + '\'' +
                '}';
    }
}
