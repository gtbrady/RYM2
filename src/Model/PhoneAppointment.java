package Model;

public class PhoneAppointment extends nAppointment{

    String counselorPhone;
    String clientPhone;

    public PhoneAppointment(int appointmentID, int counselorID, String counselorName, int clientID,
                            String clientName, AppointmentType type, String description,
                            String startTime, String endTime, String coPhone, String clPhone) {
        super(appointmentID, counselorID, counselorName, clientID, clientName, type, description,
                startTime, endTime);
        this.counselorPhone = coPhone;
        this.clientPhone = clPhone;
    }


    public String getCounselorPhone() {
        return counselorPhone;
    }

    public void setCounselorPhone(String counselorPhone) {
        this.counselorPhone = counselorPhone;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @Override
    public String toString() {
        return "PhoneAppointment{" +
                "counselorPhone='" + counselorPhone + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", appointmentID=" + appointmentID +
                ", counselorID=" + counselorID +
                ", counselorName='" + counselorName + '\'' +
                ", clientID=" + clientID +
                ", clientName='" + clientName + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
