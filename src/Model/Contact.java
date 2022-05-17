package Model;

/**
 * Contact class used to represent a contact in the contacts table
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Parameterized constructor
     * @param id contactID
     * @param name contactName
     * @param email email
     */
    public Contact (int id, String name, String email) {
        this.contactID = id;
        this.contactName = name;
        this.email = email;
    }

    /**
     * Single parameter Constructor
     * @param name contact name
     */
    public Contact (String name) {
        this.contactID = -1;
        this.contactName  = name;
        this.email = "email";
    }

    /**
     * Default constructor
     */
    public Contact () {
        this.contactID = -1;
        this.contactName = "name";
        this.email = "email";
    }

    /**
     *
     * @return String representation of a contact
     */
    @Override
    public String toString() {
        return this.contactID + ": " + this.contactName;
    }

    /**
     * Gets the contact id
     * @return The contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Gets the contact name
     * @return The contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets the contact email
     * @return The contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the contact ID
     * @param contactID The contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Sets the contact name
     * @param contactName The contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Sets the email
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
