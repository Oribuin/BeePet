package xyz.oribuin.beepet;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.manager.*;
import org.bukkit.NamespacedKey;
import xyz.oribuin.beepet.manager.BeeManager;
import xyz.oribuin.beepet.manager.CommandManager;
import xyz.oribuin.beepet.manager.ConfigurationManager;
import xyz.oribuin.beepet.manager.LocaleManager;

import java.util.List;

public class BeePetPlugin extends RosePlugin {

    public static final NamespacedKey OWNER_KEY = NamespacedKey.fromString("beepet:owner");

    private static BeePetPlugin instance;

    public static BeePetPlugin getInstance() {
        return instance;
    }

    public BeePetPlugin() {
        super(-1, -1, ConfigurationManager.class, null, LocaleManager.class, CommandManager.class);

        instance = this;
    }

    @Override
    protected void enable() {

    }

    @Override
    protected void disable() {

    }

    @Override
    protected List<Class<? extends Manager>> getManagerLoadPriority() {
        return List.of(BeeManager.class);
    }

}
