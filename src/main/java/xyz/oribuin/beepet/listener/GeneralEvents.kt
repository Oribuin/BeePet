package xyz.oribuin.beepet.listener

import org.bukkit.entity.Bee
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPortalEnterEvent
import org.bukkit.event.entity.EntityPortalEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import xyz.oribuin.beepet.BeePet
import xyz.oribuin.beepet.util.HexUtils

class GeneralEvents(private val plugin: BeePet): Listener {

    @EventHandler(ignoreCancelled = true)
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity is Bee && event.entity.hasMetadata("beePet")) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        for (world in event.player.server.worlds) {
            for (entity in world.entities) {
                if (entity.hasMetadata("${event.player.name}Bee")) {
                    entity.remove()
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onInteract(event: PlayerInteractAtEntityEvent) {
        if (event.rightClicked is Bee && event.rightClicked.hasMetadata("beePet") && event.player.isSneaking && event.hand == EquipmentSlot.HAND) {

            val customName = event.rightClicked.customName?: return
            if (!customName.toLowerCase().contains(event.player.name.toLowerCase()))
                return

            event.rightClicked.remove()
            event.player.sendMessage(HexUtils.colorify("<r:0.7:l>You have despawned your Bee Pet."))
        }
    }

    @EventHandler
    fun onPortal(event: EntityPortalEvent) {
        if (event.entity is Bee && event.entity.hasMetadata("beePet")) {
            event.isCancelled = true
        }
    }
}