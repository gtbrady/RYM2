package Utility;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * TimeManipulation class is used to convert appointments to LocalDateTimes to Strings and vice versa,  perform
 * time zone conversions, and check for overlapping appointment times for exception handling
 */
public class TimeManipulation {

    private static final ZoneId systemZone = ZoneId.systemDefault();
    private static final ZoneId utcZone = ZoneId.of("UTC");
    private static final ZoneId estZone = ZoneId.of("US/Eastern");
    private static final LocalTime openHourStart = LocalTime.of(8, 0);
    private static final LocalTime openHourEnd = LocalTime.of(22, 0);
    private static final ObservableList<String> stringMinutes = FXCollections.observableArrayList("00", "15", "30", "45");
    private static final ObservableList<String> stringHours = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    private static final ObservableList<String> stringPeriod = FXCollections.observableArrayList("AM", "PM");
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Gets the Observable list of strings, which allows the user to select the minute value for an appointment
     *
     * @return The Observable list of strings
     */
    public static ObservableList<String> getStringMinutes() {
        return stringMinutes;
    }

    /**
     * Gets the Observable list of strings, which allows the user to select the hour value for an appointment
     *
     * @return The Observable list of strings
     */
    public static ObservableList<String> getStringHours() {
        return stringHours;
    }

    /**
     * Gets the Observable list of strings, which allows the user to select the AM/PM value for an appointment
     *
     * @return The Observable list of strings
     */
    public static ObservableList<String> getStringPeriod() {
        return stringPeriod;
    }

    /***
     * Gets the starting business hours in local time
     * @return The starting business hours
     */
    public static LocalTime getOpenHourStart() {
        return openHourStart;
    }

    /**
     * Gets the ending business hours in local time
     *
     * @return The ending business hours
     */
    public static LocalTime getOpenHourEnd() {
        return openHourEnd;
    }

    /**
     * Gets the user's system ZoneID
     *
     * @return The user's system ZoneID
     */
    public static ZoneId getSystemZone() {
        return systemZone;
    }

    /**
     * Gets the UTC ZoneID
     *
     * @return UTC ZoneID
     */
    public static ZoneId getUtcZone() {
        return utcZone;
    }

    /**
     * Gets the EST ZoneID
     *
     * @return EST ZoneID
     */
    public static ZoneId getEstZone() {
        return estZone;
    }

    /**
     * Returns an observable list of strings to represent an appointment time (HH:MM:AM) from a LocalDateTime
     *
     * @param l The LocalDateTime being converted
     * @return Observable list of strings
     */
    public static ObservableList<String> timeToString(LocalDateTime l) {

        LocalTime time = l.toLocalTime();
        String h;
        String m;
        String p = "AM";

        if (time.isAfter(LocalTime.of(12, 0, 0, 0)) || time.equals(LocalTime.of(12, 0, 0, 0))) {
            p = "PM";
        }
        if (time.isAfter(LocalTime.of(13, 0, 0, 0)) || time.equals(LocalTime.of(13, 0, 0, 0))) {
            time = time.minusHours(12);
        }
        if (time.getHour() < 10) {
            if (time.getHour() == 0) {
                h = "12";
            } else {
                h = "0" + Integer.toString(time.getHour());
            }
        } else {
            h = Integer.toString(time.getHour());
        }
        if (time.getMinute() == 0) {
            m = "0" + Integer.toString(time.getMinute());
        } else {
            m = Integer.toString(time.getMinute());
        }
        ObservableList<String> timePieces = FXCollections.observableArrayList(h, m, p);

        return timePieces;
    }

    /**
     * Takes multiple strings and a LocalDate to a LocalDateTime object
     *
     * @param h hours
     * @param m minutes
     * @param p period (AM/PM)
     * @param d date
     * @return LocalDateTime
     */
    public static LocalDateTime stringToDate(String h, String m, String p, LocalDate d) {
        String time = h + ":" + m;
        LocalTime lt = LocalTime.parse(time);

        if (p.equals("PM") && !h.equals("12")) {
            lt = lt.plusHours(12);
        } else if (p.equals("AM") && h.equals("12")) {
            lt = lt.minusHours(12);
        }
        LocalDateTime localDateTime = LocalDateTime.of(d, lt);
        return localDateTime;

    }

