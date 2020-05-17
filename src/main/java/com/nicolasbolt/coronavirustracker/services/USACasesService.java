package com.nicolasbolt.coronavirustracker.services;

import com.nicolasbolt.coronavirustracker.models.USALocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class USACasesService {

    private final static String USA_VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";

    private List<USALocationStats> allStats = new ArrayList<>();

    public List<USALocationStats> getAllStats() {
        return allStats;
    }

    public int countReportedCases() {
        return allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
    }

    public int countNewCases() {
        return allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<USALocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(USA_VIRUS_DATA_URL)).build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            USALocationStats locationStat = new USALocationStats();
            locationStat.setLocation(record.get("Combined_Key"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPreviousDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }

        this.allStats = newStats;
    }
}
