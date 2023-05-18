package xyz.oribuin.beepet.nms;

import org.bukkit.Location;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface NMSHandler {

    /**
     * Spawns the bee at the given location
     *
     * @param location The location to spawn the bee
     */
    void spawn(Player owner, Location location, CustomBeeSettings options, Consumer<Bee> beeConsumer);

    /**
     * Removes the bee from the world
     */
    void remove(Entity entity);


}
