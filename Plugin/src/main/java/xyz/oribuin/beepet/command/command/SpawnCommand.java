package xyz.oribuin.beepet.command.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.framework.CommandContext;
import dev.rosewood.rosegarden.command.framework.RoseCommand;
import dev.rosewood.rosegarden.command.framework.RoseCommandWrapper;
import dev.rosewood.rosegarden.command.framework.annotation.RoseExecutable;
import dev.rosewood.rosegarden.command.framework.types.GreedyString;
import dev.rosewood.rosegarden.utils.HexUtils;
import org.bukkit.entity.Player;
import xyz.oribuin.beepet.manager.BeeManager;
import xyz.oribuin.beepet.nms.CustomBeeSettings;

public class SpawnCommand extends RoseCommand {

    public SpawnCommand(RosePlugin rosePlugin, RoseCommandWrapper parent) {
        super(rosePlugin, parent);
    }

    @RoseExecutable
    public void execute(CommandContext context, GreedyString name) {
        Player player = (Player) context.getSender();
        CustomBeeSettings settings = new CustomBeeSettings(HexUtils.colorify(name.get()));

        this.rosePlugin.getManager(BeeManager.class).createFor(player, settings);
    }

    @Override
    protected String getDefaultName() {
        return "spawn";
    }

    @Override
    public String getDescriptionKey() {
        return "command-spawn-description";
    }

    @Override
    public String getRequiredPermission() {
        return "beepet.command.spawn";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

}
