package xyz.oribuin.beepet.model;

import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.oribuin.beepet.nms.CustomBeeSettings;

import java.util.UUID;


public class UserBee {

    private final @NotNull UUID owner; // The owner of the bee
    private final @NotNull UUID id; // The id of the bee
    private final @NotNull CustomBeeSettings settings; // The settings of the bee
    private @Nullable Bee cachedBee; // The bee entity

    public UserBee(@NotNull UUID owner, @NotNull UUID id, @NotNull CustomBeeSettings settings) {
        this.owner = owner;
        this.id = id;
        this.settings = settings;
        this.cachedBee = null;
    }

    public @NotNull UUID getOwner() {
        return owner;
    }

    public @NotNull UUID getId() {
        return id;
    }

    public @NotNull CustomBeeSettings getSettings() {
        return settings;
    }

    public @Nullable Bee getCachedBee() {
        return this.cachedBee;
    }

    public void setCachedBee(@Nullable Bee bee) {
        this.cachedBee = bee;
    }

}