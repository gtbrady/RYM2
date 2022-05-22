package Model;

public class VirtualAppointment extends nAppointment{

    String counselorUsername;
    String clientUsername;

    public VirtualAppointment(int appointmentID, int counselorID, int clientID, AppointmentType type,
                              String description, String startTime, String endTime, String coUsername, String clUsername) {
        super(appointmentID, counselorID, clientID, type, description, startTime, endTime);
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
    public String toString() {
        return "VirtualAppointment{" +
                "counselorUsername='" + counselorUsername + '\'' +
                ", clientUsername='" + clientUsername + '\'' +
                ", appointmentID=" + appointmentID +
                ", counselorID=" + counselorID +
                ", clientID=" + clientID +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