    /**
     * Takes a LocalDateTime and converts it to the user's system time zone with the same instant as UTC time zone
     *
     * @param l LocalDateTime to be converted
     * @return The converted LocalDateTime
     */
    public static LocalDateTime utcToSystem(LocalDateTime l) {
        ZonedDateTime lzdt = l.atZone(utcZone);
        ZonedDateTime szdt = lzdt.withZoneSameInstant(systemZone);
        LocalDateTime convertedTime = szdt.toLocalDateTime();
        return convertedTime;
    }

    /**
     * Takes a String representation of a LocalDateTime and converts it from UTC time zone to the user's system time
     * zone, and returns it to a String format
     *
     * @param s String to be converted
     * @return The converted string
     */
    public static String stringUTS(String s) {
        LocalDateTime udt = LocalDateTime.parse(s, formatter);
        LocalDateTime sdt = utcToSystem(udt);
        return sdt.format(formatter);
    }


    public static ReportTwo dbtoSystemReportTwo(ReportTwo a) {
        String oas = a.getStartDate();
        String oae = a.getEndDate();
        String uas = TimeManipulation.stringUTS(oas);
        String uae = TimeManipulation.stringUTS(oae);
        ReportTwo updatedReportTwo = new ReportTwo(a.getCounselorName(), a.getDescription(),
                a.getType(), a.getClientName(), uas, uae);
        return updatedReportTwo;


    }

    /**
     * Takes a String representation of a LocalDateTime and converts it from the user's system time zone to UTC time
     * zone, and returns it to a String format
     *
     * @param s String to be converted
     * @return The converted String
     */
    public static String stringSTU(String s) {
        LocalDateTime udt = LocalDateTime.parse(s, formatter);
        LocalDateTime sdt = sysToUtc(udt);
        return sdt.format(formatter);
    }


    public static OfficeAppointment systemToDB(OfficeAppointment a) {

        String oas = a.getStartTime();
        String oae = a.getEndTime();
        String uas = TimeManipulation.stringSTU(oas);
        String uae = TimeManipulation.stringSTU(oae);
        OfficeAppointment updated = new OfficeAppointment(a.getAppointmentID(), a.getCounselorID(), a.getCounselorName(),
                a.getClientID(), a.getClientName(), a.getType(), a.getDescription(), uas, uae,
                a.getBuildingName(), a.getSuiteName());

        return updated;
    }

    public static PhoneAppointment systemToDB(PhoneAppointment a) {

        String oas = a.getStartTime();
        String oae = a.getEndTime();
        String uas = TimeManipulation.stringSTU(oas);
        String uae = TimeManipulation.stringSTU(oae);
        PhoneAppointment updated = new PhoneAppointment(a.getAppointmentID(), a.getCounselorID(), a.getCounselorName(),
                a.getClientID(), a.getClientName(), a.getType(), a.getDescription(), uas, uae,
                a.getCounselorPhone(), a.getClientPhone());

        return updated;
    }

    public static VirtualAppointment systemToDB(VirtualAppointment a) {

        String oas = a.getStartTime();
        String oae = a.getEndTime();
        String uas = TimeManipulation.stringSTU(oas);
        String uae = TimeManipulation.stringSTU(oae);
        VirtualAppointment updated = new VirtualAppointment(a.getAppointmentID(), a.getCounselorID(), a.getCounselorName(),
                a.getClientID(), a.getClientName(), a.getType(), a.getDescription(), uas, uae,
                a.getCounselorUsername(), a.getClientUsername());

        return updated;
    }

    /**
     * Takes a LocalDateTime and converts it to UTC time zone with the same instant as the user's system time zone
     *
     * @param l LocalDateTime to be converted
     * @return Converted LocalDateTime
     */
    public static LocalDateTime sysToUtc(LocalDateTime l) {
        ZonedDateTime lzdt = l.atZone(systemZone);
        ZonedDateTime szdt = lzdt.withZoneSameInstant(utcZone);
        LocalDateTime convertedTime = szdt.toLocalDateTime();
        return convertedTime;
    }

