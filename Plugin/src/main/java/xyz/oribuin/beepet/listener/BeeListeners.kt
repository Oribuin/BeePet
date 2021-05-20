package xyz.oribuin.beepet.listener

import io.github.bananapuncher714.nbteditor.NBTEditor
import org.bukkit.entity.Bee
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot
import xyz.oribuin.beepet.BeePet
import xyz.oribuin.beepet.manager.DataManager
import xyz.oribuin.beepet.manager.MessageManager
import xyz.oribuin.orilibrary.util.HexUtils
import xyz.oribuin.skyblock.nms.CustomOptions
import java.util.*

class BeeListeners(private val plugin: BeePet) : Listener {

    @EventHandler(ignoreCancelled = true)
    fun PlayerInteractEntityEvent.onInteract() {
        if (hand != EquipmentSlot.HAND) return

        val entity = this.rightClicked
        if (entity !is Bee) return


        println(NBTEditor.contains(entity, "beepet"))
        if (!NBTEditor.contains(entity, "beepet")) return

        val owner = plugin.server.getOfflinePlayer(UUID.fromString(NBTEditor.getString("beepet")))

        println(4)
        if (!owner.hasPlayedBefore()) {
            entity.remove()
            return
        }

        println(5)

        val options = plugin.getManager(DataManager::class.java).getSettings(player) ?: CustomOptions(HexUtils.colorify("#c0ffeeBee"))

        // TODO, Change to config message
        plugin.getManager(MessageManager::class.java).sendRaw(player, "&8[<r:0.7>BeePets&8] &fYour bee now is " + if (options.sitting) "sitting" else "flying")
        this.isCancelled = true
    }

    init {
        this.plugin.server.pluginManager.registerEvents(this, this.plugin)
    }

}