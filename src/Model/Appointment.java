package Model;

import java.util.Objects;

public abstract class Appointment {
    int appointmentID;
    int counselorID;
    String counselorName;
    int clientID;
    String clientName;
    AppointmentType type;
    String description;
    String startTime;
    String endTime;

    public Appointment(int appointmentID, int counselorID, String counselorName, int clientID, String clientName, AppointmentType type, String description, String startTime, String endTime) {
        this.appointmentID = appointmentID;
        this.counselorID = counselorID;
        this.counselorName = counselorName;
        this.clientID = clientID;
        this.clientName = clientName;
        this.type = type;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getCounselorID() {
        return counselorID;
    }

    public void setCounselorID(int counselorID) {
        this.counselorID = counselorID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return getCounselorID() == that.getCounselorID() && getClientID() == that.getClientID() && getCounselorName().equals(that.getCounselorName()) && getClientName().equals(that.getClientName()) && getType() == that.getType() && getDescription().equals(that.getDescription()) && getStartTime().equals(that.getStartTime()) && getEndTime().equals(that.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCounselorID(), getCounselorName(), getClientID(), getClientName(), getType(), getDescription(), getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return "nAppointment{" +
                "appointmentID=" + appointmentID +
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
