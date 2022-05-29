package Controller;

import DAO.DBClient;
import DAO.DBnAppointment;
import Model.Client;

import Model.nAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class clientController implements Initializable {

    //FXML
    //Table
    public TableView clientTable;
    public TableColumn clIDCol;
    public TableColumn clientNameCol;
    public TableColumn clientUsernameCol;
    public TableColumn clientEmailCol;
    public TableColumn clientPhoneCol;

    //Label
    public Label headerLabel;




    //Buttons
    public Button addButton;
    public Button editButton;
    public Button deleteButton;
    public Button saveButton;
    public Button backButton;
    public Button cancelButton;

    //Textviews
    public TextField usernameText;
    public TextField emailText;
    public TextField phoneText;
    public TextField nameText;
    public TextField clientSearchBar;

    private int buttonStatus = -1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clientTable.setItems(DBClient.getClients());
        clIDCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientUsernameCol.setCellValueFactory(new PropertyValueFactory<>("clientUsername"));
        clientEmailCol.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
        clientPhoneCol.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));

    }

    public void toMainController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 310, 395);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    private void enableFields(ActionEvent actionEvent) {
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        nameText.setDisable(false);
        nameText.setEditable(true);
        phoneText.setDisable(false);
        phoneText.setEditable(true);
        usernameText.setDisable(false);
        usernameText.setEditable(true);
        emailText.setDisable(false);
        emailText.setEditable(true);

    }

 //search function
    public void clientSearch(ActionEvent actionEvent) {
        String searchString = clientSearchBar.getText().toUpperCase();
        ObservableList<Client> allClients = DBClient.getClients();
        ObservableList<Client> matchingClients = FXCollections.observableArrayList();

        for (Client c: allClients) {
            if(c.getClientName().toUpperCase().startsWith(searchString)) {
                matchingClients.add(c);
            }
        }
        clientTable.setItems(matchingClients);
    }


    private void populateFields(ActionEvent actionEvent) {
        try {
            Client theClient = (Client) clientTable.getSelectionModel().getSelectedItem();
            if (theClient == null) {
                throw new NullPointerException();
            }
            nameText.setText(theClient.getClientName());
            usernameText.setText(theClient.getClientUsername());
            phoneText.setText(theClient.getClientPhone());
            emailText.setText(theClient.getClientEmail());
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select client to edit");
            alert.showAndWait();
        }
    }

    private void clearFields(ActionEvent actionEvent) {
        headerLabel.setText("Clients");
        buttonStatus = -1;
        addButton.setDisable(false);
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        nameText.setDisable(true);
        nameText.clear();
        phoneText.setDisable(true);
        phoneText.clear();
        emailText.setDisable(true);
        emailText.clear();
        usernameText.setDisable(true);
        usernameText.clear();
    }


    public void onAdd(ActionEvent actionEvent) {
        headerLabel.setText("Add Client");
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        buttonStatus = 1;
        enableFields(actionEvent);
    }

    public void onEdit(ActionEvent actionEvent) {
        try{
            Client theClient = (Client) clientTable.getSelectionModel().getSelectedItem();
            populateFields(actionEvent);
            if(theClient == null) {
                throw new NullPointerException();
            }
            headerLabel.setText("Edit Client");
            editButton.setDisable(true);
            deleteButton.setDisable(true);
            enableFields(actionEvent);
            buttonStatus = 2;

        } catch(NullPointerException e) {

        }
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        try {
            Client theClient = (Client) clientTable.getSelectionModel().getSelectedItem();
            populateFields(actionEvent);
            if(theClient == null) {
                throw new NullPointerException();
            }
            addButton.setDisable(true);
            editButton.setDisable(true);
            buttonStatus = -1;
            //existingAppointmentException();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete: " + theClient.toString());
            confirm.setTitle("Delete Confirmation");
            confirm.setContentText("Delete: " + theClient.toString() + "?");
            Optional<ButtonType> result = confirm.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                int deleteCheck = DBClient.deleteClient(theClient);
                if (deleteCheck > 0) {
                    Alert update = new Alert(Alert.AlertType.INFORMATION);
                    update.setTitle("Delete successful");
                    update.setContentText(theClient.getClientName() + " has been deleted.");
                    update.showAndWait();

                } else {
                    Alert update = new Alert(Alert.AlertType.INFORMATION);
                    update.setTitle("Delete unsuccessful");
                    update.setContentText(theClient.getClientName() + " hasn't been deleted.");
                    update.showAndWait();

                }
            }
        }/* catch (ExistingAppointments e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Delete Client with Existing Appointments");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }*/
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        clientTable.setItems(DBClient.getClients());
        onCancel(actionEvent);

    }

    public void onSave(ActionEvent actionEvent) {
        try {
            emptyFieldsException();
            Client stagedClient = (Client) clientTable.getSelectionModel().getSelectedItem();

            if(buttonStatus<=0) {
                throw  new IOException();
            }
            if(buttonStatus == 1) {
                Client saveClient = new Client(-1, nameText.getText(),
                        phoneText.getText(),usernameText.getText(), emailText.getText());
                emailCheck(saveClient.getClientEmail());
                phoneCheck(saveClient.getClientPhone());
                DBClient.addClient(saveClient);
                onCancel(actionEvent);
            }
            if(buttonStatus == 2) {
                Client saveClient = new Client(stagedClient.getClientID(), nameText.getText(),
                        phoneText.getText(),usernameText.getText(), emailText.getText());
                emailCheck(saveClient.getClientEmail());
                phoneCheck(saveClient.getClientPhone());
                noChangeCheck(saveClient,stagedClient);
                DBClient.editClient(saveClient);
                onCancel(actionEvent);

            }
            clientTable.setItems(DBClient.getClients());
        }
            catch (EmptyFields e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(InvalidEmail e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid email");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(InvalidPhone e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid phone");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(NoChanges e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No changes made");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (IOException e) {
        }
    }


    public void onCancel(ActionEvent actionEvent) throws IOException {
        clearFields(actionEvent);
    }

    public class EmptyFields extends Exception {
        public EmptyFields(String s, int i) {
            super("The "+ i + " fields below are required:\n" + s);
        }
    }

    public void emptyFieldsException() throws EmptyFields {
        StringBuilder m = new StringBuilder();
        int errorCounter = 0;
        Boolean nullFields = false;
        if (nameText.getText().isEmpty()) {
            m.append("Name \n");
            ++errorCounter;
            nullFields = true;
        }
        if (phoneText.getText().isEmpty()) {
            m.append("Phone Number \n");
            ++errorCounter;
            nullFields = true;
        }
        if (usernameText.getText().isEmpty()) {
            m.append("Username \n");
            ++errorCounter;
            nullFields = true;
        }
        if (emailText.getText().isEmpty()) {
            m.append("Email \n");
            ++errorCounter;
            nullFields = true;
        }
        if(nullFields) {
            throw new EmptyFields(m.toString(),errorCounter);
        }
    }

    public class InvalidEmail extends Exception {
        public InvalidEmail(String s) {
            super(s + " is not a valid email address");
        }
    }

    public void emailCheck (String email) throws InvalidEmail{
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        boolean validEmail = true;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        validEmail = matcher.matches();
        if(!validEmail) {
            throw new InvalidEmail(email);
        }
    }

    public class InvalidPhone extends Exception {
        public InvalidPhone(String s) {
            super(s + " is not a valid phone number");
        }
    }


    public void phoneCheck(String phone) throws InvalidPhone{
        String regex="^\\d{3}-\\d{3}-\\d{4}$";
        boolean validPhone = true;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        validPhone = matcher.matches();
        if(!validPhone) {
            throw new InvalidPhone(phone);
        }
    }

    public class ExistingAppointments extends Exception {
        public ExistingAppointments(String s) {
            super("The customer below cannot be deleted due to existing scheduled appointments. \n" + s);
        }
    }

    public void existingAppointmentException() throws ExistingAppointments {
        Boolean exists = false;
        Client deleteClient = (Client) clientTable.getSelectionModel().getSelectedItem();
        ObservableList<nAppointment> appointments = DBnAppointment.getAppointments();
        StringBuilder m = new StringBuilder(deleteClient.getClientName() + ": \n");
        for(nAppointment a: appointments) {
            if(a.getClientID() == deleteClient.getClientID()) {
                m.append(a.getType().toString() + " appointment: " + a.getAppointmentID() +
                        " scheduled for " + a.getStartTime() + "\n");
                exists = true;
            }
        }
        if(exists) {
            throw new ExistingAppointments(m.toString());
        }

    }


    private class NoChanges extends Exception {
        public NoChanges() {super ("No changes have been made to the selected client");}
    }

    public void noChangeCheck(Client save, Client stage) throws NoChanges {
        if(save.equals(stage)) {
            throw new NoChanges();
        }
    }


}
