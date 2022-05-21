package Controller;

import DAO.DBAppointment;
import DAO.DBContact;
import DAO.DBCustomer;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.AuthorizedUser;
import Utility.TimeComparison;
import Utility.TimeManipulation;
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
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * appointmentController controls the appointment screen for the application. It holds
 * a tableview and a form for adding, editing and deleting appointments
 */
public class appointmentController implements Initializable {
    public TableView appointmentTable;
    public TableColumn aIDCol;
    public TableColumn customerCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn cIDCol;
    public TableColumn uIDCol;
    public Label headerLabel;
    public Button addButton;
    public Button editButton;
    public Button deleteButton;
    public Button saveButton;
    public Button backButton;
    public Button cancelButton;
    public TextField appointmentIDText;
    public TextField titleText;
    public TextField locationText;
    public TextField descriptionText;
    public TextField typeText;
    public ComboBox<Contact> contactDropDown;
    public ComboBox<Customer> customerDropDrown;
    public DatePicker startDate;
    public ComboBox<String> startHours;
    public ComboBox<String> startMins;
    public ComboBox<String> startPeriod;
    public ComboBox<String> endHours;
    public ComboBox<String> endMins;
    public ComboBox<String> endPeriod;
    public ToggleGroup TG;
    private ObservableList<Customer> customers = DBCustomer.getCustomers();
    private ObservableList<Contact> contacts = DBContact.getContacts();
    private int buttonStatus = -1;

    /**
    * Sets the tableview and the comboboxes
    * @param url
    * @param resourceBundle
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTable.setItems(DBAppointment.getAllAppointments());
        aIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        cIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        uIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        customerDropDrown.setItems(customers);
        contactDropDown.setItems(contacts);
        startHours.setItems(TimeManipulation.getStringHours());
        startHours.setVisibleRowCount(5);
        startMins.setItems(TimeManipulation.getStringMinutes());
        startPeriod.setItems(TimeManipulation.getStringPeriod());
        endHours.setItems(TimeManipulation.getStringHours());
        endHours.setVisibleRowCount(5);
        endMins.setItems(TimeManipulation.getStringMinutes());
        endPeriod.setItems(TimeManipulation.getStringPeriod());


    }

    /***
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
     * Default radio button selection - retrieves all appointments in the appointments database
     * to populate the view
     * @param actionEvent When All radio button is selected
     * @throws IOException
     */
    public void showAllAppointments(ActionEvent actionEvent) throws IOException {
        appointmentTable.setItems(DBAppointment.getAllAppointments());
    }

    /***
     * Streams and filters all appointments when the weekly radio button is selected.
     * Displays only the appointments whose weekly value matches the current week.
     * <p><b>
     *     LAMBDA EXPRESSION #1 - takes an arryalist stream, filters out appointments that do
     *     not equal the current week, and populates the new arraylist in the tableview.
     * </b></p>
     * @param actionEvent When Weekly radio button is selected
     * @throws IOException
     */
    public void showWeeklyAppointments(ActionEvent actionEvent) throws IOException {

        ArrayList streamList = DBAppointment.getAllAppointments().stream()
                        .filter((x) -> TimeManipulation.weeklySelect(x)).collect(Collectors.toCollection(ArrayList::new));
        ObservableList weeklyAppointments = FXCollections.observableArrayList(streamList);



        appointmentTable.setItems(weeklyAppointments);
        //
    }

    /***
     * Streams and filters all apppointments when the monthly radio button is selected.
     * Displays only the appointments whose monthly value matches the current month.
     * <p><b>
     *     LAMBDA EXPRESSION #2 - takes an arryalist stream, filters out appointments that do
     *     not equal the current month, and populates the new arraylist in the tableview.
     * </b></p>
     * @param actionEvent When Monthly radio button is selected
     * @throws IOException
     */
    public void showMonthlyAppointments(ActionEvent actionEvent) throws IOException {

        ArrayList streamList = DBAppointment.getAllAppointments().stream()
                .filter((x) -> TimeManipulation.monthlySelect(x)).collect(Collectors.toCollection(ArrayList::new));

        ObservableList monthlyAppointments = FXCollections.observableArrayList(streamList);
        appointmentTable.setItems(monthlyAppointments);
    }

