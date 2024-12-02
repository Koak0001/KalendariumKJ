import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalendarCall {

    public static List<CalendarObject> returnCalendar(int a) throws IOException, InterruptedException {

        String yearInput = Integer.toString(a);

        String apiKey = "bsXapqtblZEHocuIrVdlRNfCytyegpZD";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.kalendarium.dk/MinimalCalendar/" + yearInput)) // Replace with your API URL
                .header("Content-Type", "application/json") // Specify the content type
                .GET() // Specify the HTTP method
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<CalendarObject> calendarObjects = new ArrayList<>();

        if (response.statusCode() == 200) {
            // Fetch the response body as a string
            String responseBody = response.body();

            // Use ObjectMapper to parse the JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode days = rootNode.path("days");

            // List to store CalendarObject instances

            // Map JSON to CalendarObject and add to the list
            days.forEach(day -> {
                int year = day.get("year").asInt();
                int month = day.get("month").asInt();
                int dayOfMonth = day.get("day").asInt();
                int dayInYear = day.get("dayInYear").asInt();
                LocalDate date = LocalDate.parse(day.get("date").asText());
                String dayName = day.get("dayName").asText();
                String weekday = day.get("weekday").asText();
                int weekNumber = day.get("weekNumber").asInt();

                // Determine if the day is a holiday
                boolean holiday = day.get("holliday").asBoolean();

                // Check the "events" array for holiday information
                if (day.has("events")) {
                    for (JsonNode event : day.get("events")) {
                        if (event.has("holliday") && event.get("holliday").asBoolean()) {
                            holiday = true;
                            break;
                        }
                    }
                }

                // Create the CalendarObject
                CalendarObject calendarObject = new CalendarObject(year, month, dayOfMonth, dayInYear, date, holiday, dayName, weekday, weekNumber);

                // Add to the list
                calendarObjects.add(calendarObject);
            });

            // Print each CalendarObject
//            calendarObjects.forEach(obj -> {
//                System.out.printf("Date: %d-%02d-%02d, Day: %s, Weekday: %s, Holiday: %b%n",
//                        obj.getYear(),
//                        obj.getMonth(),
//                        obj.getDay(),
//                        obj.getDayName(),
//                        obj.getWeekday(),
//                        obj.isHolliday());
//            });

        } else {
            System.out.println("Failed to fetch data: " + response.statusCode());
        }
        return calendarObjects;
    }
}
