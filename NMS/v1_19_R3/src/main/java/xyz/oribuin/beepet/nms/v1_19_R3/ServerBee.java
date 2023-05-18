package xyz.oribuin.beepet.nms.v1_19_R3;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Player;
import xyz.oribuin.beepet.nms.CustomBeeSettings;
import xyz.oribuin.beepet.nms.v1_19_R3.goal.FollowGoal;

public class ServerBee extends Bee {

    private final Player owner;
    private final CustomBeeSettings bee;

    public ServerBee(final Player owner, CustomBeeSettings bee) {
        super(EntityType.BEE, ((CraftWorld) owner.getWorld()).getHandle());

        this.owner = owner;
        this.bee = bee;

        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);

        this.goalSelector.removeAllGoals(goal -> true); // Remove all goals from the bee
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FollowGoal(this.bee, this.owner, this)); // Add the goal to the bee
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    public Player getOwner() {
        return this.owner;
    }

    public CustomBeeSettings getBee() {
        return this.bee;
    }

}