    /***
     * enableFields is triggered when user hits either add or edit. Allows
     * the user to interface with the form to add or edit appointments.
     * @param actionEvent When add or update buttons are pressed
     */
    private void enableFields(ActionEvent actionEvent) {
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        titleText.setDisable(false);
        titleText.setEditable(true);
        locationText.setDisable(false);
        locationText.setEditable(true);
        descriptionText.setDisable(false);
        descriptionText.setEditable(true);
        typeText.setDisable(false);
        typeText.setEditable(true);
        startDate.setDisable(false);
        contactDropDown.setDisable(false);
        customerDropDrown.setDisable(false);
        startHours.setDisable(false);
        startMins.setDisable(false);
        startPeriod.setDisable(false);
        endHours.setDisable(false);
        endMins.setDisable(false);
        endPeriod.setDisable(false);
    }

    /***
     * populateFields populates the form fields with the selected appointment's data.
     * @param actionEvent When edit button is pressed
     */
    private void populateFields(ActionEvent actionEvent) {
        try {
            Appointment theAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            Customer aCustomer = new Customer(theAppointment.getCustomerID());
            Contact aContact = new Contact(theAppointment.getContact());
            Timestamp sts =Timestamp.valueOf(theAppointment.getStartDT());
            Timestamp ets = Timestamp.valueOf(theAppointment.getEndDT());
            LocalDate sdt = sts.toLocalDateTime().toLocalDate();
            LocalDateTime slt = sts.toLocalDateTime();
            LocalDateTime elt = ets.toLocalDateTime();
            ObservableList<String> startTimePieces = TimeManipulation.timeToString(slt);
            ObservableList<String> endTimePieces = TimeManipulation.timeToString(elt);

            if (theAppointment == null) {
                throw new NullPointerException();
            }

            appointmentIDText.setText(String.valueOf(theAppointment.getAppointmentID()));
            titleText.setText(theAppointment.getTitle());
            descriptionText.setText(theAppointment.getDescription());
            locationText.setText(theAppointment.getLocation());
            customerDropDrown.setValue(retrieveCustomerName(aCustomer));
            contactDropDown.setValue(retrieveContactName(aContact));
            typeText.setText(theAppointment.getType());
            startDate.setValue(sts.toLocalDateTime().toLocalDate());
            startHours.setValue(startTimePieces.get(0));
            startMins.setValue(startTimePieces.get(1));
            startPeriod.setValue(startTimePieces.get(2));
            endHours.setValue(endTimePieces.get(0));
            endMins.setValue(endTimePieces.get(1));
            endPeriod.setValue(endTimePieces.get(2));

        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select an appointment first");
            alert.showAndWait();
        }
    }

    /***
     * Searches the customers arraylist and returns the Customer object whose customer id matches.
     * Used to populate the customer combo box
     * @param customer Customer whose ID matches the argument Customer ID
     * @return Customer object that matches given customer id
     */
    private Customer retrieveCustomerName(Customer customer) {
        for (Customer c : customers) {
            if(customer.getCustomerID() == c.getCustomerID()) {
                return c;
            }
        }
        return customer;
    }

    /***
     * Searches the contact arraylist and returns the Contact object whose contact id matches.
     * Used to populate the contact combo box
     * @param contact Contact whose ID matches the argument Contact ID
     * @return Contact object that matches given contact id
     */
    private Contact retrieveContactName(Contact contact) {
        for(Contact c: contacts) {
            if(contact.getContactName().equals(c.getContactName())) {
                return c;
            }
        }
        return contact;
    }

    /***
     * Returns all form fields to their default state
     * @param actionEvent When there is either a successful save, cancel or delete
     */
    private void clearFields(ActionEvent actionEvent) {
        headerLabel.setText("Appointments");
        appointmentIDText.setText("Appointment ID");
        buttonStatus = -1;
        addButton.setDisable(false);
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        titleText.setDisable(true);
        titleText.clear();
        locationText.setDisable(true);
        locationText.clear();
        descriptionText.setDisable(true);
        descriptionText.clear();
        typeText.setDisable(true);
        typeText.clear();
        startDate.setDisable(true);
        startDate.setValue(null);
        startDate.getEditor().clear();
        contactDropDown.setDisable(true);
        contactDropDown.getSelectionModel().clearSelection();
        contactDropDown.setValue(null);
        customerDropDrown.setDisable(true);
        customerDropDrown.getSelectionModel().clearSelection();
        customerDropDrown.setValue(null);
        startHours.setDisable(true);
        startHours.getSelectionModel().clearSelection();
        startHours.setValue(null);
        startHours.setPromptText("HH");
        startMins.setDisable(true);
        startMins.getSelectionModel().clearSelection();
        startMins.setPromptText("MM");
        startMins.setValue(null);
        startPeriod.setDisable(true);
        startPeriod.getSelectionModel().clearSelection();
        startPeriod.setValue(null);
        startPeriod.setPromptText("AM");
        endHours.setDisable(true);
        endHours.getSelectionModel().clearSelection();
        endHours.setValue(null);
        endHours.setPromptText("HH");
        endMins.setDisable(true);
        endMins.getSelectionModel().clearSelection();
        endMins.setValue(null);
        endMins.setPromptText("MM");
        endPeriod.setDisable(true);
        endPeriod.getSelectionModel().clearSelection();
        endPeriod.setValue(null);

    }

