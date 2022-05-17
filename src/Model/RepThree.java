package Model;

/**
 * RepThree class used to represent a row in the Customers by Country repor
 */
public class RepThree {
    private String country;
    private int customerCount;

    /**
     * Parameterized constructor
     * @param c country
     * @param count customerCount
     */
    public RepThree(String c, int count) {
        this.country = c;
        this.customerCount = count;
    }

    /**
     * Default constructor
     */
    public RepThree() {
        this.country = "Country";
        this.customerCount = -1;
    }

    /**
     * Gets the country
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the customer count
     * @return The customer count
     */
    public int getCustomerCount() {
        return customerCount;
    }

    /**
     * Sets the country
     * @param country The country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets the customer count
     * @param customerCount The customer count to be set
     */
    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }
}
