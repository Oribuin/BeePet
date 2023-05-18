package xyz.oribuin.beepet.manager;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.manager.Manager;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import xyz.oribuin.beepet.model.UserBee;
import xyz.oribuin.beepet.nms.CustomBeeSettings;
import xyz.oribuin.beepet.nms.NMSAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static xyz.oribuin.beepet.BeePetPlugin.OWNER_KEY;

public class BeeManager extends Manager {

    private final Map<UUID, UserBee> userBees = new HashMap<>();

    public BeeManager(RosePlugin rosePlugin) {
        super(rosePlugin);
    }

    @Override
    public void reload() {
        this.clearBees();
    }

    /**
     * Create a bee pet for a player
     *
     * @param player   The player to create the bee pet for.
     * @param settings The settings of the bee pet.
     */
    public void createFor(Player player, CustomBeeSettings settings) {
        NMSAdapter.getHandler().spawn(player, player.getLocation(), settings, bee -> {
            // Create a new user bee
            UserBee userBee = new UserBee(player.getUniqueId(), bee.getUniqueId(), settings);
            this.userBees.put(player.getUniqueId(), userBee);

            // TODO: Add locale messaging.
            player.sendMessage(Component.text("You have created a bee pet!"));
        });
    }


    /**
     * Remove a bee pet from a player
     *
     * @param player The player to remove the bee pet from.
     */
    public void remove(Player player) {
        UserBee userBee = this.userBees.remove(player.getUniqueId());
        if (userBee == null) return;

        if (userBee.getCachedBee() != null)
            NMSAdapter.getHandler().remove(userBee.getCachedBee());
    }

    /**
     * Clear all bees from the server
     */
    public void clearBees() {
        this.userBees.forEach((uuid, userBee) -> {
            if (userBee.getCachedBee() != null)
                NMSAdapter.getHandler().remove(userBee.getCachedBee());
        });

        this.userBees.clear();
    }

    @Override
    public void disable() {
        // Remove all bees
        this.clearBees();

        for (World world : this.rosePlugin.getServer().getWorlds()) {
            for (Bee bee : world.getEntitiesByClass(Bee.class)) {
                if (bee.getPersistentDataContainer().has(OWNER_KEY, PersistentDataType.STRING))
                    bee.remove();
            }
        }
    }

}
