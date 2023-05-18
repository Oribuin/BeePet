package xyz.oribuin.beepet.nms.v1_18_R2.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.bukkit.Location;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import xyz.oribuin.beepet.nms.CustomBeeSettings;

import java.util.Random;

public class FollowGoal extends Goal {

    private static final Random RANDOM = new Random();

    private final CustomBeeSettings bee;
    private final Player owner;
    private final Mob entity;

    public FollowGoal(CustomBeeSettings bee, Player owner, Mob entity) {
        this.bee = bee;
        this.owner = owner;
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        PathNavigation navigation = this.entity.getNavigation();
        Bee bukkitEntity = (Bee) this.entity.getBukkitEntity();

        if (this.bee.isSitting())
            navigation.stop(); // Stop the bee from moving

        if (bukkitEntity.getWorld() != this.owner.getWorld())  {
            bukkitEntity.teleport(this.owner); // Teleport the bee to the owner if they are in a different world
            return true;
        }

        double distance = bukkitEntity.getLocation().distance(this.owner.getLocation());

        if (distance >= this.bee.getTeleportDistance() || distance <= this.bee.getFollowDistance())
            return true;

        // Walk to random spot around the owner
        Location location = this.owner.getLocation().clone().add(
                -RANDOM.nextInt(10) + 5,
                0,
                RANDOM.nextInt(10) - 5
        );
        navigation.moveTo(location.getX(), location.getY(), location.getZ(), this.bee.getSpeed());
        return true;
    }

}
