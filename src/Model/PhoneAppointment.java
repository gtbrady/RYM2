package Model;

import java.util.Objects;

public class PhoneAppointment extends Appointment {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneAppointment)) return false;
        if (!super.equals(o)) return false;
        PhoneAppointment that = (PhoneAppointment) o;
        return getCounselorPhone().equals(that.getCounselorPhone()) && getClientPhone().equals(that.getClientPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCounselorPhone(), getClientPhone());
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
