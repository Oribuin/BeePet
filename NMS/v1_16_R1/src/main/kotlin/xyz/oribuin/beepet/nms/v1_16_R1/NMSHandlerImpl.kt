package xyz.oribuin.beepet.nms.v1_16_R1

import net.minecraft.server.v1_16_R1.NBTTagCompound
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld
import org.bukkit.entity.Bee
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.Plugin
import xyz.oribuin.skyblock.nms.BeeOptions
import xyz.oribuin.skyblock.nms.CustomOptions
import xyz.oribuin.skyblock.nms.NMSHandler

class NMSHandlerImpl : NMSHandler {

    override fun createBee(plugin: Plugin, owner: Player, options: BeeOptions, custom: CustomOptions): Bee {

        // Create bee instance
        val bee = CustomBee(owner, options, custom.sitting)
        val loc = owner.location.clone()

        // Spawn the bee into the world
        (owner.world as CraftWorld).handle.addEntity(bee, CreatureSpawnEvent.SpawnReason.CUSTOM)
        bee.setLocation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)

        val compound = NBTTagCompound()
        compound.setString("beepet", owner.uniqueId.toString())
        bee.save(compound)

        // Add the correct values and meta data
        val bukkit = bee.bukkitEntity as Bee
        bukkit.setMetadata("beepet", FixedMetadataValue(plugin, owner.uniqueId.toString()))

        bukkit.isCustomNameVisible = true
        bukkit.customName = custom.name

        return bee.bukkitEntity as Bee
    }

}