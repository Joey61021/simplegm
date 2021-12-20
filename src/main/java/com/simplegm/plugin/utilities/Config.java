package com.simplegm.plugin.utilities;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config extends YamlConfiguration {

    public File       file;
    public JavaPlugin javaPlugin;

    public Config(JavaPlugin javaPlugin, File path, String name, String def) {
        this.javaPlugin = javaPlugin;
        setup(path, name, def);
    }

    public void loaddefaults(String def) {
        load();
        InputStream is = this.javaPlugin.getResource(def);
        if (is != null) {
            try {
                this.load(new InputStreamReader(is));
            } catch (IOException | InvalidConfigurationException exception) {
                exception.printStackTrace();
            }
        }
        save();
    }

    public boolean setup(File path, String name) {
        path.mkdirs();
        this.file = new File(path, name.endsWith(".yml") ? name : name + ".yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return true;
        }
        load();
        save();
        return false;
    }

    public void setup(File path, String name, String def) {
        if (setup(path, name)) loaddefaults(def);
    }

    public void load() {
        try {
            super.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
