package xyz.oribuin.beepet

import xyz.oribuin.beepet.command.BeeCommand
import xyz.oribuin.beepet.listener.BeeListeners
import xyz.oribuin.beepet.manager.DataManager
import xyz.oribuin.beepet.manager.MessageManager
import xyz.oribuin.orilibrary.OriPlugin

class BeePet : OriPlugin() {

    override fun enablePlugin() {

        // Register Plugin Managers
        this.server.scheduler.runTaskAsynchronously(this, Runnable {
            this.getManager(DataManager::class.java)
            this.getManager(MessageManager::class.java)
        })

        // Register Listeners.
        BeeListeners(this)

        // Register Commands
        BeeCommand(this).register(null, null)

    }

    override fun disablePlugin() {

    }

}