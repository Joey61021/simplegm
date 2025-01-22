package com.simplegm.plugin.services;

import com.simplegm.plugin.utilities.Config;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SoundService {

    @NonNull
    private final Config config;

    public void playSound(Player player, String soundRoot) {
        if (config.getBoolean(soundRoot + ".enabled"))
            player.playSound(player.getLocation(), Sound.valueOf(config.getString(soundRoot + ".type").toUpperCase()), 1, 1);
    }
}
