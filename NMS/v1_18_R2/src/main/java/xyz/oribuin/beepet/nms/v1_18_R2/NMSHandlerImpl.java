package xyz.oribuin.beepet.nms.v1_18_R2;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import xyz.oribuin.beepet.nms.CustomBeeSettings;
import xyz.oribuin.beepet.nms.NMSHandler;

import java.util.Random;
import java.util.function.Consumer;

import static xyz.oribuin.beepet.BeePetPlugin.OWNER_KEY;

public class NMSHandlerImpl implements NMSHandler {

    @Override
    public void spawn(Player owner, Location location, CustomBeeSettings options, Consumer<Bee> beeConsumer) {
        ServerBee serverBee = new ServerBee(owner, options);
        Location loc = location.clone();
        if (loc.getWorld() == null) return;

        boolean spawned = ((CraftWorld) loc.getWorld()).getHandle().addFreshEntity(serverBee);
        if (!spawned || OWNER_KEY == null) return;


        Bee bukkitEntity = (Bee) serverBee.getBukkitEntity();
        bukkitEntity.teleport(location); // Teleport the bee to the location
        bukkitEntity.getPersistentDataContainer().set(
                OWNER_KEY,
                PersistentDataType.STRING,
                owner.getUniqueId().toString()
        );

        bukkitEntity.setCustomName(options.getName());
        bukkitEntity.setCustomNameVisible(true);
        beeConsumer.accept(bukkitEntity);
    }

    @Override
    public void remove(Entity entity) {
        entity.remove(); // Remove the bee from the world
    }

}
