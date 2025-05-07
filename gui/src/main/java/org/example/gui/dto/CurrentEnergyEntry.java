package org.example.gui.dto;

import java.time.LocalDateTime;

public class CurrentEnergyEntry {
    private LocalDateTime timestamp;
    private double communityPercentage;
    private double gridPercentage;
    private boolean communityDepleted;

    public CurrentEnergyEntry() {
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getCommunityPercentage() {
        return communityPercentage;
    }

    public void setCommunityPercentage(double communityPercentage) {
        this.communityPercentage = communityPercentage;
    }

    public double getGridPercentage() {
        return gridPercentage;
    }

    public void setGridPercentage(double gridPercentage) {
        this.gridPercentage = gridPercentage;
    }

    public boolean isCommunityDepleted() {
        return communityDepleted;
    }

    public void setCommunityDepleted(boolean communityDepleted) {
        this.communityDepleted = communityDepleted;
    }
}
