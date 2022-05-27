package Controller;

import DAO.*;
import Model.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ComboBox <String> detail1DropDown;
    public ComboBox <String> detail2DropDown;

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
    private static Counselor stagedCounselor;
    private static Client stagedClient;
    private static Office stagedOffice;
    private static Suite stagedSuite;

    private static String stagedDetail1;
    private static String stagedDetail2;

    private static int debugTracker = 1;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nAppointmentTable.setItems(DBnAppointment.getAppointments());
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
        System.out.println(debugTracker++ + ",enableFields");
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

            System.out.println(debugTracker++ + ",populateFields");
        nAppointment theAppointment = (nAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            if (theAppointment == null) {
                throw new NullPointerException();
            }
        stagedType = theAppointment.getType();

        setDetailHeader(stagedType);
        if(theAppointment instanceof OfficeAppointment) {
            System.out.println(debugTracker++ + ",populateFields,theAppointment instanceof OfficeAppointment," + (theAppointment instanceof OfficeAppointment));
            stagedOfficeAppointment = (OfficeAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            stagedOffice = new Office(stagedOfficeAppointment.getBuildingName());
            stagedSuite = new Suite(stagedOfficeAppointment.getSuiteName());
            stagedCounselor = new Counselor(stagedOfficeAppointment.getCounselorID());
            stagedClient = new Client(stagedOfficeAppointment.getClientID());
            stagedDetail1 = stagedOffice.getBuildingName();
            //detail1DropDown.setDisable(false);
            stagedDetail2 = stagedSuite.getSuiteName();
            //detail2DropDown.setDisable(false);
        }
        else if (theAppointment instanceof VirtualAppointment) {
            System.out.println(debugTracker++ + ",populateFields,theAppointment instanceof VirtualAppointment," + (theAppointment instanceof VirtualAppointment));
            stagedVirtualAppointment = (VirtualAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            stagedCounselor = new Counselor(stagedVirtualAppointment.getCounselorID());
            stagedClient = new Client(stagedVirtualAppointment.getClientID());
            stagedDetail1 = stagedCounselor.getCounselorUsername();
            stagedDetail2 = stagedClient.getClientUsername();

        }
        else if (theAppointment instanceof PhoneAppointment) {
            System.out.println(debugTracker++ + ",enableFields,theAppointment instanceof PhoneAppointment," + (theAppointment instanceof PhoneAppointment));
            stagedPhoneAppointment = (PhoneAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            stagedCounselor = new Counselor(stagedPhoneAppointment.getCounselorID());
            stagedClient = new Client(stagedPhoneAppointment.getClientID());
            stagedDetail1 = stagedCounselor.getCounselorPhone();
            stagedDetail2 = stagedClient.getClientPhone();


        }

        Timestamp sts =Timestamp.valueOf(theAppointment.getStartTime());
        Timestamp ets = Timestamp.valueOf(theAppointment.getEndTime());
        LocalDate sdt = sts.toLocalDateTime().toLocalDate();
        LocalDateTime slt = sts.toLocalDateTime();
        LocalDateTime elt = ets.toLocalDateTime();
        ObservableList<String> startTimePieces = TimeManipulation.timeToString(slt);
        ObservableList<String> endTimePieces = TimeManipulation.timeToString(elt);

        descriptionText.setText(theAppointment.getDescription());
        counselorDropDown.setValue(stagedCounselor);
        clientDropDown.setValue(stagedClient);
        typeDropDown.setValue(stagedType);
        System.out.println(debugTracker++ + ",populateFields,1.detail1DropDown.getValue()," + detail1DropDown.getValue());
        System.out.println(debugTracker++ + ",populateFields,1.detail2DropDown.getValue()," + detail2DropDown.getValue());

        detail1DropDown.setValue(stagedDetail1);
        detail2DropDown.setValue(stagedDetail2);

        System.out.println(debugTracker++ + ",populateFields,2.detail1DropDown.getValue()," + detail1DropDown.getValue());
        System.out.println(debugTracker++ + ",populateFields,2.detail2DropDown.getValue()," + detail2DropDown.getValue());



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
        System.out.println(debugTracker++ + ",clearFields");
        System.out.println(debugTracker++ + ",clearFields,1.detail1DropDown.getValue()," + detail1DropDown.getValue());
        System.out.println(debugTracker++ + ",clearFields,1.detail2DropDown.getValue()," + detail2DropDown.getValue());
        headerLabel.setText("Appointments");
        detail1Label.setText("Detail1");
        detail2Label.setText("Detail2");
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
        detail1DropDown.setDisable(true);
        detail1DropDown.getSelectionModel().clearSelection();
        detail1DropDown.setValue(null);
        detail2DropDown.setDisable(true);
        detail2DropDown.getSelectionModel().clearSelection();
        detail2DropDown.setValue(null);
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


        stagedPhoneAppointment = null;
        stagedVirtualAppointment = null;
        stagedOfficeAppointment = null;
        stagedType = null;
        stagedCounselor = null;
        stagedClient = null;
        stagedOffice = null;
        stagedSuite = null;

        System.out.println(debugTracker++ + ",clearFields,2.detail1DropDown.getValue()," + detail1DropDown.getValue());
        System.out.println(debugTracker++ + ",clearFields,2.detail2DropDown.getValue()," + detail2DropDown.getValue());
    }

    //this is disabled and we are almost functional
    public void onType(ActionEvent actionEvent) throws IOException{
        System.out.println(debugTracker++ + ",onType");
        System.out.println(debugTracker++ + ",onType,1.detail1DropDown.getValue()," + detail1DropDown.getValue());
        System.out.println(debugTracker++ + ",onType,1.detail2DropDown.getValue()," + detail2DropDown.getValue());


        stagedType = typeDropDown.getSelectionModel().getSelectedItem();
        System.out.println(debugTracker++ + ",onType,stagedType == AppointmentType.Office," + (stagedType == AppointmentType.Office));
        System.out.println(debugTracker++ + ",onType,stagedOfficeAppointment != null," + (stagedOfficeAppointment != null));
        System.out.println(debugTracker++ + ",onType,stagedType == AppointmentType.Phone," + (stagedType == AppointmentType.Phone));
        System.out.println(debugTracker++ + ",onType,stagedType == AppointmentType.Virtual," + (stagedType == AppointmentType.Virtual));
        System.out.println(debugTracker++ + ",onType,counselorDropDown.getValue() != null," + (counselorDropDown.getValue() != null));
        System.out.println(debugTracker++ + ",onType,clientDropDown.getValue() != null," + (clientDropDown.getValue() != null));



        if(stagedType == AppointmentType.Office) {


            detail1DropDown.setValue(null);
            detail2DropDown.setValue(null);

            if(stagedOfficeAppointment != null) {

                detail1DropDown.setValue(stagedOfficeAppointment.getBuildingName());
                detail1DropDown.getEditor().setText(stagedOfficeAppointment.getBuildingName());
                detail2DropDown.setValue(stagedOfficeAppointment.getSuiteName());
                detail2DropDown.getEditor().setText(stagedOfficeAppointment.getSuiteName());

            }

            ObservableList<String> officeNameArray = DBOffice.getOfficeNames();
            detail1DropDown.setDisable(false);
            detail2DropDown.setDisable(false);
            detail1DropDown.setItems(officeNameArray);
            System.out.println(debugTracker++ + ",onType,2.detail1DropDown.getValue()," + detail1DropDown.getValue());
            System.out.println(debugTracker++ + ",onType,detail1DropDown.getEditor().getText()," + detail1DropDown.getEditor().getText());
            System.out.println(debugTracker++ + ",onType,2.detail2DropDown.getValue()," + detail2DropDown.getValue());
            System.out.println(debugTracker++ + ",onType,detail2DropDown.getEditor().getText()," + detail2DropDown.getEditor().getText());


        } else if(stagedType == AppointmentType.Phone) {
            detail1DropDown.setDisable(true);
            detail2DropDown.setDisable(true);
            if(counselorDropDown.getValue() != null) {

                ObservableList<String> phoneDetail1 = FXCollections.observableArrayList();

                System.out.println(debugTracker++ + ",onType,detail1Items," + phoneDetail1);
                phoneDetail1.add(stagedCounselor.getCounselorPhone());
                System.out.println(debugTracker++ + ",onType,detail1Items," + phoneDetail1);
                detail1DropDown.setValue(stagedCounselor.getCounselorPhone());
                detail1DropDown.getEditor().setText(stagedCounselor.getCounselorPhone());
                System.out.println(debugTracker++ + ",onType,3.detail1DropDown.getValue()," + detail1DropDown.getValue());
                System.out.println(debugTracker++ + ",onType,detail1DropDown.getEditor().getText()," + detail1DropDown.getEditor().getText());
                System.out.println(debugTracker++ + ",onType,detail1DropDown.getEditor().getText()," + detail1DropDown.getEditor().getText());

            }
            if(clientDropDown.getValue() != null) {
                ObservableList<String> phoneDetail2 = FXCollections.observableArrayList();
                System.out.println(debugTracker++ + ",onType,detail2Items," + phoneDetail2);
                phoneDetail2.add(stagedClient.getClientPhone());
                System.out.println(debugTracker++ + ",onType,detail2Items," + phoneDetail2);
                detail2DropDown.setValue(stagedClient.getClientPhone());
                detail2DropDown.getEditor().setText(stagedClient.getClientPhone());
                System.out.println(debugTracker++ + ",onType,3.detail2DropDown.getValue()," + detail2DropDown.getValue());
                System.out.println(debugTracker++ + ",onType,detail2DropDown.getEditor().getText()," + detail2DropDown.getEditor().getText());

            }


        } else if (stagedType == AppointmentType.Virtual) {
            detail1DropDown.setDisable(true);
            detail2DropDown.setDisable(true);
            if(counselorDropDown.getValue() != null) {
                ObservableList<String> virtualDetail1 = FXCollections.observableArrayList();
                System.out.println(debugTracker++ + ",onType,detail1Items," + virtualDetail1);
                virtualDetail1.add(stagedCounselor.getCounselorUsername());
                System.out.println(debugTracker++ + ",onType,detail1Items," + virtualDetail1);
                detail1DropDown.setValue(stagedCounselor.getCounselorUsername());
                detail1DropDown.getEditor().setText(stagedCounselor.getCounselorUsername());
                System.out.println(debugTracker++ + ",onType,4.detail1DropDown.getValue()," + detail1DropDown.getValue());
                System.out.println(debugTracker++ + ",onType,detail1DropDown.getEditor().getText()," + detail1DropDown.getEditor().getText());
            }
            if(clientDropDown.getValue() != null) {
                ObservableList<String> virtualDetail2 = FXCollections.observableArrayList();
                System.out.println(debugTracker++ + ",onType,detail2Items," + virtualDetail2);
                virtualDetail2.add(stagedClient.getClientUsername());
                System.out.println(debugTracker++ + ",onType,detail2Items," + virtualDetail2);
                detail2DropDown.setValue(stagedClient.getClientUsername());
                detail2DropDown.getEditor().setText(stagedClient.getClientUsername());
                System.out.println(debugTracker++ + ",onType,3.detail2DropDown.getValue()," + detail2DropDown.getValue());
                System.out.println(debugTracker++ + ",onType,detail2DropDown.getEditor().getText()," + detail2DropDown.getEditor().getText());

            }
        }
    }

    /*
    THIS IS THE CULPRIT
    not filtering for the office the type drop down - it is assuming everything is an office whenever something changes
     */
    public void onOffice() {
        System.out.println(debugTracker++ + ",onOffice");
        if(stagedType == AppointmentType.Office) {
            if (detail1DropDown.getValue() != null) {
                stagedOffice = new Office(detail1DropDown.getValue());

                ObservableList<String> selectedSuites = FXCollections.observableArrayList();
                for(Suite s : suites) {
                    if(s.getOfficeID() == stagedOffice.getOfficeID()) {
                        selectedSuites.add(s.getSuiteName());
                    }
                }
               // detail2DropDown.setItems(selectedSuites);

            }

            /*for(String s:selectedSuites) {

            }*/

        }
        else {

        }
    }

    public void onClient(ActionEvent actionEvent) {
        System.out.println(debugTracker++ + ",onClient");
        stagedClient = clientDropDown.getSelectionModel().getSelectedItem();
        if(stagedType != null) {
            if(stagedType == AppointmentType.Phone) {
                detail2DropDown.setValue(stagedClient.getClientPhone());

            } else if(stagedType == AppointmentType.Virtual) {
                detail2DropDown.setValue(stagedClient.getClientUsername());

            }
        }

    }

    public void onCounselor(ActionEvent actionEvent)  {
        System.out.println(debugTracker++ + ",onCounselor");
        stagedCounselor = counselorDropDown.getSelectionModel().getSelectedItem();
        if(stagedType != null) {
            if(stagedType == AppointmentType.Phone) {
                detail1DropDown.setValue(stagedCounselor.getCounselorPhone());

            } else if (stagedType == AppointmentType.Virtual) {
                detail1DropDown.setValue(stagedCounselor.getCounselorUsername());


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
            nAppointment stagedAppointment = (nAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            //stage drop downs


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


        } catch (EmptyFields e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Missing information");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        }
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        headerLabel.setText("Delete Appointment");
        addButton.setDisable(true);
        editButton.setDisable(true);
        populateFields();

        try {
            nAppointment stagedAppointment = (nAppointment) nAppointmentTable.getSelectionModel().getSelectedItem();
            int rowsDeleted = -1;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Are you sure you want to delete this appointment?\n" +
                    stagedAppointment.getAppointmentID() + " | " + stagedAppointment.getClientName() + " | " +
                    stagedAppointment.getType() + " | " + stagedAppointment.getStartTime());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                rowsDeleted = DBnAppointment.deleteAppointment(stagedAppointment);

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
        nAppointmentTable.setItems(DBnAppointment.getAppointments());
        onCancel(actionEvent);

    }

    public void showAllAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBnAppointment.getAppointments());
    }

    public void showOfficeAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBnAppointment.getOfficeAppointments());
    }

    public void showVirtualAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBnAppointment.getVirtualAppointments());
    }

    public void showPhoneAppointments(ActionEvent actionEvent) {
        nAppointmentTable.setItems(DBnAppointment.getPhoneAppointments());
    }

    public void setDetailHeader(AppointmentType type) {
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

    public String appointmentDetail1(nAppointment appointment ) {

        String detailOne;
        if(appointment instanceof OfficeAppointment) {
            detailOne = ((OfficeAppointment) appointment).getBuildingName();
        } else if (appointment instanceof VirtualAppointment) {
            detailOne = ((VirtualAppointment) appointment).getCounselorUsername();
        }
        else if (appointment instanceof PhoneAppointment) {
            detailOne = ((PhoneAppointment) appointment).getCounselorPhone();
        }
        else {
            detailOne = "error";
        }
        return detailOne;
    }

    public String appointmentDetail2(nAppointment appointment ) {

        String detailTwo;
        if(appointment instanceof OfficeAppointment) {
            detailTwo = ((OfficeAppointment) appointment).getSuiteName();
        } else if (appointment instanceof VirtualAppointment) {
            detailTwo = ((VirtualAppointment) appointment).getClientUsername();
        }
        else if (appointment instanceof PhoneAppointment) {
            detailTwo = ((PhoneAppointment) appointment).getClientPhone();
        }
        else {
            detailTwo = "error";
        }
        return detailTwo;
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

    public void noChangeCheck(nAppointment save, nAppointment stage) throws NoChanges {
        if(save.equals(stage))
            throw new NoChanges();
    }

    public class OutsideBusinessHours extends Exception {
        public OutsideBusinessHours() {super("Outside Business Hours");}
    }

    public boolean bhCheck(nAppointment a) throws OutsideBusinessHours {

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

    public boolean scheduleConflictCheckClient(nAppointment a) throws ScheduleConflict {
        boolean scheduleConflict = false;
        StringBuilder message = new StringBuilder(a.getClientName() + " has an appointment scheduled for that time. Please review:\n");
        for(nAppointment appointment : DBnAppointment.getAppointments()) {
            if(a.getClientID() == appointment.getClientID()) {
                TimeComparison tcp = new TimeComparison(appointment, a);
                if(TimeManipulation.overlapCheck(tcp)) {
                    message.append(appointment.getAppointmentID() + " | " + appointment.getDescription() + " | " +
                            appointment.getStartTime()+ "\n");
                    if(a.getAppointmentID() != appointment.getAppointmentID()) {
                        scheduleConflict = true;
                    }
                }
            }/*
            if(scheduleConflict) {
                throw new ScheduleConflict(message.toString());
            } */ //not sure what this is doing here ^
        }
        if(scheduleConflict) {
            throw new ScheduleConflict(message.toString());
        }
        return scheduleConflict;
    }

    public boolean scheduleConflictCheckCounselor(nAppointment a) throws ScheduleConflict {
        boolean scheduleConflict = false;
        StringBuilder message = new StringBuilder(a.getCounselorName() + " has an appointment scheduled for that time. Please review:\n");
        for(nAppointment appointment : DBnAppointment.getAppointments()) {
            if(a.getCounselorID() == appointment.getCounselorID()) {
                TimeComparison tcp = new TimeComparison(appointment, a);
                if(TimeManipulation.overlapCheck(tcp)) {
                    message.append(appointment.getAppointmentID() + " | " + appointment.getDescription() + " | " +
                            appointment.getStartTime()+ "\n");
                    if(a.getAppointmentID() != appointment.getAppointmentID()) {
                        scheduleConflict = true;
                    }
                }
            }/*
            if(scheduleConflict) {
                throw new ScheduleConflict(message.toString());
            } */ //not sure what this is doing here ^
        }
        if(scheduleConflict) {
            throw new ScheduleConflict(message.toString());
        }
        return scheduleConflict;
    }



}