    /**
     * Takes a Time Comparison object and returns a new time comparison object. This is used for businessHourCheck
     * to ensure the appointment the user attempts to save falls within business hours
     *
     * @param tc TimeComparison object to be converted
     * @return Converted TimeComparison object
     */
    public static TimeComparison businessHourPrep(TimeComparison tc) {

        LocalDateTime stagedStart = tc.getStagedStart();
        LocalDateTime stagedEnd = tc.getStagedEnd();
        LocalDateTime compareStart = LocalDateTime.of(stagedStart.toLocalDate(), openHourStart);
        LocalDateTime compareEnd = LocalDateTime.of(stagedEnd.toLocalDate(), openHourEnd);

        ZonedDateTime zonedCompareStart = ZonedDateTime.of(compareStart, estZone);
        ZonedDateTime zonedCompareEnd = ZonedDateTime.of(compareEnd, estZone);
        ZonedDateTime zonedStageStart = ZonedDateTime.of(stagedStart, systemZone);
        ZonedDateTime zonedStageEnd = ZonedDateTime.of(stagedEnd, systemZone);

        ZonedDateTime convertStart = zonedStageStart.withZoneSameInstant(estZone);
        ZonedDateTime convertEnd = zonedStageEnd.withZoneSameInstant(estZone);

        stagedStart = convertStart.toLocalDateTime();
        stagedEnd = convertEnd.toLocalDateTime();
        compareStart = zonedCompareStart.toLocalDateTime();
        compareEnd = zonedCompareEnd.toLocalDateTime();

        TimeComparison etc = new TimeComparison(stagedStart, stagedEnd, compareStart, compareEnd);

        return etc;
    }

    /**
     * Returns true or false depending on if both the proposed starting and end times of the appointment the user
     * attempts to save fall within appropriate business hours, EST.
     *
     * @param tc TimeComparison object being evaluated
     * @return True if the appointment falls within business hours
     */
    public static Boolean businessHourCheck(TimeComparison tc) {
        Boolean validBusinessAppointment;
        Boolean startCheck;
        Boolean endCheck;

        LocalDateTime stagedStart = tc.getStagedStart();
        LocalDateTime stagedEnd = tc.getStagedEnd();
        LocalDateTime compareStart = tc.getCompareStart();
        LocalDateTime compareEnd = tc.getCompareEnd();

        if ((stagedStart.isEqual(compareStart) || stagedStart.isAfter(compareStart)) && stagedStart.isBefore(compareEnd)) {
            startCheck = true;
        } else {
            startCheck = false;
        }
        if (stagedEnd.isAfter(compareStart) && (stagedEnd.isBefore(compareEnd) || stagedEnd.isEqual(compareEnd))) {
            endCheck = true;
        } else {
            endCheck = false;
        }
        if (startCheck && endCheck) {
            validBusinessAppointment = true;
        } else {
            validBusinessAppointment = false;
        }
        return validBusinessAppointment;
    }

    /**
     * Takes a time comparison object and determines if the start/end date times represented overlap
     *
     * @param tc TimeComparison object being evaluated
     * @return True if an overlap is found
     */
    public static Boolean overlapCheck(TimeComparison tc) {
        Boolean overlap = false;
        Boolean beforeCheck = true;
        Boolean afterCheck = true;

        LocalDateTime stagedStart = tc.getStagedStart();
        LocalDateTime stagedEnd = tc.getStagedEnd();
        LocalDateTime compareStart = tc.getCompareStart();
        LocalDateTime compareEnd = tc.getCompareEnd();


        Boolean startToStart = stagedStart.isAfter(compareStart);
        Boolean startToEnd = stagedStart.isAfter(compareEnd) || stagedStart.equals(compareEnd);
        Boolean endToStart = stagedEnd.isBefore(compareStart) || stagedEnd.equals(compareStart);
        Boolean endToEnd = stagedEnd.isBefore(compareEnd) || stagedEnd.equals(compareEnd);

        if (startToStart && startToEnd) {
            if (!endToStart && !endToEnd) {
            } else {
                beforeCheck = false;
            }
        } else {
            beforeCheck = false;
        }

        if (!startToStart && !startToEnd) {
            if (endToStart && endToEnd) {
            } else {
                afterCheck = false;
            }
        } else {
            afterCheck = false;
        }

        if ((beforeCheck && !afterCheck) || (!beforeCheck && afterCheck)) {
        } else {
            overlap = true;
        }
        return overlap;
    }

    /**
     * Creates a timestamp in UTC time. Used for loggin user activity
     *
     * @return The timestamp
     */
    public static Timestamp utcTimestamp() {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime sldt = sysToUtc(ldt);
        Timestamp ts = Timestamp.valueOf(sldt);
        return ts;

    }
}

