package Model;

import java.util.Objects;

/**
 * Customer class used to represent a customer in the customers table
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String phoneNumber;
    private String address;
    private String postalCode;
    private String firstLevel;
    private int firstLevelId;
    private String country;
    private int countryId;

    /**
     * Parameterized constructor
     * @param id customerID
     * @param name customerName
     * @param phone phoneNumber
     * @param adr address
     * @param postal postalCode
     * @param level firstLevel
     * @param levelId firstLevelId
     * @param ctr country
     * @param ctrId countryId
     */
    public Customer (int id, String name, String phone, String adr, String postal, String level, int levelId, String ctr, int ctrId) {
        this.customerID = id;
        this.customerName = name;
        this.phoneNumber = phone;
        this.address = adr;
        this.postalCode = postal;
        this.firstLevel = level;
        this.firstLevelId = levelId;
        this.country = ctr;
        this.countryId = ctrId;
    }

    /**
     * Single parameter constructor
     * @param id customerID
     */
    public Customer (int id) {
        this.customerID = id;
        this.customerName = "name";
        this.phoneNumber = "###-###-####";
        this.address = "adr";
        this.postalCode = "postal";
        this.firstLevel = "state";
        this.firstLevelId = -1;
        this.country = "country";
        this.countryId = -1;
    }

    /**
     * Default constructor
     */
    public Customer () {
        this.customerID = -1;
        this.customerName = "name";
        this.phoneNumber = "###-###-####";
        this.address = "adr";
        this.postalCode = "postal";
        this.firstLevel = "state";
        this.firstLevelId = -1;
        this.country = "country";
        this.countryId = -1;
    }

    /**
     * Sets the customer ID
     * @param customerID The customer ID to be set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Sets the customer name
     * @param customerName The customer name to be set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Sets the phone number
     * @param phoneNumber The phone number to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the address
     * @param address The address to be set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the postal code
     * @param postalCode The postal code to be set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets the first-level-division
     * @param firstLevel The first-level-division to be set
     */
    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    /**
     * Sets the first-level-id
     * @param firstLevelId The first-level-id to be set
     */
    public void setFirstLevelId(int firstLevelId) {
        this.firstLevelId = firstLevelId;
    }

    /**
     * Sets the country
     * @param country The country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets the country ID
     * @param countryId The country ID to be set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the customer ID
     * @return The customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the customer name
     * @return The customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the phone number
     * @return The phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the address
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the postal code
     * @return The postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the first-level-division
     * @return The first-level-division
     */
    public String getFirstLevel() {
        return firstLevel;
    }

    /**
     * Gets the country
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the first-level-id
     * @return The first-level-id
     */
    public int getFirstLevelId() {
        return firstLevelId;
    }

    /**
     * Gets the country ID
     * @return The country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     *
     * @return String representation of a country
     */
    @Override
    public String toString() {
      return this.getCustomerID() + ": " + this.customerName;
    }

    /**
     * Determines if the countries are equal
     * @param o Object that is cast to a country object
     * @return If the countries are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return firstLevelId == customer.firstLevelId && countryId == customer.countryId && Objects.equals(customerName,
                customer.customerName) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(address,
                customer.address) && Objects.equals(postalCode, customer.postalCode) && Objects.equals(firstLevel,
                customer.firstLevel) && Objects.equals(country, customer.country);
    }

    /**
     * For overriding equals
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerName, phoneNumber, address, postalCode, firstLevel, firstLevelId, country, countryId);
    }
}
