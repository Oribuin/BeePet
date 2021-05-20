package xyz.oribuin.beepet.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.beepet.manager.DataManager
import xyz.oribuin.orilibrary.OriPlugin
import xyz.oribuin.orilibrary.command.Command
import xyz.oribuin.orilibrary.util.HexUtils
import xyz.oribuin.skyblock.nms.BeeOptions
import xyz.oribuin.skyblock.nms.CustomOptions
import xyz.oribuin.skyblock.nms.NMSAdapter

@Command.Info(
    name = "beepet",
    description = "Main command for bee pets.",
    usage = "/beepet",
    subcommands = [],
    aliases = [],
    permission = "beepets.use",
    playerOnly = true
)
class BeeCommand(private val plugin: OriPlugin) : Command(plugin) {

    private val data = this.plugin.getManager(DataManager::class.java)

    override fun runFunction(sender: CommandSender, label: String, args: Array<String>) {
        val player = sender as Player

        val settings = data.getSettings(player) ?: data.saveSettings(player.uniqueId, CustomOptions(HexUtils.colorify("#c0ffeeBee")))

        NMSAdapter.handler.createBee(plugin, player, BeeOptions(1.0, 5.0, 10.0), settings)
    }


}