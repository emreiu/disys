package energy_community.controller;

import energy_community.model.dto.CurrentEnergyEntry;
import energy_community.model.dto.HistoricalEnergyEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class EnergyController {

    @GetMapping("/energy/current")
    public CurrentEnergyEntry getCurrentEnergyData() {
        return new CurrentEnergyEntry(
                LocalDateTime.of(2025, 5, 1, 14, 0),
                94.37,
                5.63,
                true
        );
    }

    @GetMapping("/energy/historical")
    public List<HistoricalEnergyEntry> getHistoricalEnergyData(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return List.of(
                new HistoricalEnergyEntry(
                        LocalDateTime.of(2025, 5, 1, 14, 0),
                        18.65,
                        18.05,
                        1.076
                ),
                new HistoricalEnergyEntry(
                        LocalDateTime.of(2025, 5, 1, 15, 0),
                        15.015,
                        14.033,
                        2.049
                )
        );
    }
}
