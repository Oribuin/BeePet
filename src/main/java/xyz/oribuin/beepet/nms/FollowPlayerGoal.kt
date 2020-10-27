package xyz.oribuin.beepet.nms

import net.minecraft.server.v1_16_R2.Entity
import net.minecraft.server.v1_16_R2.EntityInsentient
import net.minecraft.server.v1_16_R2.PathfinderGoal
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer
import org.bukkit.entity.Player

/**
 * @author Matt
 */
internal class FollowPlayerGoal(private val owner: Player, private val petInsentient: EntityInsentient, private val movementSpeed: Double) : PathfinderGoal() {

    private val navigation = petInsentient.navigation

    private var followDistance = 5.0
    private val tpDistance = 10.0

    /**
     * Main ticking class for the PathfinderGoal
     */
    override fun a(): Boolean {

        // Gets the distance between the player and the pet
        val ownerLocation = owner.location
        val distance = petInsentient.bukkitEntity.location.distance(ownerLocation)

        if (ownerLocation.world != petInsentient.bukkitEntity.world) {
            petInsentient.bukkitEntity.location.world = ownerLocation.world
        }

        // Checks if distance is less than the follow distance
        if (distance < followDistance) return true

        // Checks if distance is less than the tp distance
        if (distance < tpDistance) {
            // Walks to player
            navigation.a((owner as CraftPlayer).handle as Entity, movementSpeed)
            return true
        }

        // Teleports to owner
        petInsentient.setPosition(ownerLocation.x, ownerLocation.y, ownerLocation.z)
        petInsentient.bukkitEntity.teleport(ownerLocation)
        return true

    }

}
