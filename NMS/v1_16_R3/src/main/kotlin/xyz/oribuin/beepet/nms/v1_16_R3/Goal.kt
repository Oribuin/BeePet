package xyz.oribuin.beepet.nms.v1_16_R3

import net.minecraft.server.v1_16_R3.Entity
import net.minecraft.server.v1_16_R3.EntityInsentient
import net.minecraft.server.v1_16_R3.PathfinderGoal
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArmorStand
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Bee
import org.bukkit.entity.Player
import java.util.*

class Goal(
    private val owner: Player,
    private val speed: Double,
    private val followDistance: Double,
    private val tpDistance: Double,
    private val entity: EntityInsentient,
    private val sitting: Boolean
) : PathfinderGoal() {

    override fun a(): Boolean {
        val navigation = entity.navigation
        val bukkitEntity = entity.bukkitEntity as Bee

        if (sitting) return true

        // If in different worlds,
        if (bukkitEntity.world != owner.world) {
            bukkitEntity.teleport(owner)
            return true
        }

        // Define distance
        val distance = bukkitEntity.location.distance(owner.location)

        if (distance < followDistance) return true

        // Walk to the player
        if (distance < tpDistance) {

            val loc = owner.location.clone().add(-Random().nextInt(3).toDouble(), 0.0, Random().nextInt(3).toDouble())

            val temp = bukkitEntity.world.spawn(loc, ArmorStand::class.java) {
                it.isInvulnerable = true
                it.isInvisible = true
                it.isSmall = true
                it.setGravity(false)
            }

            navigation.a((temp as CraftArmorStand).handle as Entity, speed)
            temp.remove()
            return true
        }

        bukkitEntity.teleport(owner)
        return true
    }

}