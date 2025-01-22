package com.simplegm.plugin.services;

import com.simplegm.plugin.services.message.Message;
import com.simplegm.plugin.services.message.MessageService;
import com.simplegm.plugin.utilities.Config;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GamemodeService {

    @NonNull
    private final MessageService messageService;
    @NonNull
    private final SoundService   soundService;
    @NonNull
    private final Config         config;

    public void setGamemode(Player player, GameMode gamemode) {
        if (player.getGameMode() == gamemode) {
            messageService.sendMessage(player, Message.GENERAL_ALREADY_IN_GM_SELF);
            return;
        }
        player.setGameMode(gamemode);
        messageService.sendMessage(player,
                                    Message.CMD_MESSAGES_SELF,
                                    (s) -> s.replace("%gamemode%", config.getString("general.gamemodes." + player.getGameMode().toString().toLowerCase())));
        soundService.playSound(player, "commands.sound");
    }

    public void switchGamemode(Player player) {
        switch (player.getGameMode()) {
            case SURVIVAL:
            case ADVENTURE:
                player.setGameMode(GameMode.CREATIVE);
                break;
            default:
                player.setGameMode(GameMode.SURVIVAL);
                break;
        }
        messageService.sendMessage(player,
                                    Message.CMD_MESSAGES_SELF,
                                    (s) -> s.replace("%gamemode%", config.getString("general.gamemodes." + player.getGameMode().toString().toLowerCase())));
        soundService.playSound(player, "commands.sound");
    }
}
