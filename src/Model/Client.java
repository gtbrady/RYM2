package Model;

import java.util.Objects;

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
        return clientID +": " + clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return getClientName().equals(client.getClientName()) && getClientPhone().equals(client.getClientPhone()) && getClientUsername().equals(client.getClientUsername()) && getClientEmail().equals(client.getClientEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientName(), getClientPhone(), getClientUsername(), getClientEmail());
    }
}
