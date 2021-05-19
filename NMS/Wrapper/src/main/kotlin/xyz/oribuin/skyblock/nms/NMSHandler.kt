package xyz.oribuin.skyblock.nms

import org.bukkit.entity.Bee
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

interface NMSHandler {

    fun createBee(plugin: Plugin, owner: Player, options: BeeOptions, custom: CustomOptions): Bee
}