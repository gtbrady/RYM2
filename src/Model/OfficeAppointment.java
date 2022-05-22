package Model;

public class OfficeAppointment extends nAppointment{

    String buildingName;
    String suiteName;

    public OfficeAppointment(int appointmentID, int counselorID, int clientID, AppointmentType type,
                             String description, String startTime, String endTime, String bName, String sName) {
        super(appointmentID, counselorID, clientID, type, description, startTime, endTime);
        this.buildingName = bName;
        this.suiteName = sName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    @Override
    public String toString() {
        return "OfficeAppointment{" +
                "buildingName='" + buildingName + '\'' +
                ", suiteName='" + suiteName + '\'' +
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
