package Model;

public abstract class nAppointment {
    int appointmentID;
    int counselorID;
    int clientID;
    AppointmentType type;
    String description;
    String startTime;
    String endTime;

    public nAppointment(int appointmentID, int counselorID, int clientID, AppointmentType type, String description, String startTime, String endTime) {
        this.appointmentID = appointmentID;
        this.counselorID = counselorID;
        this.clientID = clientID;
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

    @Override
    public String toString() {
        return "nAppointment{" +
                "appointmentID=" + appointmentID +
                ", counselorID=" + counselorID +
                ", clientID=" + clientID +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
