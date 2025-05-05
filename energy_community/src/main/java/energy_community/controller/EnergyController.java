package energy_community.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EnergyController {

    @GetMapping("/energy/historical")
    public List<Map<String, Object>> getHistoricalData(
            @RequestParam String start,
            @RequestParam String end) {

        return List.of(
                Map.of(
                        "hour", "2025-05-01T14:00:00",
                        "community_produced", 18.65,
                        "community_used", 18.05,
                        "grid_used", 1.076
                ),
                Map.of(
                        "hour", "2025-05-01T15:00:00",
                        "community_produced", 15.015,
                        "community_used", 14.033,
                        "grid_used", 2.049
                )
        );
    }

    @GetMapping("/energy/current")
    public Map<String, Object> getCurrentPercentage() {
        // Fiktive Beispielwerte – diese würden später aus DB oder Service kommen
        double communityProduced = 18.65;
        double communityUsed = 18.05;
        double gridUsed = 1.076;
        double totalUsed = communityUsed + gridUsed;

        double communityPercentage = (communityUsed / totalUsed) * 100;
        double gridPercentage = (gridUsed / totalUsed) * 100;

        return Map.of(
                "community_percentage", Math.round(communityPercentage * 100.0) / 100.0,
                "grid_percentage", Math.round(gridPercentage * 100.0) / 100.0,
                "community_depleted", true,
                "timestamp", "2025-05-01T14:00:00"
        );
    }
}
