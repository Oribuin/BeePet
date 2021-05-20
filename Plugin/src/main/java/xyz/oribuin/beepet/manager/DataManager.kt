package xyz.oribuin.beepet.manager

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import xyz.oribuin.beepet.BeePet
import xyz.oribuin.orilibrary.database.DatabaseConnector
import xyz.oribuin.orilibrary.database.MySQLConnector
import xyz.oribuin.orilibrary.database.SQLiteConnector
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.orilibrary.util.FileUtils
import xyz.oribuin.orilibrary.util.HexUtils
import xyz.oribuin.skyblock.nms.CustomOptions
import java.util.*
import java.util.function.Consumer

class DataManager(private val plugin: BeePet) : Manager(plugin) {

    private var connector: DatabaseConnector? = null
    private val settings = mutableMapOf<UUID, CustomOptions>()

    override fun enable() {

        val config = this.plugin.config

        if (config.getBoolean("mysql.enabled")) {
            val host = config.getString("mysql.host") ?: return
            val port = config.getInt("mysql.port")
            val dbname = config.getString("mysql.dbname") ?: return
            val username = config.getString("mysql.username") ?: return
            val password = config.getString("mysql.password") ?: return
            val ssl = config.getBoolean("mysql.ssl")

            this.connector = MySQLConnector(this.plugin, host, port, dbname, username, password, ssl)
            this.plugin.logger.info("Using MySQL for Database Saving ~ $host:$port")
        } else {
            // Create DB Files
            FileUtils.createFile(plugin, "beepets.db")

            this.connector = SQLiteConnector(this.plugin, "beepets.db")
            this.plugin.logger.info("Using SQLite for Database Saving ~ beepets.db")
        }

        // Check if connector is null
        if (connector == null) {
            this.plugin.logger.severe("Failed to connect to SQLite or MySQL, Disabling plugin...")
            this.plugin.server.pluginManager.disablePlugin(plugin)
            return
        }

        this.async { _ ->
            this.connector?.connect { connection ->
                connection.prepareStatement("CREATE TABLE IF NOT EXISTS beepets_settings (uuid VARCHAR(50), name TEXT, sitting BOOLEAN, PRIMARY KEY(uuid))")?.use { it.executeUpdate() }
            }
        }
    }

    private fun loadSettings() {
        this.connector?.connect { connection ->
            connection.prepareStatement("SELECT * FROM beepets_settings")?.use {

                val result = it.executeQuery()
                while (result.next()) {
                    this.settings[UUID.fromString(result.getString("uuid"))] = CustomOptions(HexUtils.colorify(result.getString("name")), result.getBoolean("sitting"))
                }

            }
        }
    }

    fun saveSettings(uuid: UUID, options: CustomOptions): CustomOptions {

        this.settings[uuid] = options

        async {

            this.connector?.connect { connection ->

                connection.prepareStatement("REPLACE INTO beepets_settings (uuid, name, sitting) VALUES(?, ?, ?)").use {
                    it.setString(1, uuid.toString())
                    it.setString(2, options.name)
                    it.setBoolean(3, options.sitting)
                    it.executeUpdate()
                }
            }
        }

        return options
    }

    fun getSettings(player: Player): CustomOptions? {
        return this.settings[player.uniqueId]
    }

    override fun disable() {

    }

    private fun async(callback: Consumer<BukkitTask>) {
        this.plugin.server.scheduler.runTaskAsynchronously(plugin, callback)
    }

}