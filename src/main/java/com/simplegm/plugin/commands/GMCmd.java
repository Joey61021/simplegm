package com.simplegm.plugin.commands;

import com.simplegm.plugin.misc.Config;
import com.simplegm.plugin.services.GamemodeService;
import com.simplegm.plugin.services.SoundService;
import com.simplegm.plugin.services.message.Message;
import com.simplegm.plugin.services.message.MessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GMCmd implements CommandExecutor {

    @NonNull
    private final MessageService  messageService;
    @NonNull
    private final GamemodeService gamemodeService;
    @NonNull
    private final Config          config;
    @NonNull
    private final SoundService    soundService;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            messageService.sendMessage(sender, Message.GENERAL_NO_CONSOLE);
            return false;
        }
        Player player = (Player) sender;
        if (!(player.hasPermission("simplegm.gm") || player.hasPermission("simplegm.*"))) {
            messageService.sendMessage(player, Message.GENERAL_NO_PERMISSION);
            return false;
        }
        if (args.length == 0) {
            gamemodeService.switchGamemode(player);
            return false;
        }
        if (!player.hasPermission("simplegm.others")) {
            gamemodeService.switchGamemode(player);
            return false;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            messageService.sendMessage(player, Message.GENERAL_NO_PLAYER);
            return false;
        }
        if (target.getUniqueId().equals(player.getUniqueId())) {
            gamemodeService.switchGamemode(player);
            return false;
        }
        gamemodeService.switchGamemode(target);
        messageService.sendMessage(player,
                                    Message.CMD_MESSAGES_OTHER,
                                    (s) -> s.replace("%target%", target.getName())
                                            .replace("%gamemode%", config.getString("General.Gamemodes." + target.getGameMode().toString().charAt(0)
                                                    + target.getGameMode().toString().substring(1).toLowerCase())));
        soundService.playSound(player, "Commands.Sound");
        return false;
    }
}
