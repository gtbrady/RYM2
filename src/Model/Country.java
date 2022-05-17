package Model;

/**
 * Country class used to represent a country in the countries table
 */
public class Country {
    private int countryID;
    private String countryName;

    /**
     * Parameterized constructor
     * @param id countryID
     * @param name countryName
     */
    public Country(int id, String name) {
        this.countryID = id;
        this.countryName = name;
    }

    /**
     * Default constructor
     */
    public Country() {
        this.countryID = -1;
        this.countryName = "name";
    }

    /**
     * Gets the country ID
     * @return The country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Gets the country name
     * @return The country name
     */
    public String getCountryName() {


        return countryName;
    }

    /**
     * Sets the country ID
     * @param countryID The country ID to be set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Sets the country name
     * @param countryName The country name to be set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     *
     * @return String representation of a country
     */
    @Override
    public String toString() {
        return getCountryName();
    }
}
