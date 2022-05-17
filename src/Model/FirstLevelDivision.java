package Model;

/**
 * First-Level-Division class used to represent a first-level-division in the first-level-division tables
 */
public class FirstLevelDivision {
    private int divisionID;
    private String divisionName;
    private int ctID;

    /**
     * Parameterized constructor
     * @param id divisionID
     * @param name divisionName
     * @param c ctID
     */
    public FirstLevelDivision(int id, String name, int c) {
        this.divisionID = id;
        this.divisionName = name;
        this.ctID = c;
    }

    /**
     * Default constructor
     */
    public FirstLevelDivision() {
        this.divisionID = -1;
        this.divisionName = "name";
    }

    /**
     * Gets the division ID
     * @return The division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Gets the division name
     * @return The division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Gets the country ID
     * @return The country ID
     */
    public int getCtID() {
        return ctID;
    }

    /**
     * Sets the division ID
     * @param divisionID The division ID to be set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Sets the division name
     * @param divisionName The division name to be set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Sets the country ID
     * @param ctID The country ID to be set
     */
    public void setCtID(int ctID) {
        this.ctID = ctID;
    }

    /**
     *
     * @return String representation of a first-level-division
     */
    @Override
    public String toString() {
        return divisionName;
    }
}
