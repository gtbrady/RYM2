package Model;

import java.util.Objects;

/**
 * Appointment class used to represent an appointment in the appointments table
 */
public class Appointment {
    private int appointmentID;
    private String customerName;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String startDT;
    private String endDT;
    private int customerID;
    private int userID;

    /**
     * Parametrized constructor
     * @param aID appointmentID
     * @param n customerName
     * @param t title
     * @param d description
     * @param l location
     * @param c contact
     * @param tp type
     * @param sDT startDT - start date for the appointment
     * @param eDT endDT - end date for the appointment
     * @param cID customerID
     * @param uID userID
     */
    public Appointment(int aID, String n, String t, String d, String l, String c, String tp,
                       String sDT, String eDT, int cID, int uID) {
        this.appointmentID = aID;
        this.customerName = n;
        this.title = t;
        this.description = d;
        this.location = l;
        this.contact = c;
        this.type = tp;
        this.startDT = sDT;
        this.endDT = eDT;
        this.customerID = cID;
        this.userID = uID;
    }

    /**
     * Default constructor
     */
    public Appointment() {
        this.appointmentID = -2;
        this.customerName = "Name";
        this.title = "Title";
        this.description = "Description";
        this.location = "Location";
        this.contact = "Contact";
        this.type = "Type";
        this.startDT = "Start date and time";
        this.endDT = "End date and time";
        this.customerID = -2;
        this.userID = -2;
    }

    /**
     * Sets the appointmentID
     * @param appointmentID The appointmentID to be set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Sets the customer name
     * @param customerName The customer name to be set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Sets the title
     * @param title The tile to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description
     * @param description The description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the location
     * @param location The location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     * Sets the contact
     * @param contact The contact to be set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Sets the type
     * @param type The type to be set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the start date
     * @param startDT The start date to be set
     */
    public void setStartDT(String startDT) {
        this.startDT = startDT;
    }

    /**
     * Sets the end date
     * @param endDT The end date to be set
     */
    public void setEndDT(String endDT) {
        this.endDT = endDT;
    }

    /**
     * Sets the customerID
     * @param customerID the customerID to be set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Sets the userID
     * @param userID The userID to be set
     */
    public void setUserID(int userID) {this.userID = userID; }

    /**
     * Gets the appointmentID
     * @return The appointmentID
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
     * Gets the description
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the location
     * @return The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the contact
     * @return The contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Gets the type
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the startDT
     * @return The startDT
     */
    public String getStartDT() {
        return startDT;
    }

    /**
     * Gets the endDT
     * @return The endDT
     */
    public String getEndDT() {
        return endDT;
    }

    /**
     * Gets the customerID
     * @return The customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the userID
     * @return The userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the customer name
     * @return The customer name
     */
    public String getCustomerName() {
        return customerName;
    }



    /**
     * Determines if the appointments are equal
     * @param o Object that is cast to an appointment object
     * @return If appointments are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return customerID == that.customerID && customerName.equals(that.customerName) && title.equals(that.title) && description.equals(that.description) && location.equals(that.location) && contact.equals(that.contact) && type.equals(that.type) && startDT.equals(that.startDT) && endDT.equals(that.endDT);
    }

    /**
     * For overriding equals
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerName, title, description, location, contact, type, startDT, endDT, customerID);
    }

    /**
     *
     * @return String representation of an appointment object
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", customerName='" + customerName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", contact='" + contact + '\'' +
                ", type='" + type + '\'' +
                ", startDT='" + startDT + '\'' +
                ", endDT='" + endDT + '\'' +
                ", customerID=" + customerID +
                ", userID=" + userID +
                '}';
    }
}
