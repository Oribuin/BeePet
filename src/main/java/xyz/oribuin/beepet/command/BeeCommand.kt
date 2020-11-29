package xyz.oribuin.beepet.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.beepet.BeePet
import xyz.oribuin.beepet.util.HexUtils.colorify

class BeeCommand(private val plugin: BeePet) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, vararg args: String): Boolean {
        if (sender !is Player) {
            sender.sendMessage(colorify("#b00b1eOnly a player can execute this command."))
            return true
        }

        if (!sender.hasPermission("beepet.spawn")) {
            sender.sendMessage(colorify("#b00b1eYou don't have enough permission for that."))
            return true
        }

        for (entity in sender.world.entities) {
            if (entity.hasMetadata("${sender.name}Bee")) {
                entity.remove()
            }
        }

        plugin.spawnBee(sender)
        sender.sendMessage(colorify("<rainbow:0.7:l>Successfully spawned a pet bee."))
        sender.sendMessage(colorify("&7To despawn the bee, Shift right click it."))
        return true
    }

}