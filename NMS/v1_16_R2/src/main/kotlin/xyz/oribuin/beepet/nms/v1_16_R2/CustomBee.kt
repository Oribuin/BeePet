package xyz.oribuin.beepet.nms.v1_16_R2

import net.minecraft.server.v1_16_R2.EntityBee
import net.minecraft.server.v1_16_R2.EntityTypes
import net.minecraft.server.v1_16_R2.PathfinderGoalBreed
import net.minecraft.server.v1_16_R2.PathfinderGoalFloat
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.nms.BeeOptions

class CustomBee(player: Player, config: BeeOptions, sitting: Boolean) : EntityBee(EntityTypes.BEE, (player.location.world as CraftWorld).handle) {

    init {
        this.goalSelector.a(1, PathfinderGoalFloat(this))
        this.goalSelector.a(2, Goal(player, config.speed, config.followDistance, config.tpDistance, this, sitting))
        this.goalSelector.a(3, PathfinderGoalBreed(this, 0.0))
    }

}