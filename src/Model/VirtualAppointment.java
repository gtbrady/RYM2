package Model;

import java.util.Objects;

public class VirtualAppointment extends Appointment {

    String counselorUsername;
    String clientUsername;

    public VirtualAppointment(int appointmentID, int counselorID, String counselorName, int clientID,
                              String clientName, AppointmentType type, String description,
                              String startTime, String endTime, String coUsername, String clUsername) {
        super(appointmentID, counselorID, counselorName, clientID, clientName, type, description,
                startTime, endTime);
        this.counselorUsername = coUsername;
        this.clientUsername = clUsername;
    }

    public String getCounselorUsername() {
        return counselorUsername;
    }

    public void setCounselorUsername(String counselorUsername) {
        this.counselorUsername = counselorUsername;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualAppointment)) return false;
        if (!super.equals(o)) return false;
        VirtualAppointment that = (VirtualAppointment) o;
        return getCounselorUsername().equals(that.getCounselorUsername()) && getClientUsername().equals(that.getClientUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCounselorUsername(), getClientUsername());
    }

    @Override
    public String toString() {
        return "VirtualAppointment{" +
                "counselorUsername='" + counselorUsername + '\'' +
                ", clientUsername='" + clientUsername + '\'' +
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
