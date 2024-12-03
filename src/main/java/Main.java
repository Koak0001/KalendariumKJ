import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        List<CalendarObject> calendar2024 = CalendarCall.returnCalendar(2024);
        List<CalendarObject> calendar2025 = CalendarCall.returnCalendar(2025);

        List<CalendarObject> mergedCalendar = filterAndMergeCalendars(calendar2024, calendar2025);

        // Print the merged calendar
        mergedCalendar.forEach(obj -> {
            System.out.printf("Date: %d-%02d-%02d, Weekday: %s, Holiday: %b%n",
                    obj.getYear(),
                    obj.getMonth(),
                    obj.getDay(),
                    obj.getWeekday(),
                    obj.isHoliday());
        });

        // Count weekdays excluding holidays and weekends
        Map<DayOfWeek, Long> weekdayCounts = mergedCalendar.stream()
                .filter(obj -> { // Exclude holidays and weekends
                    DayOfWeek dayOfWeek = obj.getDate().getDayOfWeek();
                    return !obj.isHoliday() && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
                })
                .collect(Collectors.groupingBy(
                        obj -> obj.getDate().getDayOfWeek(), // Group by weekday
                        Collectors.counting()               // Count occurrences
                ));

        // Print weekday counts
        System.out.println("\nWeekday counts (excluding holidays and weekends):");
        weekdayCounts.forEach((day, count) ->
                System.out.printf("%s: %d%n", day, count)
        );
    }



    public static List<CalendarObject> filterAndMergeCalendars(List<CalendarObject> calendar2024, List<CalendarObject> calendar2025) {
        // Define the cutoff dates
        LocalDate start2024 = LocalDate.of(2024, 8, 1); // August 1, 2024
        LocalDate end2024 = LocalDate.of(2024, 12, 31); // December 31, 2024
        LocalDate start2025 = LocalDate.of(2025, 1, 1); // January 1, 2025
        LocalDate end2025 = LocalDate.of(2025, 7, 31); // July 31, 2025

        // Define holiday periods
        List<HolidayPeriod> holidays = Arrays.asList(
                new HolidayPeriod(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 4)),   // August holiday
                new HolidayPeriod(LocalDate.of(2024, 10, 12), LocalDate.of(2024, 10, 20)), // October holiday
                new HolidayPeriod(LocalDate.of(2024, 12, 21), LocalDate.of(2025, 1, 5)),  // December to January
                new HolidayPeriod(LocalDate.of(2025, 2, 8), LocalDate.of(2025, 2, 16)),   // February holiday
                new HolidayPeriod(LocalDate.of(2025, 4, 12), LocalDate.of(2025, 4, 21)),  // April holiday
                new HolidayPeriod(LocalDate.of(2025, 4, 29), LocalDate.of(2025, 5, 1)),   // End of April
                new HolidayPeriod(LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 9)),    // Early May
                new HolidayPeriod(LocalDate.of(2025, 7, 5), LocalDate.of(2025, 7, 31))    // July holiday
        );

        // Filter the 2024 calendar: Apply date range, exclude weekends, and omit holidays
        List<CalendarObject> filtered2024 = calendar2024.stream()
                .filter(obj -> !obj.getDate().isBefore(start2024) && !obj.getDate().isAfter(end2024)) // Within range
                .filter(obj -> {
                    DayOfWeek dayOfWeek = obj.getDate().getDayOfWeek();
                    return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
                }) // Exclude weekends
                .peek(obj -> { // Mark holidays
                    holidays.forEach(period -> {
                        if (!obj.getDate().isBefore(period.getStartDate()) && !obj.getDate().isAfter(period.getEndDate())) {
                            obj.setHoliday(true);
                        }
                    });
                })
                .filter(obj -> !obj.isHoliday()) // Exclude marked holidays
                .collect(Collectors.toList());

        // Filter the 2025 calendar: Apply date range and exclude weekends
        List<CalendarObject> filtered2025 = calendar2025.stream()
                .filter(obj -> !obj.getDate().isBefore(start2025) && !obj.getDate().isAfter(end2025)) // Within range
                .filter(obj -> {
                    DayOfWeek dayOfWeek = obj.getDate().getDayOfWeek();
                    return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
                })
                .peek(obj -> { // Mark holidays
                    holidays.forEach(period -> {
                        if (!obj.getDate().isBefore(period.getStartDate()) && !obj.getDate().isAfter(period.getEndDate())) {
                            obj.setHoliday(true);
                        }
                    });
                })
                .filter(obj -> !obj.isHoliday()) // Exclude marked holidays
                .collect(Collectors.toList());

        // Merge the two filtered lists
        List<CalendarObject> mergedCalendar = new ArrayList<>();
        mergedCalendar.addAll(filtered2024);
        mergedCalendar.addAll(filtered2025);

        // Return the merged calendar
        return mergedCalendar;
    }

    // Helper class to represent holiday periods
    static class HolidayPeriod {
        private final LocalDate startDate;
        private final LocalDate endDate;

        public HolidayPeriod(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }
    }

}