    /***
     * Enables form fields to allow user to input data for a new appointment
     * @param actionEvent When the add button is pressed
     * @throws IOException
     */
    public void onAdd(ActionEvent actionEvent) throws IOException{
        headerLabel.setText("Add Appointment");
        appointmentIDText.setPromptText("Auto-Generated");
        buttonStatus = 1;
        startPeriod.setValue(TimeManipulation.getStringPeriod().get(0));
        endPeriod.setValue(TimeManipulation.getStringPeriod().get(0));
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        enableFields(actionEvent);

    }

    /***
     * Enables form and populates form fields with the appointment selected in the tableview
     * @param actionEvent When the edit button is pressed
     * @throws IOException
     */
    public void onEdit(ActionEvent actionEvent) throws IOException{
        headerLabel.setText("Edit Appointment");
        buttonStatus = 2;
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        enableFields(actionEvent);
        populateFields(actionEvent);
    }

    /***
     * Returns the appointment screen to the default state
     * @param actionEvent When the cancel button is pressed
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException{
        clearFields(actionEvent);
    }

    /***
     * Deletes the selected appointment. Asks the user for confirmation prior to deleting appointment from the table.
     * Provides a confirmation message when the appointment is deleted.
     * @param actionEvent When the delete button is pressed
     * @throws IOException
     */
    public void onDelete(ActionEvent actionEvent) throws IOException{
        headerLabel.setText("Delete Appointment");
        addButton.setDisable(true);
        editButton.setDisable(true);
        populateFields(actionEvent);

        try {
            Appointment stagedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            int rowsDeleted = -1;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Are you sure you want to delete this appointment?\n" +
                    stagedAppointment.getAppointmentID() + " | " + stagedAppointment.getCustomerName() + " | " +
                    stagedAppointment.getType() + " | " + stagedAppointment.getStartDT());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                rowsDeleted = DBAppointment.deleteAppointment(stagedAppointment);

                if(rowsDeleted > 0) {
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Appointment Deleted");
                    confirmation.setContentText("The appointment below has been deleted:\n" +
                            stagedAppointment.getAppointmentID() + " | " + stagedAppointment.getCustomerName() + " | " +
                            stagedAppointment.getType() + " | " + stagedAppointment.getStartDT() );
                    confirmation.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        appointmentTable.setItems(DBAppointment.getAllAppointments());
        onCancel(actionEvent);
    }

    /***
     * Saves the selected appointment. If add button was selected, the appointment is inserted into the database.
     * If the edit button was selected, the appointment will be updated.
     * @param actionEvent When the save button is selected
     * @throws IOException
     */
    public void onSave(ActionEvent actionEvent) throws IOException{
        try {
            emptyFieldsException();
            Appointment stagedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            Contact stagedContact = contactDropDown.getSelectionModel().getSelectedItem();
            Customer stagedCustomer = customerDropDrown.getSelectionModel().getSelectedItem();

            LocalDateTime sT= TimeManipulation.stringToDate(
                    startHours.getSelectionModel().getSelectedItem().toString(),
                    startMins.getSelectionModel().getSelectedItem().toString(),
                    startPeriod.getSelectionModel().getSelectedItem().toString(),
                    startDate.getValue());
            LocalDateTime eT= TimeManipulation.stringToDate(
                    endHours.getSelectionModel().getSelectedItem().toString(),
                    endMins.getSelectionModel().getSelectedItem().toString(),
                    endPeriod.getSelectionModel().getSelectedItem().toString(),
                    startDate.getValue());

            String startTime = sT.format(TimeManipulation.formatter);
            String endTime = eT.format(TimeManipulation.formatter);

            if(buttonStatus <= 0) {
                throw new IOException();
            }

            if(buttonStatus == 1) {
                Appointment saveAppointment = new Appointment(-1, stagedCustomer.getCustomerName(),titleText.getText(),
                        descriptionText.getText(),locationText.getText(), stagedContact.getContactName(), typeText.getText(),
                         startTime, endTime, stagedCustomer.getCustomerID(),AuthorizedUser.getAuthorizedID());
                Appointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                validAppointmentCheck(saveAppointment);
                bhCheck(saveAppointment);
                scheduleConflictCheck(saveAppointment);
                DBAppointment.addAppointment(convertedAppointment, stagedCustomer, stagedContact);
                onCancel(actionEvent);
            }

            if(buttonStatus == 2) {
                Appointment saveAppointment = new Appointment(stagedAppointment.getAppointmentID(), stagedCustomer.getCustomerName(),titleText.getText(),
                        descriptionText.getText(),locationText.getText(), stagedContact.getContactName(), typeText.getText(),
                        startTime, endTime, stagedCustomer.getCustomerID(),AuthorizedUser.getAuthorizedID());
                Appointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                validAppointmentCheck(saveAppointment);
                bhCheck(saveAppointment);
                scheduleConflictCheck(saveAppointment);
                noChangeCheck(saveAppointment,stagedAppointment);
                DBAppointment.editAppointment(convertedAppointment, stagedCustomer, stagedContact);
                onCancel(actionEvent);
            }

            appointmentTable.setItems(DBAppointment.getAllAppointments());
        } catch (EmptyFields e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Missing information");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        }
        catch (InvalidAppointmentDateTime e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Appointment");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (OutsideBusinessHours e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            LocalDate date = LocalDate.now();
            LocalTime start = LocalTime.of(8,0);
            LocalTime end = LocalTime.of(22,00);
            ZonedDateTime estZonedStart = ZonedDateTime.of(date,start, TimeManipulation.getEstZone());
            ZonedDateTime estZonedEnd = ZonedDateTime.of(date,end, TimeManipulation.getEstZone());
            LocalTime est = estZonedStart.toLocalTime();
            LocalTime eest = estZonedEnd.toLocalTime();

            ZonedDateTime systemZonedStart = estZonedStart.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime systemZonedEnd = estZonedEnd.withZoneSameInstant(ZoneId.systemDefault());
            LocalTime lst = systemZonedStart.toLocalTime();
            LocalTime let = systemZonedEnd.toLocalTime();
            alert.setTitle(e.getMessage());
            alert.setContentText("Appointments can only be scheduled during business hours.\n" +
                                "Business hours: " + est + " - " + eest + " "+ TimeManipulation.getEstZone() +
                                "\nUser Time: " + lst + " - " + let + " " + TimeManipulation.getSystemZone());

            alert.showAndWait();
        }
        catch (ScheduleConflict e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Schedule Conflict");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(NoChanges e ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing information");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Exception thrown when there are empty form fields and the save button is pressed
     */
    public class EmptyFields extends Exception {
        public EmptyFields(String s, int i) {
            super("The " + i + " fields below are required:\n" + s);
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
            if(titleText.getText().isEmpty()) {
                m.append("Title \n");
                ++errorCounter;
                nullFields = true;
            }
            if(descriptionText.getText().isEmpty()) {
                m.append("Description\n");
                ++errorCounter;
                nullFields = true;
            }
            if(locationText.getText().isEmpty()) {
                m.append("Location\n");
                ++errorCounter;
                nullFields = true;
            }
            if(contactDropDown.getSelectionModel().getSelectedItem() == null) {
                m.append("Contact\n");
                ++errorCounter;
                nullFields = true;
            }
            if(customerDropDrown.getSelectionModel().getSelectedItem() == null) {
                m.append("Customer\n");
                ++errorCounter;
                nullFields = true;
            }
            if(typeText.getText().isEmpty()) {
                m.append("Type\n");
                ++errorCounter;
                nullFields = true;
            }
            if (startDate.getEditor().getText().isEmpty()) {
                m.append("Date\n");
                ++errorCounter;
                nullFields = true;
            }
            if (startHours.getSelectionModel().getSelectedItem() == null ||
                startMins.getSelectionModel().getSelectedItem() == null ||
            startPeriod.getSelectionModel().getSelectedItem() == null) {
                m.append("Start Time\n");
                ++errorCounter;
                nullFields = true;
            }
            if(endHours.getSelectionModel().getSelectedItem() == null ||
            endMins.getSelectionModel().getSelectedItem() == null ||
            endPeriod.getSelectionModel().getSelectedItem() == null) {
                m.append(("End Time\n"));
                ++errorCounter;
                nullFields = true;
            }
            if(nullFields) {
                throw new EmptyFields(m.toString(),errorCounter);
            }
    }

    /***
     * Exception thrown when the user selects an appointment to edit and attempts to save but not changes were made
     */
    public class NoChanges extends Exception {
        public NoChanges() {super ("No changes have been made to the selected appointment");}
    }

    /***
     * Checks if the appointments are equal
     * @param save The new appointment object created from user input
     * @param stage The appointment selected from the tableview
     * @throws NoChanges When the appointments are equal
     */
    public void noChangeCheck(Appointment save, Appointment stage) throws NoChanges {
        if(save.equals(stage))
            throw new NoChanges();
    }

    /***
     * Exception thrown when the user attempts to schedule an appointment outside of business hours
     */
    public class OutsideBusinessHours extends Exception {
        public OutsideBusinessHours() {super("Outside Business Hours");}
    }

    /***
     * Checks the appointment is within business hours of 8:00AM to 10:00PM EST when the user presses the save button
     * @param a The appointment that is being checked against business hours
     * @return True if the appointment falls within business hours
     * @throws OutsideBusinessHours When the appointment is outside of business hours
     */
    public boolean bhCheck(Appointment a) throws OutsideBusinessHours {

        boolean validTime = true;
        TimeComparison tc = new TimeComparison(a);
        TimeComparison tc2 = TimeManipulation.businessHourPrep(tc);
        validTime = TimeManipulation.businessHourCheck(tc2);
        if(!validTime) {
            throw new OutsideBusinessHours();
        }
        return validTime;
    }

    /***
     * Exception thrown when the user attempts to save an appointment but provides an invalid date/time combination
     */
    public class InvalidAppointmentDateTime extends Exception {
        public InvalidAppointmentDateTime(String s) {super(s);}
    }

    /***
     * Checks the appointment the user is attempting to save for two conditions:
     *  1.The start time should be before the end time
     *  2.The appointment must be in the future
     * @param a The appointment that is being checked for valid date/times
     * @return True if appointment is valid
     * @throws InvalidAppointmentDateTime If appointment is not valid
     */
    public boolean validAppointmentCheck(Appointment a) throws InvalidAppointmentDateTime {
        StringBuilder message = new StringBuilder();
        boolean validAppointment = true;
        TimeComparison tc = new TimeComparison(a);
        LocalDateTime start = tc.getStagedStart();
        LocalDateTime end = tc.getStagedEnd();
        if (!end.isAfter(start)) {
            message.append("End time must be after start time.\n");
            validAppointment = false;
        }
        if(!start.isAfter(LocalDateTime.now().plusMinutes(1))) {
            message.append("Appointment must be a future date/time.");
            validAppointment = false;
        }
        if(!validAppointment) {
            throw new InvalidAppointmentDateTime(message.toString());
        }
        return validAppointment;
    }

    /***
     * Exception thrown to prevent the user from saving an appointment that overlaps for the given customer
     */
    public class ScheduleConflict extends Exception {
        public ScheduleConflict(String s) {super(s);}
    }

    /***
     * Checks the given appointment for the selected customer to ensure the customer does not have a pre-existing
     * appointment for the provided appointment time
     * @param a The appointment that is being checked
     * @return True if there is a schedule conflict
     * @throws ScheduleConflict If a schedule conflict exists
     */
    public boolean scheduleConflictCheck(Appointment a) throws ScheduleConflict {
        boolean scheduleConflict = false;
        StringBuilder message = new StringBuilder(a.getCustomerName() + " has an appointment scheduled for that time. Please review:\n");
        for(Appointment appointment : DBAppointment.getAllAppointments()) {
            if(a.getCustomerID() == appointment.getCustomerID()) {
                TimeComparison tcp = new TimeComparison(appointment, a);
                if(TimeManipulation.overlapCheck(tcp)) {
                    message.append(appointment.getAppointmentID() + " | " + appointment.getDescription() + " | " +
                            appointment.getStartDT()+ "\n");
                    if(a.getAppointmentID() != appointment.getAppointmentID()) {
                        scheduleConflict = true;
                    }
                }
            }
            if(scheduleConflict) {
                throw new ScheduleConflict(message.toString());
            }
        }
        if(scheduleConflict) {
            throw new ScheduleConflict(message.toString());
        }
        return scheduleConflict;
    }
}
