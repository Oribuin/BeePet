package xyz.oribuin.skyblock.nms

import org.bukkit.Bukkit

object NMSAdapter {

    lateinit var handler: NMSHandler

    init {

        try {
            val name = Bukkit.getServer().javaClass.getPackage().name
            val version = name.substring(name.lastIndexOf('.') + 1)
            this.handler = Class.forName("xyz.oribuin.beepet.nms.$version.NMSHandlerImpl").getConstructor().newInstance() as NMSHandler
        } catch (ignored: Exception) {
        }

    }
}