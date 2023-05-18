package xyz.oribuin.beepet.nms;

import javax.annotation.Nullable;

public class CustomBeeSettings {

    private String name;
    private boolean sitting;
    private double speed;
    private double followDistance;
    private double teleportDistance;

    public CustomBeeSettings(@Nullable String name) {
        this.name = name;
        this.sitting = false;
        this.speed = 1;
        this.followDistance = 7;
        this.teleportDistance = 32;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    public double getFollowDistance() {
        return this.followDistance;
    }

    public void setFollowDistance(double followDistance) {
        this.followDistance = followDistance;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTeleportDistance() {
        return this.teleportDistance;
    }

    public void setTeleportDistance(double teleportDistance) {
        this.teleportDistance = teleportDistance;
    }

}
