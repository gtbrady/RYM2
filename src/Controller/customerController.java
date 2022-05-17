package Controller;

import DAO.DBAppointment;
import DAO.DBCountry;
import DAO.DBCustomer;
import DAO.DBFirstDivision;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
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

/***
 * customerController controls the customer screen for the application. It holds a
 * tableview and a form for adding, editing and deleting customers
 */
public class customerController implements Initializable {

    public TableView custTable;
    public TableColumn custIdCol;
    public TableColumn custNameCol;
    public TableColumn custPhoneCol;
    public TableColumn custAddressCol;
    public TableColumn custCountryCol;
    public TableColumn custStateCol;
    public TableColumn custPostalCol;
    public Label headerLabel;
    public TextField idText;
    public TextField nameText;
    public TextField phoneText;
    public TextField addressText;
    public TextField postalText;
    public ComboBox<Country> countryCombo;
    public ComboBox<FirstLevelDivision> stateCombo;
    public Button addButton;
    public Button editButton;
    public Button deleteButton;
    public Button saveButton;
    public Button cancelButton;
    private ObservableList<Country> countries = DBCountry.getCountry();
    private ObservableList<FirstLevelDivision> states = DBFirstDivision.getFirstLevel();
    private int buttonStatus = -1;

    /**
     * Sets the tableview and the comboboxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        custTable.setItems(DBCustomer.getCustomers());
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        custStateCol.setCellValueFactory(new PropertyValueFactory<>("firstLevel"));
        custPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryCombo.setItems(countries);
        stateCombo.setVisibleRowCount(5);
    }

    /**
     * Transitions to the main menu screen
     * @param actionEvent When the back button is pressed
     * @throws IOException
     */
    public void toMainController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 310, 335);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /***
     * enableFields is triggered when user hits either add or edit. Allows
     * The user to interface with the form to add or edit customers.
     * @param actionEvent when add or update buttons are pressed
     */
    private void enableFields(ActionEvent actionEvent) {
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        nameText.setDisable(false);
        nameText.setEditable(true);
        phoneText.setDisable(false);
        phoneText.setEditable(true);
        addressText.setDisable(false);
        addressText.setEditable(true);
        postalText.setDisable(false);
        postalText.setEditable(true);
        countryCombo.setDisable(false);
    }

    /***
     * populateFields populates the form fields with the selected customer's data.
     * @param actionEvent When edit button is pressed
     */
    private void populateFields(ActionEvent actionEvent) {
        try {
            Customer theCustomer =  (Customer) custTable.getSelectionModel().getSelectedItem();
            if (theCustomer == null) {
                throw new NullPointerException();
            }
            FirstLevelDivision theLevel = new FirstLevelDivision(theCustomer.getFirstLevelId(),theCustomer.getFirstLevel(), theCustomer.getCountryId());
            Country theCountry= new Country(theCustomer.getCountryId(),theCustomer.getCountry());
            idText.setText(String.valueOf(theCustomer.getCustomerID()));
            nameText.setText(theCustomer.getCustomerName());
            phoneText.setText(theCustomer.getPhoneNumber());
            addressText.setText(theCustomer.getAddress());
            countryCombo.setValue(theCountry);
            stateCombo.setValue(theLevel);
            postalText.setText(theCustomer.getPostalCode());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select customer to edit");
            alert.showAndWait();
        }
    }

    /***
     * Returns all form fields to their default state
     * @param actionEvent When there is either a successful save, cancel or delete
     */
    private void clearFields(ActionEvent actionEvent) {
        headerLabel.setText("Customers");
        idText.setText("Customer ID");
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
        addressText.setDisable(true);
        addressText.clear();
        postalText.setDisable(true);
        postalText.clear();
        countryCombo.setDisable(true);
        countryCombo.getSelectionModel().clearSelection();
        countryCombo.setValue(null);
        stateCombo.setDisable(true);
        stateCombo.getSelectionModel().clearSelection();
        stateCombo.setValue(null);
    }

    /***
     * Populates first-level-division combo box based on the selected country
     * @param actionEvent When a country is selected from the country combo box
     * @throws IOException
     */
    public void onCountry(ActionEvent actionEvent) throws IOException {
        try {
            Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
            ObservableList<FirstLevelDivision> selectedStates = FXCollections.observableArrayList();
            for(FirstLevelDivision d: states) {
                if(selectedCountry == null)
                {
                    throw new NullPointerException();
                }
                if (d.getCtID() == selectedCountry.getCountryID()) {
                    selectedStates.add(d);
                }
            }
            stateCombo.setItems(selectedStates);
            stateCombo.setDisable(false);
        }   catch (NullPointerException e) {

        }
    }

    /***
     * Enables form fields to allow user to input data for a new customer
     * @param actionEvent When the add button is pressed
     * @throws IOException
     */
    public void onAdd(ActionEvent actionEvent) throws IOException {
        headerLabel.setText("Add Customer");
        idText.setPromptText("Auto-Generated");

        editButton.setDisable(true);
        deleteButton.setDisable(true);
        buttonStatus = 1;
        enableFields(actionEvent);
    }

    /***
     * Enables form and populates form fields with the customer selected in the tableview
     * @param actionEvent When the edit button is pressed
     * @throws IOException
     */
    public void onEdit(ActionEvent actionEvent) throws IOException {
        try {
            Customer theCustomer = (Customer) custTable.getSelectionModel().getSelectedItem();
            populateFields(actionEvent);
            if(theCustomer == null) {
                throw new NullPointerException();
            }
            headerLabel.setText("Edit Customer");
            addButton.setDisable(true);
            deleteButton.setDisable(true);
            enableFields(actionEvent);
            buttonStatus = 2;
        } catch (NullPointerException e) {

        }
    }

    /***
     * Returns the appointment screen to the default state
     * @param actionEvent When the cancel button is pressed
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        clearFields(actionEvent);
    }

    /***
     * Deletes the selected appointment. Asks the user for confirmation prior to deleting customer from the table.
     * Provides a confirmation message when the appointment is deleted.
     * @param actionEvent When the delete button is pressed
     * @throws IOException
     */
    public void onDelete(ActionEvent actionEvent) throws IOException{
        try {
            Customer theCustomer = (Customer) custTable.getSelectionModel().getSelectedItem();
            populateFields(actionEvent);
            if(theCustomer == null) {
                throw new NullPointerException();
            }
            addButton.setDisable(true);
            editButton.setDisable(true);
            buttonStatus = -1;
            existingAppointmentException();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete: " + theCustomer.toString());
            confirm.setTitle("Delete Confirmation");
            confirm.setContentText("Delete: " + theCustomer.toString() + "?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int deleteCheck = DBCustomer.deleteCustomer(theCustomer);
                if (deleteCheck > 0) {
                    Alert update = new Alert(Alert.AlertType.INFORMATION);
                    update.setTitle("Delete successful");
                    update.setContentText(theCustomer.getCustomerName() + " has been deleted.");
                    update.showAndWait();

                } else {
                    Alert update = new Alert(Alert.AlertType.INFORMATION);
                    update.setTitle("Delete unsuccessful");
                    update.setContentText(theCustomer.getCustomerName() + " hasn't been deleted.");
                    update.showAndWait();

                }
            }

        } catch (ExistingAppointments e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Delete Customer with Existing Appointments");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        custTable.setItems(DBCustomer.getCustomers());
        onCancel(actionEvent);
    }

    /***
     * Saves the selected customer. If add button was selected, the customer is inserted into the database.
     * If the edit button was selected, the customer will be updated.
     * @param actionEvent When the save button is selected
     * @throws IOException
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        try {
            emptyFieldsException();
            Country stagedCountry = countryCombo.getSelectionModel().getSelectedItem();
            FirstLevelDivision stagedFirstLevelDivision = stateCombo.getSelectionModel().getSelectedItem();
            Customer stagedCustomer =  (Customer) custTable.getSelectionModel().getSelectedItem();

            if (buttonStatus <= 0) {
                throw new IOException();
            }

            if (buttonStatus == 1) {
                Customer saveCustomer = new Customer(-1, nameText.getText(),phoneText.getText(),
                        addressText.getText(),postalText.getText(), stagedFirstLevelDivision.getDivisionName(),
                        stagedFirstLevelDivision.getDivisionID(), stagedCountry.getCountryName(),
                        stagedCountry.getCountryID());
                DBCustomer.addCustomer(saveCustomer);
                onCancel(actionEvent);
            }

            if (buttonStatus == 2) {
                Customer saveCustomer = new Customer(stagedCustomer.getCustomerID(), nameText.getText(),phoneText.getText(),
                        addressText.getText(),postalText.getText(), stagedFirstLevelDivision.getDivisionName(),
                        stagedFirstLevelDivision.getDivisionID(), stagedCountry.getCountryName(),
                        stagedCountry.getCountryID());
                noChangeCheck(saveCustomer,stagedCustomer);
                DBCustomer.editCustomer(saveCustomer);
                onCancel(actionEvent);
            }

            custTable.setItems(DBCustomer.getCustomers());
        }
        catch (EmptyFields e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(NoChanges e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing information");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (IOException e) {
        }
    }

    /***
     * Exception thrown when there are empty form fields and the save button is pressed
     */
    public class EmptyFields extends Exception {
        public EmptyFields(String s, int i) {
            super("The "+ i + " fields below are required:\n" + s);
        }
    }

    /***
     * Checks for empty form fields
     * @throws EmptyFields When any empty field is found
     */
    public void emptyFieldsException() throws EmptyFields {
        StringBuilder m = new StringBuilder();
        int errorCounter = 0;
        Boolean nullFields = false;
        if(nameText.getText().isEmpty()) {
            m.append("Name \n");
            ++errorCounter;
            nullFields = true;
        }
        if(phoneText.getText().isEmpty()) {
            m.append("Phone Number \n");
            ++errorCounter;
            nullFields = true;
        }
        if(addressText.getText().isEmpty()) {
            m.append("Address \n");
            ++errorCounter;
            nullFields = true;
        }
        if(postalText.getText().isEmpty()) {
            m.append("Postal code \n");
            ++errorCounter;
            nullFields = true;
        }
        if(countryCombo.getSelectionModel().getSelectedItem() == null) {
            m.append("Country \n");
            ++errorCounter;
            nullFields = true;
        }
        if(stateCombo.getSelectionModel().getSelectedItem() == null) {
            m.append("State/Province \n");
            ++errorCounter;
            nullFields = true;
        }
        if(nullFields == true) {
            throw new EmptyFields(m.toString(), errorCounter);
        }
    }

    /***
     * Exception thrown when the user attempts to delete a customer, but the customer has
     * pre-existing appointments scheduled. Must be prevented due to foreign key constraints
     */
    public class ExistingAppointments extends Exception {
        public ExistingAppointments(String s) {
            super("The customer below cannot be deleted due to existing scheduled appointments. \n" + s);
        }
    }

    /***
     * Checks if there are any appointments scheduled for the selected customer
     * @throws ExistingAppointments If customer has scheduled appointments
     */
    public void existingAppointmentException() throws ExistingAppointments {
        Boolean exists = false;
        Customer deleteCustomer = (Customer) custTable.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> appointments = DBAppointment.getAllAppointments();
        StringBuilder m = new StringBuilder(deleteCustomer.getCustomerName() + ": \n");
        for(Appointment a : appointments) {
            if(a.getCustomerID() == deleteCustomer.getCustomerID()) {
                m.append("Appointment: " + a.getAppointmentID() + " scheduled for " + a.getStartDT() + "\n");
                exists = true;
            }
        }
        if (exists == true) {
            throw new ExistingAppointments(m.toString());
        }
    }

    /***
     * Exception thrown when the user selects a customer to edit and attempts to save but not changes were made
     */
    private class NoChanges extends Exception {
        public NoChanges() {super ("No changes have been made to the selected customer");}
    }

    /***
     * Checks if the customers are equal
     * @param save The new customer object created from user input
     * @param stage The customer selected from the tableview
     * @throws NoChanges When the customers are equal
     */
    public void noChangeCheck(Customer save, Customer stage) throws NoChanges {
        if(save.equals(stage))
        throw new NoChanges();
    }
}
