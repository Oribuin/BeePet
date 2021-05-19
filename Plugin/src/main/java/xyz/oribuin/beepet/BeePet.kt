package xyz.oribuin.beepet

import xyz.oribuin.beepet.command.BeeCommand
import xyz.oribuin.orilibrary.OriPlugin

class BeePet : OriPlugin() {

    override fun enablePlugin() {

        BeeCommand(this).register(null, null)

    }

    override fun disablePlugin() {

    }

}