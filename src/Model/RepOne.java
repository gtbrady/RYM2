package Model;

/**
 * RepOne class used to represent a row in the Appointments by Type/Month report
 */
public class RepOne {
    private String month;
    private String type;
    private int totalAppointments;

    /**
     * Parameterized constructor
     * @param m month
     * @param t type
     * @param tA totalAppointments
     */
    public RepOne(String m, String t, int tA) {
        this.month = m;
        this.type = t;
        this.totalAppointments = tA;
    }

    /**
     * Default constructor
     */
    public RepOne() {
        this.month = "Month";
        this.type = "Type";
        this.totalAppointments = -1;
    }

    /**
     * Gets the month
     * @return The month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Gets the type
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the total appointments
     * @return The total appointments
     */
    public int getTotalAppointments() {
        return totalAppointments;
    }

    /**
     * Sets the month
     * @param month The month to be set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Sets the type
     * @param type The type to be set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the total appointments
     * @param totalAppointments The total appointments to be set
     */
    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }
}
