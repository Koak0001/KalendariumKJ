import java.time.LocalDate;

public class CalendarObject {


    private int year;
    private int month;
    private int day;
    private int dayInYear;
    private LocalDate date;
    private boolean holliday;
    private String dayName;
    private String weekday;
    private int weeknumber;

    @Override
    public String toString() {
        return "CalendarObject{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", dayInYear=" + dayInYear +
                ", date=" + date +
                ", holliday=" + holliday +
                ", dayName='" + dayName + '\'' +
                ", weekday='" + weekday + '\'' +
                ", weekNumber=" + weeknumber +
                '}';
    }

    public CalendarObject(int year, int month, int day, int dayInYear, LocalDate date, boolean holliday, String dayName, String weekday, int weeknumber) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayInYear = dayInYear;
        this.date = date;
        this.holliday = holliday;
        this.dayName = dayName;
        this.weekday = weekday;
        this.weeknumber = weeknumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDayInYear() {
        return dayInYear;
    }

    public void setDayInYear(int dayInYear) {
        this.dayInYear = dayInYear;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isHolliday() {
        return holliday;
    }

    public void setHolliday(boolean holliday) {
        this.holliday = holliday;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getWeeknumber() {
        return weeknumber;
    }

    public void setWeeknumber(int weeknumber) {
        this.weeknumber = weeknumber;
    }
}
