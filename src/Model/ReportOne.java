package Model;

public class ReportOne {
    String month;
    AppointmentType type;
    int totalAppointments;

    public ReportOne(String month, AppointmentType type, int totalAppointments) {
        this.month = month;
        this.type = type;
        this.totalAppointments = totalAppointments;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    @Override
    public String toString() {
        return "ReportOne{" +
                "month='" + month + '\'' +
                ", type=" + type +
                ", totalAppointments=" + totalAppointments +
                '}';
    }
}
