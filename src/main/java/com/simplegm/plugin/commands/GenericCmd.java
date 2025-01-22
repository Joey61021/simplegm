package com.simplegm.plugin.commands;

import com.simplegm.plugin.services.GamemodeService;
import com.simplegm.plugin.services.SoundService;
import com.simplegm.plugin.services.message.Message;
import com.simplegm.plugin.services.message.MessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GenericCmd implements CommandExecutor {

    @NonNull
    private final MessageService  messageService;
    @NonNull
    private final GamemodeService gamemodeService;
    @NonNull
    private final SoundService    soundService;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            messageService.sendMessage(sender, Message.GENERAL_NO_CONSOLE);
            return false;
        }

        Player player = (Player) sender;
        if (!(player.hasPermission("simplegm.gm" + cmd.getName().substring(2)) || player.hasPermission("simplegm.*"))) {
            messageService.sendMessage(player, Message.GENERAL_NO_PERMISSION);
            return false;
        }

        GameMode gamemode;
        switch (cmd.getName().toLowerCase()) {
            case "gma": gamemode = GameMode.ADVENTURE; break;
            case "gmc": gamemode = GameMode.CREATIVE; break;
            case "gms": gamemode = GameMode.SURVIVAL; break;
            case "gmsp": gamemode = GameMode.SPECTATOR; break;
            default:
                throw new IllegalStateException("Unexpected value: " + cmd.getName().toLowerCase());
        }

        if (args.length == 0) {
            gamemodeService.setGamemode(player, gamemode);
            return false;
        }

        if (!player.hasPermission("simplegm.others")) {
            gamemodeService.setGamemode(player, gamemode);
            return false;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            messageService.sendMessage(player, Message.GENERAL_NO_PLAYER);
            return false;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            gamemodeService.setGamemode(player, gamemode);
            return false;
        }

        if (target.getGameMode() == gamemode) {
            messageService.sendMessage(player, Message.GENERAL_ALREADY_IN_GM_OTHER);
            return false;
        }

        gamemodeService.setGamemode(target, gamemode);
        messageService.sendMessage(player,
                                    Message.CMD_MESSAGES_OTHER,
                                    (s) -> s.replace("%target%", target.getName())
                                            .replace("%gamemode%", target.getGameMode().toString().toLowerCase()));
        soundService.playSound(player, "commands.sound");
        return false;
    }
}
