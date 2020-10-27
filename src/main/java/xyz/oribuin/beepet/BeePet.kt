package xyz.oribuin.beepet

import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.entity.Bee
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import xyz.oribuin.beepet.command.BeeCommand
import xyz.oribuin.beepet.listener.GeneralEvents
import xyz.oribuin.beepet.nms.CustomBee
import xyz.oribuin.beepet.util.HexUtils.colorify

class BeePet : JavaPlugin(), Listener {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(GeneralEvents(this), this);

        Bukkit.getPluginCommand("spawnbee")?.setExecutor(BeeCommand(this))


        Bukkit.getScheduler().runTaskTimerAsynchronously(this, Runnable {
            Bukkit.getWorlds().forEach { world ->
                world.entities.forEach { entity ->
                    if (entity.hasMetadata("beePet") && entity is Bee) {
                        entity.flower = null
                        entity.hive = null
                        entity.fireTicks = 0
                    }
                }
            }
        }, 0, 1)

    }

    override fun onDisable() {
        Bukkit.getWorlds().forEach { world -> world.entities.forEach { entity -> if (entity.hasMetadata("beePet")) entity.remove() } }
    }

    fun spawnBee(player: Player) {
        val loc = player.location.add(0.0, 2.0, 0.0)

        val bee = CustomBee(player)
        (player.world as CraftWorld).handle.addEntity(bee, CreatureSpawnEvent.SpawnReason.CUSTOM)
        bee.setLocation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)


        bee.bukkitEntity.customName = colorify("#c0ffee${player.name}&e's bee")
        bee.bukkitEntity.isCustomNameVisible = true
        bee.bukkitEntity.setMetadata("${player.name}Bee", FixedMetadataValue(this, null))
        bee.bukkitEntity.setMetadata("beePet", FixedMetadataValue(this, null))

    }
}
