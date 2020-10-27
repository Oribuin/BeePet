package xyz.oribuin.beepet.nms

import net.minecraft.server.v1_16_R2.*
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.entity.Player

class CustomBee(private val player: Player) : EntityBee(EntityTypes.BEE, (player.location.world as CraftWorld).handle) {
    init {
        this.goalSelector.a(1, PathfinderGoalFloat(this))
        this.goalSelector.a(2, FollowPlayerGoal(player, this, 1.0))
        this.goalSelector.a(3, PathfinderGoalTempt(this, 1.1, RecipeItemStack.a(Items.BLAZE_ROD), false))
        this.goalSelector.a(4, PathfinderGoalBreed(this,  0.0))
    }


}