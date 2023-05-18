package xyz.oribuin.beepet.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.framework.RoseCommandWrapper;

import java.util.List;

public class BeeCommandWrapper extends RoseCommandWrapper {

    public BeeCommandWrapper(RosePlugin rosePlugin) {
        super(rosePlugin);
    }

    @Override
    public String getDefaultName() {
        return "bee";
    }

    @Override
    public List<String> getDefaultAliases() {
        return List.of();
    }

    @Override
    public List<String> getCommandPackages() {
        return List.of("xyz.oribuin.beepet.command.command");
    }

    @Override
    public boolean includeBaseCommand() {
        return true;
    }

    @Override
    public boolean includeHelpCommand() {
        return true;
    }

    @Override
    public boolean includeReloadCommand() {
        return true;
    }

}
