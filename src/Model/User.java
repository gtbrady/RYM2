package Model;

import java.util.Objects;

/**
 * User class used to represent a user in the users table
 */
public class User {
    private int userID;
    private String userName;
    private String password;

    /**
     * Parameterized constructor
     * @param id userID
     * @param name userName
     * @param pw password
     */
    public User (int id, String name, String pw) {
        this.userID = id;
        this.userName = name;
        this.password = pw;
    }

    /**
     * Dual parameter constructor
     * @param name username
     * @param pw password
     */
    public User (String name, String pw) {
        this.userID = -2;
        this.userName = name;
        this.password = pw;
    }

    /**
     * Default constructor
     */
    public User () {
        this.userID = -1;
        this.userName = "name";
        this.password = "pw";
    }

    /**
     * Gets the user ID
     * @return The user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the username
     * @return The username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the password
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user ID
     * @param userID The user ID to be set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Sets the username
     * @param userName The username to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the password
     * @param password The password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return String representation of a user
     */
    @Override
    public String toString() {
        return "Username:" + this.userName + " | Password: " + this.password;
    }

    /**
     * Determines if the users are equal
     * @param o Object that is cast to a user object
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userName.equals(user.userName) && password.equals(user.password);
    }

    /**
     * For overriding equals
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}
