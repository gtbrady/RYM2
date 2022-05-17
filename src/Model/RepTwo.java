package Model;

/**
 * RepTwo class used to represent a row in the Schedule for Contact report
 */
public class RepTwo {
    private String contactName;
    private int appointmentID;
    private String title;
    private String type;
    private String description;
    private String start;
    private String end;
    private int customerID;

    /**
     * Parameterized constructor
     * @param cn contactName
     * @param a appointmentID
     * @param title title
     * @param type type
     * @param d description
     * @param s start
     * @param e end
     * @param c customerID
     */
    public RepTwo(String cn, int a, String title, String type, String d, String s, String e, int c) {
        this.contactName = cn;
        this.appointmentID = a;
        this.title = title;
        this.type = type;
        this.description = d;
        this.start = s;
        this.end = e;
        this.customerID = c;
    }

    /**
     * Default constructor
     */
    public RepTwo() {
        this.contactName = "cn";
        this.appointmentID = -1;
        this.title = "title";
        this.type = "type";
        this.description = "d";
        this.start = "s";
        this.end = "e";
        this.customerID = -1;
    }


    /**
     * Gets the contact name
     * @return The contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets the appointment ID
     * @return The appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Gets the title
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the type
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the description
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the start date/time
     * @return The start date/time
     */
    public String getStart() {
        return start;
    }

    /**
     * Gets the end date/time
     * @return The end date/time
     */
    public String getEnd() {
        return end;
    }

    /**
     * Gets the customer ID
     * @return The customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the contact name
     * @param contactName The contact name to be set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Sets the appointment ID
     * @param appointmentID The appointment ID to be set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Sets the title
     * @param title The title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the type
     * @param type The type to be set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the description
     * @param description The description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the start date/time
     * @param start The start date/time to be set
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Sets the end date/time
     * @param end The end date/time to be set
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Sets the customer ID
     * @param customerID The customer ID to be set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
