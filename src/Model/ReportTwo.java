package Model;

public class ReportTwo {

    String counselorName;
    String description;
    AppointmentType type;
    String clientName;
    String startDate;
    String endDate;

    public ReportTwo(String counselorName, String description, AppointmentType type, String clientName, String startDate, String endDate) {
        this.counselorName = counselorName;
        this.description = description;
        this.type = type;
        this.clientName = clientName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ReportTwo{" +
                "counselorName='" + counselorName + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", clientName='" + clientName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
