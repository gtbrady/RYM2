package Utility;

import Model.Appointment;
import Model.nAppointment;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Time comparison class that takes date/time objects. Used in the TimeManipulation class to compare
 * appointment start/date times. Overloaded constructors are used for specific comparisons, so null
 * fields are possible depending on the use
 */
public class TimeComparison {
    private LocalDateTime stagedStart;
    private LocalDateTime stagedEnd;
    private LocalDateTime compareStart;
    private LocalDateTime compareEnd;
    private LocalDate date;

    /**
     *
     * @param ss stagedStart
     * @param se stagedEnd
     * @param cs compareStart
     * @param ce compareEnd
     */
    public TimeComparison(LocalDateTime ss, LocalDateTime se, LocalDateTime cs, LocalDateTime ce) {
        this.stagedStart = ss;
        this.stagedEnd = se;
        this.compareStart = cs;
        this.compareEnd = ce;

    }

    /**
     *
     * @param ss stagedStart
     */
    public TimeComparison(LocalDateTime ss) {
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(15);
        this.stagedStart = ss;
        this.compareStart = ldt;
    }

    /**
     *
     * @param stage stage appointment - converted to start/end date/times
     * @param compare compare appointment - converted to start/end date/times
     */
    public TimeComparison(Appointment stage, Appointment compare) {
        this.stagedStart = LocalDateTime.parse(stage.getStartDT(), TimeManipulation.formatter);
        this.stagedEnd = LocalDateTime.parse(stage.getEndDT(), TimeManipulation.formatter);
        this.compareStart = LocalDateTime.parse(compare.getStartDT(), TimeManipulation.formatter);
        this.compareEnd = LocalDateTime.parse(compare.getEndDT(), TimeManipulation.formatter);

    }

    //update RYM2
    public TimeComparison(nAppointment stage, nAppointment compare) {
        this.stagedStart = LocalDateTime.parse(stage.getStartTime(), TimeManipulation.formatter);
        this.stagedEnd = LocalDateTime.parse(stage.getEndTime(), TimeManipulation.formatter);
        this.compareStart = LocalDateTime.parse(compare.getStartTime(), TimeManipulation.formatter);
        this.compareEnd = LocalDateTime.parse(compare.getEndTime(), TimeManipulation.formatter);

    }

    /**
     *
     * @param stage stage appointment - converted to start/end date/times
     */
    public TimeComparison(Appointment stage) {
        this.stagedStart = LocalDateTime.parse(stage.getStartDT(), TimeManipulation.formatter);
        this.stagedEnd = LocalDateTime.parse(stage.getEndDT(), TimeManipulation.formatter);
        this.date = stagedStart.toLocalDate();
        this.compareStart = LocalDateTime.of(date, TimeManipulation.getOpenHourStart());
        this.compareEnd = LocalDateTime.of(date, TimeManipulation.getOpenHourEnd());
    }

    //updated RYM2
    public TimeComparison(nAppointment stage) {
        this.stagedStart = LocalDateTime.parse(stage.getStartTime(), TimeManipulation.formatter);
        this.stagedEnd = LocalDateTime.parse(stage.getEndTime(), TimeManipulation.formatter);
        this.date = stagedStart.toLocalDate();
        this.compareStart = LocalDateTime.of(date, TimeManipulation.getOpenHourStart());
        this.compareEnd = LocalDateTime.of(date, TimeManipulation.getOpenHourEnd());
    }

    /**
     * Gets the staged start date/time
     * @return The staged start date/time
     */
    public LocalDateTime getStagedStart() {
        return stagedStart;
    }

    /**
     * Gets the staged end date/time
     * @return The staged end date/time
     */
    public LocalDateTime getStagedEnd() {
        return stagedEnd;
    }

    /**
     * Gets the compare start date/time
     * @return The compare start date/time
     */
    public LocalDateTime getCompareStart() {
        return compareStart;
    }

    /**
     * Gets the compare end date/time
     * @return The compare end date/time
     */
    public LocalDateTime getCompareEnd() {
        return compareEnd;
    }


    /**
     *
     * @return String representation of a TimeComparison object
     */
    @Override
    public String toString() {
        return "TimeComparison{" +
                "stagedStart=" + stagedStart +
                ", stagedEnd=" + stagedEnd +
                ", compareStart=" + compareStart +
                ", compareEnd=" + compareEnd +
                '}';
    }

}
