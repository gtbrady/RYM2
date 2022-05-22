package Model;

public class Client {
    int clientID;
    String clientName;
    String clientPhone;
    String clientUsername;
    String clientEmail;

    public Client(int clientID, String clientName, String clientPhone, String clientUsername, String clientEmail) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.clientUsername = clientUsername;
        this.clientEmail = clientEmail;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientID=" + clientID +
                ", clientName='" + clientName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", clientUsername='" + clientUsername + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                '}';
    }
}
