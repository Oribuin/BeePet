package xyz.oribuin.beepet.manager

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import xyz.oribuin.beepet.BeePet
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.orilibrary.util.FileUtils
import xyz.oribuin.orilibrary.util.HexUtils
import xyz.oribuin.orilibrary.util.StringPlaceholders
import java.io.File

class MessageManager(private val plugin: BeePet) : Manager(plugin) {

    lateinit var config: FileConfiguration

    override fun enable() {
        this.config = YamlConfiguration.loadConfiguration(FileUtils.createFile(this.plugin, "messages.yml"))

        // Set any values that dont exist
        for (msg in Messages.values()) {
            val key = msg.name.toLowerCase().replace("_", "-")

            if (config.get(key) == null) {
                config.set(key, msg.value)
            }

        }

        config.save(File(plugin.dataFolder, "messages.yml"))
    }

    /**
     * Send a configuration messageId with placeholders.
     *
     * @param receiver     The CommandSender who receives the messageId.
     * @param messageId    The messageId path
     * @param placeholders The Placeholders
     */
    fun send(receiver: CommandSender, messageId: String, placeholders: StringPlaceholders = StringPlaceholders.empty()) {
        val msg = config.getString(messageId)

        if (msg == null) {
            receiver.sendMessage(HexUtils.colorify("&c&lError &7| &fThis is an invalid message in the messages file, Please contact the server owner about this issue. (Id: $messageId)"))
            return
        }

        val prefix = config.getString("prefix") ?: Messages.PREFIX.value
        receiver.sendMessage(HexUtils.colorify(prefix + apply(if (receiver is Player) receiver else null, placeholders.apply(msg))))
    }

    /**
     * Send a raw message to the receiver with placeholders.
     *
     *
     * Use this to send a message to a player without the message being defined in a config.
     *
     * @param receiver     The message receiver
     * @param message      The message
     * @param placeholders Message Placeholders.
     */
    fun sendRaw(receiver: CommandSender, message: String, placeholders: StringPlaceholders = StringPlaceholders.empty()) {
        receiver.sendMessage(HexUtils.colorify(apply(if (receiver is Player) receiver else null, placeholders.apply(message))))
    }

    override fun disable() {

    }

    enum class Messages(val value: String) {
        PREFIX("#77dd77&lParkour &8| &f"),

        RELOAD("You have reloaded CYTParkour!"),
        INVALID_PLAYER("&c&lError &8| &fPlease enter a valid player."),
        INVALID_ARGS("&c&lError &8| &fPlease provide valid arguments. Correct usage: %usage%"),
        UNKNOWN_CMD("&c&lError &8| &fPlease include a valid command."),
        NO_PERM("&c&lError &8| &fYou do not have permission to do this."),
        PLAYER_ONLY("&c&lError &8| &fOnly a player can execute this command."),
        CONSOLE_ONLY("&c&lError &8| &fOnly console can execute this command.");
    }

    private fun apply(player: Player?, text: String): String {
        return if (!this.plugin.server.pluginManager.isPluginEnabled("PlaceholderAPI"))
            text
        else
            PlaceholderAPI.setPlaceholders(player, text)
    }
}