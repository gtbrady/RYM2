package Model;

/**
 * AuthorizedUser class used to represent a user that has been authenticated via the login screen
 * and is the active user of the application.
 */
public class AuthorizedUser {
    private static int authorizedID;
    private static String authorizedName;

    /**
     * Gets the authorized user ID
     * @return The authorized user ID
     */
    public static int getAuthorizedID() {
        return authorizedID;
    }

    /**
     * Sets the authorized user ID
     * @param authorizedID The authorized user ID to be set
     */
    public static void setAuthorizedID(int authorizedID) {
        AuthorizedUser.authorizedID = authorizedID;
    }

    /**
     * Gets the authorized username
     * @return The authorized username
     */
    public static String getAuthorizedName() {
        return authorizedName;
    }

    /**
     * Sets the authorized username
     * @param authorizedName The authorized username to be set
     */
    public static void setAuthorizedName(String authorizedName) {
        AuthorizedUser.authorizedName = authorizedName;
    }
}
