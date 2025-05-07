package org.example.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.gui.dto.CurrentEnergyEntry;
import org.example.gui.dto.HistoricalEnergyEntry;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EnergyController {

    @FXML
    private TextArea taOutput;

    @FXML
    private TextArea taHistoricalOutput;

    @FXML
    private TextField tfHistoricStart;

    @FXML
    private TextField tfHistoricEnd;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);


    @FXML
    public void onLoadCurrentData(ActionEvent actionEvent) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/energy/current"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            CurrentEnergyEntry entry = mapper.readValue(response.body(), CurrentEnergyEntry.class);

            taOutput.setText("Aktuelle Energiedaten:\n" +
                    "Zeitpunkt: " + entry.getTimestamp() + "\n" +
                    "Community %: " + entry.getCommunityPercentage() + "\n" +
                    "Grid %: " + entry.getGridPercentage() + "\n" +
                    "Community leer: " + entry.isCommunityDepleted());

        } catch (Exception e) {
            taOutput.setText("Fehler beim Laden derr Daten:\n" + e.getMessage());
        }
    }

    @FXML
    public void onLoadHistoricalData(ActionEvent actionEvent) {
        try {
            String start = URLEncoder.encode(tfHistoricStart.getText(), StandardCharsets.UTF_8);
            String end = URLEncoder.encode(tfHistoricEnd.getText(), StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/energy/historical?start=" + start + "&end=" + end))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<HistoricalEnergyEntry> entries = mapper.readValue(response.body(), new TypeReference<>() {
            });

            StringBuilder sb = new StringBuilder();
            sb.append(entries.size()).append(" historische Einträge gefunden:\n\n");

            for (HistoricalEnergyEntry e : entries) {
                sb
                        .append("Zeit: ").append(e.getHour()).append("\n")
                        .append("Produziert: ").append(e.getCommunityProduced()).append(" kWh\n")
                        .append("Verbraucht: ").append(e.getCommunityUsed()).append(" kWh\n")
                        .append("Netzbezug: ").append(e.getGridUsed()).append(" kWh\n\n");
            }

            taHistoricalOutput.setText(sb.toString());

        } catch (Exception e) {
            taHistoricalOutput.setText("Fehler beim Laden historischer Daten:\n" + e.getMessage());
        }
    }
}
