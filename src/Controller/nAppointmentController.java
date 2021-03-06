package Controller;

import DAO.*;
import Model.*;
import Utility.TimeComparison;
import Utility.TimeManipulation;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class nAppointmentController implements Initializable {

    //Table
    public TableView nAppointmentTable;
    public TableColumn aIDCol;
    public TableColumn clientCol;
    public TableColumn counselorCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;

    // Labels
    public Label headerLabel;


    //Buttons
    public Button addButton;
    public Button editButton;
    public Button deleteButton;
    public Button saveButton;
    public Button backButton;
    public Button cancelButton;

// Input
    public TextField descriptionText;

    public ComboBox<AppointmentType> typeDropDown;
    public ComboBox<Client> clientDropDown;
    public ComboBox <Counselor> counselorDropDown;


    //dates
    public DatePicker startDate;
    public ComboBox <String> startHours;
    public ComboBox <String> startMins;
    public ComboBox <String> startPeriod;

    public ComboBox <String> endHours;
    public ComboBox <String> endMins;
    public ComboBox <String> endPeriod;

    //radio
    public ToggleGroup TG;
    public RadioButton allRadio;
    public RadioButton inOfficeRadio;
    public RadioButton virtualRadio;
    public RadioButton phoneRadio;
    public Label detail1Label;
    public Label detail2Label;
    public Label detail1LabelText;
    public Label detail2LabelText;


    private int buttonStatus = -1;

    private ObservableList<Client> clients = DBClient.getClients();
    private ObservableList<Counselor> counselors = DBCounselor.getCounselors();
    private ObservableList<Office> offices = DBOffice.getOffices();
    private ObservableList<String> officeNames = DBOffice.getOfficeNames();
    private ObservableList<Suite> suites = DBSuite.getSuites();

    private ObservableList<String> suiteNames = DBSuite.getSuiteNames();

    private static PhoneAppointment stagedPhoneAppointment;
    private static VirtualAppointment stagedVirtualAppointment;
    private static OfficeAppointment stagedOfficeAppointment;

    private static AppointmentType stagedType;
    private static Counselor sCounselor;
    private static Client sClient;

    private static int debugTracker = 1;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nAppointmentTable.setItems(DBAppointment.getAppointments());
        aIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        counselorCol.setCellValueFactory(new PropertyValueFactory<>("counselorName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));




        clientDropDown.setItems(clients);
        counselorDropDown.setItems(counselors);
        typeDropDown.getItems().setAll(AppointmentType.values());

        startHours.setItems(TimeManipulation.getStringHours());
        startHours.setVisibleRowCount(5);
        startMins.setItems(TimeManipulation.getStringMinutes());
        startPeriod.setItems(TimeManipulation.getStringPeriod());
        endHours.setItems(TimeManipulation.getStringHours());
        endHours.setVisibleRowCount(5);
        endMins.setItems(TimeManipulation.getStringMinutes());
        endPeriod.setItems(TimeManipulation.getStringPeriod());
        detail1Label.setVisible(false);
        detail2Label.setVisible(false);
        detail1LabelText.setVisible(false);
        detail2LabelText.setVisible(false);


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

    private void enableFields() {

        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        descriptionText.setDisable(false);
        descriptionText.setEditable(true);

        typeDropDown.setDisable(false);
        counselorDropDown.setDisable(false);
        clientDropDown.setDisable(false);

        startDate.setDisable(false);
        startHours.setDisable(false);
        startMins.setDisable(false);
        startPeriod.setDisable(false);
        endHours.setDisable(false);
        endMins.setDisable(false);
        endPeriod.setDisable(false);


    }

    private void populateFields() {
        try {


        Appointment theAppointment = (Appointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            if (theAppointment == null) {
                throw new NullPointerException();
            }
        stagedType = theAppointment.getType();

        setDetailHeader(stagedType);
        if(theAppointment instanceof OfficeAppointment) {

            stagedOfficeAppointment = (OfficeAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            sCounselor = new Counselor(stagedOfficeAppointment.getCounselorID());
            detail1LabelText.setText(stagedOfficeAppointment.getBuildingName());
            detail1LabelText.setVisible(true);
            detail2LabelText.setText(stagedOfficeAppointment.getSuiteName());
            detail2LabelText.setVisible(true);
            sClient = new Client(stagedOfficeAppointment.getClientID());



        }
        else if (theAppointment instanceof VirtualAppointment) {

            stagedVirtualAppointment = (VirtualAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            sCounselor = new Counselor(stagedVirtualAppointment.getCounselorID());
            sClient = new Client(stagedVirtualAppointment.getClientID());
            detail1LabelText.setText(stagedVirtualAppointment.getCounselorUsername());
            detail1LabelText.setVisible(true);
            detail2LabelText.setText(stagedVirtualAppointment.getClientUsername());
            detail2LabelText.setVisible(true);


        }
        else if (theAppointment instanceof PhoneAppointment) {

            stagedPhoneAppointment = (PhoneAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            sCounselor = new Counselor(stagedPhoneAppointment.getCounselorID());
            sClient = new Client(stagedPhoneAppointment.getClientID());
            detail1LabelText.setText(stagedPhoneAppointment.getCounselorPhone());
            detail1LabelText.setVisible(true);
            detail2LabelText.setText(stagedPhoneAppointment.getClientPhone());
            detail2LabelText.setVisible(true);
        }
        descriptionText.setText(theAppointment.getDescription());
        counselorDropDown.setValue(sCounselor);
        clientDropDown.setValue(sClient);
        typeDropDown.setValue(stagedType);

        Timestamp sts =Timestamp.valueOf(theAppointment.getStartTime());
        Timestamp ets = Timestamp.valueOf(theAppointment.getEndTime());
        LocalDate sdt = sts.toLocalDateTime().toLocalDate();
        LocalDateTime slt = sts.toLocalDateTime();
        LocalDateTime elt = ets.toLocalDateTime();
        ObservableList<String> startTimePieces = TimeManipulation.timeToString(slt);
        ObservableList<String> endTimePieces = TimeManipulation.timeToString(elt);


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

    private void clearFields() {

        headerLabel.setText("Appointments");
        detail1Label.setVisible(false);
        detail2Label.setVisible(false);
        buttonStatus = -1;
        addButton.setDisable(false);
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        descriptionText.setDisable(true);
        descriptionText.clear();

        typeDropDown.setDisable(true);
        typeDropDown.getSelectionModel().clearSelection();
        typeDropDown.setValue(null);
        clientDropDown.setDisable(true);
        clientDropDown.getSelectionModel().clearSelection();
        clientDropDown.setValue(null);
        counselorDropDown.setDisable(true);
        counselorDropDown.getSelectionModel().clearSelection();
        counselorDropDown.setValue(null);
        startDate.setDisable(true);
        startDate.setValue(null);
        startDate.getEditor().clear();
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

        detail1LabelText.setText(null);
        detail1LabelText.setVisible(false);
        detail2LabelText.setText(null);
        detail2LabelText.setVisible(false);


        stagedPhoneAppointment = null;
        stagedVirtualAppointment = null;
        stagedOfficeAppointment = null;
        stagedType = null;
        sCounselor = null;
        sClient = null;


    }

    //this is disabled and we are almost functional
    public void onType(ActionEvent actionEvent) throws IOException{

        if(typeDropDown.getValue() != null) {
            stagedType = typeDropDown.getSelectionModel().getSelectedItem();
            setDetailHeader(stagedType);

            if(stagedType == AppointmentType.Office) {
                if(stagedOfficeAppointment != null) {
                    detail1LabelText.setText(stagedOfficeAppointment.getBuildingName());
                    detail1LabelText.setVisible(true);
                    detail2LabelText.setText(stagedOfficeAppointment.getSuiteName());
                    detail2LabelText.setVisible(true);
                }
                else {
                    if(counselorDropDown.getValue() != null) {
                        Office office = new Office(counselorDropDown.getValue().getOfficeID());
                        Suite suite = new Suite(counselorDropDown.getValue().getSuiteID());
                        detail1LabelText.setText(office.getBuildingName());
                        detail1LabelText.setVisible(true);
                        detail2LabelText.setText(suite.getSuiteName());
                        detail2LabelText.setVisible(true);
                    }
                }
            } else if(stagedType == AppointmentType.Phone) {
                if(stagedPhoneAppointment != null) {
                    detail1LabelText.setText(stagedPhoneAppointment.getCounselorPhone());
                    detail1LabelText.setVisible(true);
                    detail2LabelText.setText(stagedPhoneAppointment.getClientPhone());
                    detail2LabelText.setVisible(true);
                }
                else {
                    if(counselorDropDown.getValue() != null) {
                        detail1LabelText.setText(counselorDropDown.getValue().getCounselorPhone());
                        detail1LabelText.setVisible(true);
                    }
                    if(clientDropDown.getValue() != null) {
                        detail2LabelText.setText(clientDropDown.getValue().getClientPhone());
                        detail2LabelText.setVisible(true);
                    }
                }


            } else if (stagedType == AppointmentType.Virtual) {
                if(stagedVirtualAppointment != null) {
                    detail1LabelText.setText(stagedVirtualAppointment.getCounselorUsername());
                    detail1LabelText.setVisible(true);
                    detail2LabelText.setText(stagedVirtualAppointment.getClientUsername());
                    detail2LabelText.setVisible(true);
                }
                else {
                    if(counselorDropDown.getValue() != null) {
                        detail1LabelText.setText(counselorDropDown.getValue().getCounselorUsername());
                        detail1LabelText.setVisible(true);
                    }
                    if(clientDropDown.getValue() != null) {
                        detail2LabelText.setText(clientDropDown.getValue().getClientUsername());
                        detail2LabelText.setVisible(true);
                    }

                }

            }
        }
    }

    /*
    THIS IS THE CULPRIT
    not filtering for the office the type drop down - it is assuming everything is an office whenever something changes
     */

    public void onClient(ActionEvent actionEvent) {
        if(clientDropDown.getValue() != null) {
            if(stagedType != null) {
                if(stagedType == AppointmentType.Phone) {
                    if(clientDropDown != null) {
                        detail2LabelText.setText(clientDropDown.getValue().getClientPhone());
                        detail2LabelText.setVisible(true);
                    }
                }
                else if (stagedType == AppointmentType.Virtual) {
                    if(clientDropDown != null) {
                        detail2LabelText.setText(clientDropDown.getValue().getClientUsername());
                        detail2LabelText.setVisible(true);
                    }
                }
            }
        }
    }

    public void onCounselor(ActionEvent actionEvent)  {
        if(counselorDropDown.getValue() != null) {
            if (stagedType != null) {
                if (stagedType == AppointmentType.Office) {
                    if (counselorDropDown != null) {
                        Office office = new Office(counselorDropDown.getValue().getOfficeID());
                        Suite suite = new Suite(counselorDropDown.getValue().getSuiteID());
                        detail1LabelText.setText(office.getBuildingName());
                        detail1LabelText.setVisible(true);
                        detail2LabelText.setText(suite.getSuiteName());
                        detail2LabelText.setVisible(true);
                    }
                } else if (stagedType == AppointmentType.Phone) {
                    if (counselorDropDown != null) {
                        detail1LabelText.setText(counselorDropDown.getValue().getCounselorPhone());
                        detail1LabelText.setVisible(true);
                    }
                } else if (stagedType == AppointmentType.Virtual) {
                    if (counselorDropDown != null) {
                        detail1LabelText.setText(counselorDropDown.getValue().getCounselorUsername());
                        detail1LabelText.setVisible(true);
                    }
                }
            }
        }
    }

    public void onAdd(ActionEvent actionEvent) throws IOException {
        headerLabel.setText("Add Appointment");
        buttonStatus = 1;
        startPeriod.setValue(TimeManipulation.getStringPeriod().get(0));
        endPeriod.setValue(TimeManipulation.getStringPeriod().get(0));
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        enableFields();
    }

    public void onEdit(ActionEvent actionEvent) throws IOException {
        headerLabel.setText("Edit Appointment");
        buttonStatus = 2;
        addButton.setDisable(true);
        //editButton.setDisable(true);
        deleteButton.setDisable(true);
        enableFields();

        populateFields();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        clearFields();
    }

    public void onSave(ActionEvent actionEvent) throws IOException {
        try {
            emptyFieldsException();
            Appointment stagedAppointment = (Appointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            Counselor stagedCounselor = counselorDropDown.getSelectionModel().getSelectedItem();
            Client stagedClient = clientDropDown.getSelectionModel().getSelectedItem();



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
                if(stagedType == AppointmentType.Office){
                    OfficeAppointment saveAppointment = new OfficeAppointment(-1, stagedCounselor.getCounselorID(),
                            stagedCounselor.getCounselorName(),stagedClient.getClientID(),stagedClient.getClientName(),
                            typeDropDown.getSelectionModel().getSelectedItem(),descriptionText.getText(),startTime,endTime,
                            detail1LabelText.getText(),detail2LabelText.getText());
                    OfficeAppointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                    validAppointmentCheck(saveAppointment);
                    bhCheck(saveAppointment);
                    scheduleConflictCheck(saveAppointment);
                    DBAppointment.addnAppointment(convertedAppointment,stagedClient,stagedCounselor);
                    clearFields();
                }
                else if(stagedType == AppointmentType.Phone){
                    PhoneAppointment saveAppointment = new PhoneAppointment(-1, stagedCounselor.getCounselorID(),
                            stagedCounselor.getCounselorName(),stagedClient.getClientID(),stagedClient.getClientName(),
                            typeDropDown.getSelectionModel().getSelectedItem(),descriptionText.getText(),startTime,endTime,
                            detail1LabelText.getText(),detail2LabelText.getText());
                    PhoneAppointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                    validAppointmentCheck(saveAppointment);
                    bhCheck(saveAppointment);
                    scheduleConflictCheck(saveAppointment);
                    DBAppointment.addnAppointment(convertedAppointment,stagedClient,stagedCounselor);
                    clearFields();
                }
                else if(stagedType == AppointmentType.Virtual){
                    VirtualAppointment saveAppointment = new VirtualAppointment(-1, stagedCounselor.getCounselorID(),
                            stagedCounselor.getCounselorName(),stagedClient.getClientID(),stagedClient.getClientName(),
                            typeDropDown.getSelectionModel().getSelectedItem(),descriptionText.getText(),startTime,endTime,
                            detail1LabelText.getText(),detail2LabelText.getText());
                    VirtualAppointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                    validAppointmentCheck(saveAppointment);
                    bhCheck(saveAppointment);
                    scheduleConflictCheck(saveAppointment);
                    DBAppointment.addnAppointment(convertedAppointment,stagedClient,stagedCounselor);
                    clearFields();
                }
                nAppointmentTable.setItems(DBAppointment.getAppointments());


            }
            if(buttonStatus == 2) {
                if(stagedType == AppointmentType.Office){
                    OfficeAppointment saveAppointment = new OfficeAppointment(stagedAppointment.getAppointmentID(),
                            stagedCounselor.getCounselorID(),stagedCounselor.getCounselorName(),
                            stagedClient.getClientID(),stagedClient.getClientName(),typeDropDown.getSelectionModel().getSelectedItem(),
                            descriptionText.getText(),startTime, endTime,detail1LabelText.getText(),detail2LabelText.getText());
                    OfficeAppointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                    validAppointmentCheck(saveAppointment);
                    bhCheck(saveAppointment);
                    scheduleConflictCheck(saveAppointment);
                    noChangeCheck(saveAppointment,stagedAppointment);
                    DBAppointment.editAppointment(convertedAppointment,stagedClient,stagedCounselor);
                    clearFields();

                }
                else if(stagedType == AppointmentType.Phone){
                    PhoneAppointment saveAppointment = new PhoneAppointment(stagedAppointment.getAppointmentID(),
                            stagedCounselor.getCounselorID(),stagedCounselor.getCounselorName(),
                            stagedClient.getClientID(), stagedClient.getClientName(), typeDropDown.getSelectionModel().getSelectedItem(),
                            descriptionText.getText(), startTime,endTime, detail1LabelText.getText(),detail2LabelText.getText());
                    PhoneAppointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                    validAppointmentCheck(saveAppointment);
                    bhCheck(saveAppointment);
                    scheduleConflictCheck(saveAppointment);
                    noChangeCheck(saveAppointment,stagedAppointment);

                    DBAppointment.editAppointment(convertedAppointment,stagedClient,stagedCounselor);
                    clearFields();
                }
                else if(stagedType == AppointmentType.Virtual){
                    VirtualAppointment saveAppointment = new VirtualAppointment(stagedAppointment.getAppointmentID(),
                            stagedCounselor.getCounselorID(), stagedCounselor.getCounselorName(),
                            stagedClient.getClientID(),stagedClient.getClientName(), typeDropDown.getSelectionModel().getSelectedItem(),
                            descriptionText.getText(),startTime,endTime, detail1LabelText.getText(),detail2LabelText.getText());
                    VirtualAppointment convertedAppointment = TimeManipulation.systemToDB(saveAppointment);
                    validAppointmentCheck(saveAppointment);
                    bhCheck(saveAppointment);
                    scheduleConflictCheck(saveAppointment);
                    noChangeCheck(saveAppointment,stagedAppointment);
                    DBAppointment.editAppointment(convertedAppointment,stagedClient,stagedCounselor);
                    clearFields();
                }
                nAppointmentTable.setItems(DBAppointment.getAppointments());
            }
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

    public void onDelete(ActionEvent actionEvent) throws IOException {
        headerLabel.setText("Delete Appointment");
        addButton.setDisable(true);
        editButton.setDisable(true);
        populateFields();

        try {
            Appointment stagedAppointment = (Appointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            int rowsDeleted = -1;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Are you sure you want to delete this appointment?\n" +
                    stagedAppointment.getAppointmentID() + " | " + stagedAppointment.getClientName() + " | " +
                    stagedAppointment.getType() + " | " + stagedAppointment.getStartTime());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                rowsDeleted = DBAppointment.deleteAppointment(stagedAppointment);

                if(rowsDeleted > 0) {
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Appointment Deleted");
                    confirmation.setContentText("The appointment below has been deleted:\n" +
                            stagedAppointment.getAppointmentID() + " | " + stagedAppointment.getClientName() + " | " +
                            stagedAppointment.getType() + " | " + stagedAppointment.getStartTime() );
                    confirmation.showAndWait();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        nAppointmentTable.setItems(DBAppointment.getAppointments());
        clearFields();

    }

    public void showAllAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBAppointment.getAppointments());
    }

    public void showOfficeAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBAppointment.getOfficeAppointments());
    }

    public void showVirtualAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBAppointment.getVirtualAppointments());
    }

    public void showPhoneAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBAppointment.getPhoneAppointments());
    }

    public void setDetailHeader(AppointmentType type) {
        detail1Label.setVisible(true);
        detail2Label.setVisible(true);
        if(type == AppointmentType.Office) {
            detail1Label.setText("Office Building");
            detail2Label.setText("Suite/Room");
        } else if(type == AppointmentType.Phone) {
            detail1Label.setText("Counselor Phone");
            detail2Label.setText("Client Phone");
        } else if (type == AppointmentType.Virtual) {
            detail1Label.setText("Counselor Username");
            detail2Label.setText("Client Username");
        } else {
            detail1Label.setText("Detail1");
            detail2Label.setText("Detail2");
        }
    }






    public class EmptyFields extends Exception {
        public EmptyFields(String s, int i) {super("The " + i + " fields below are required:\n" + s);
        }
    }

    public void emptyFieldsException() throws EmptyFields {
        StringBuilder m = new StringBuilder();
        int errorCounter = 0;
        Boolean nullFields = false;

        if(typeDropDown.getSelectionModel().getSelectedItem() == null) {
            m.append("Type\n");
            ++errorCounter;
            nullFields = true;
        }
        if(clientDropDown.getSelectionModel().getSelectedItem() == null) {
            m.append("Client\n");
            ++errorCounter;
            nullFields = true;
        }
        if(counselorDropDown.getSelectionModel().getSelectedItem() == null) {
            m.append("Counselor\n");
            ++errorCounter;
            nullFields = true;
        }
        if(descriptionText.getText().isEmpty()) {
            m.append("Description\n");
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

    public class NoChanges extends Exception {
        public NoChanges() {super ("No changes have been made to the selected appointment");}
    }

    public void noChangeCheck(Appointment save, Appointment stage) throws NoChanges {
        System.out.println("Stage: " + stage.toString());
        System.out.println("Save: " + save.toString());

        System.out.println(save.equals(stage));
        if(save.equals(stage)) {
            throw new NoChanges();
        }
    }

    public class OutsideBusinessHours extends Exception {
        public OutsideBusinessHours() {super("Outside Business Hours");}
    }

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

     public class InvalidAppointmentDateTime extends Exception {
        public InvalidAppointmentDateTime(String s) {super(s);}
    }

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

    public class ScheduleConflict extends Exception {
        public ScheduleConflict(String s) {super(s);}
    }

    public boolean scheduleConflictCheck(Appointment a) throws ScheduleConflict {
        boolean scheduleConflict = false;
        StringBuilder message = new StringBuilder(a.getCounselorName() + " has a schedule conflict. Please review:\n");
        for(Appointment appointment : DBAppointment.getAppointments()) {
            if(a.getCounselorID() == appointment.getCounselorID()) {
                TimeComparison tcp = new TimeComparison(appointment, a);
                if(TimeManipulation.overlapCheck(tcp)) {
                    message.append(appointment.getAppointmentID() + " | " + a.getClientName() +  " | " + appointment.getDescription() + " | " +
                            appointment.getStartTime()+ "\n");
                    if(a.getAppointmentID() != appointment.getAppointmentID()) {
                        scheduleConflict = true;
                    }
                }
            }
        }
        if(!scheduleConflict) {
            message = new StringBuilder(a.getClientName() + " has a schedule conflict. Please review:\n");
            for(Appointment appointment : DBAppointment.getAppointments()) {
                if(a.getClientID() == appointment.getClientID()) {
                    TimeComparison tcp = new TimeComparison(appointment, a);
                    if(TimeManipulation.overlapCheck(tcp)) {
                        message.append(appointment.getAppointmentID() + " | " + a.getCounselorName() + " | " + appointment.getDescription() + " | " +
                                appointment.getStartTime()+ "\n");
                        if(a.getAppointmentID() != appointment.getAppointmentID()) {
                            scheduleConflict = true;
                        }
                    }
                }
            }
        }
        if(scheduleConflict) {
            throw new ScheduleConflict(message.toString());
        }
        return scheduleConflict;
    }
}
