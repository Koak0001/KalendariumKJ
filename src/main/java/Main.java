import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Demo les go.

        List<CalendarObject> calendar2024 = CalendarCall.returnCalendar(2024);
        List<CalendarObject> calendar2025 = CalendarCall.returnCalendar(2025);

        List<CalendarObject> mergedCalendar = filterAndMergeCalendars(calendar2024, calendar2025);

        mergedCalendar.forEach(obj -> {
            System.out.printf("Date: %d-%02d-%02d, Day: %s, Weekday: %s, Holiday: %b%n",
                    obj.getYear(),
                    obj.getMonth(),
                    obj.getDay(),
                    obj.getDayName(),
                    obj.getWeekday(),
                    obj.isHolliday());
        });

    }

    public static List<CalendarObject> filterAndMergeCalendars(List<CalendarObject> calendar2024, List<CalendarObject> calendar2025) {
        // Define the cutoff dates
        LocalDate start2024 = LocalDate.of(2024, 8, 1); // August 1, 2024
        LocalDate end2024 = LocalDate.of(2024, 12, 31); // December 31, 2024
        LocalDate start2025 = LocalDate.of(2025, 1, 1); // January 1, 2025
        LocalDate end2025 = LocalDate.of(2025, 7, 31); // July 31, 2025

        // Filter the 2024 calendar: Keep only dates between August 1 and December 31
        List<CalendarObject> filtered2024 = calendar2024.stream()
                .filter(obj -> !obj.getDate().isBefore(start2024) && !obj.getDate().isAfter(end2024))
                .collect(Collectors.toList());

        // Filter the 2025 calendar: Keep only dates between January 1 and July 31
        List<CalendarObject> filtered2025 = calendar2025.stream()
                .filter(obj -> !obj.getDate().isBefore(start2025) && !obj.getDate().isAfter(end2025)).
                        collect(Collectors.toList());

        // Merge the two filtered lists
        List<CalendarObject> mergedCalendar = new ArrayList<>();
        mergedCalendar.addAll(filtered2024);
        mergedCalendar.addAll(filtered2025);

        // Return the merged calendar
        return mergedCalendar;
    }
}
