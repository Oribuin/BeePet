package xyz.oribuin.beepet.nms

import net.minecraft.server.v1_16_R3.Entity
import net.minecraft.server.v1_16_R3.EntityInsentient
import net.minecraft.server.v1_16_R3.PathfinderGoal
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import xyz.oribuin.beepet.BeePet
import xyz.oribuin.beepet.util.PluginUtils.async

/**
 * @author Matt
 */
internal class FollowPlayerGoal(private val plugin: BeePet, private val owner: Player, private val petInsentient: EntityInsentient, private val movementSpeed: Double) : PathfinderGoal() {

    private val navigation = petInsentient.navigation

    private var followDistance = 5.0
    private val tpDistance = 10.0

    /**
     * Main ticking class for the PathfinderGoal
     */
    override fun a(): Boolean {

        // Gets the distance between the player and the pet
        val ownerLocation = owner.location

        if (ownerLocation.world != petInsentient.bukkitEntity.world) {
            petInsentient.bukkitEntity.location.world = ownerLocation.world
            return true
        }

        val distance = petInsentient.bukkitEntity.location.distance(ownerLocation)

        // Checks if distance is less than the follow distance
        if (distance < followDistance) return true

        // Checks if distance is less than the tp distance
        if (distance < tpDistance) {
            // Walks to player
            navigation.a((owner as CraftPlayer).handle as Entity, movementSpeed)
            return true
        }

        // Teleports to owner
        async(plugin, Runnable {
            petInsentient.setPosition(ownerLocation.x, ownerLocation.y, ownerLocation.z)
            petInsentient.bukkitEntity.teleport(ownerLocation)
            petInsentient.bukkitEntity.location.world = ownerLocation.world
        })
        return true

    }

}
