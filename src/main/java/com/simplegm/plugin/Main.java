package com.simplegm.plugin;

import com.simplegm.plugin.commands.GMCmd;
import com.simplegm.plugin.commands.GenericCmd;
import com.simplegm.plugin.misc.Config;
import com.simplegm.plugin.services.GamemodeService;
import com.simplegm.plugin.services.SoundService;
import com.simplegm.plugin.services.message.MessageService;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config config;

    private MessageService  messageService;
    private SoundService    soundService;
    private GamemodeService gamemodeService;

    @Override
    public void onEnable() {
        loadConfig();
        setupServices();
        registerCommands();
    }

    void loadConfig() {
        config = new Config(this, getDataFolder(), "config", "config.yml");
    }

    void setupServices() {
        messageService  = new MessageService(config);
        soundService    = new SoundService(config);
        gamemodeService = new GamemodeService(messageService, soundService, config);
    }

    void registerCommands() {
        getCommand("gma").setExecutor(new GenericCmd(messageService, gamemodeService, soundService));
        getCommand("gmc").setExecutor(new GenericCmd(messageService, gamemodeService, soundService));
        getCommand("gm").setExecutor(new GMCmd(messageService, gamemodeService, config, soundService));
        getCommand("gms").setExecutor(new GenericCmd(messageService, gamemodeService, soundService));
        getCommand("gmsp").setExecutor(new GenericCmd(messageService, gamemodeService, soundService));
    }
}
