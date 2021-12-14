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
        GameMode generic = cmd.getName().equals("gma") ? GameMode.ADVENTURE : cmd.getName().equals("gmc")
                ? GameMode.CREATIVE : cmd.getName().equals("gms") ? GameMode.SURVIVAL : GameMode.SPECTATOR;
        if (args.length == 0) {
            gamemodeService.setGamemode(player, generic);
            return false;
        }
        if (!player.hasPermission("simplegm.others")) {
            gamemodeService.setGamemode(player, generic);
            return false;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            messageService.sendMessage(player, Message.GENERAL_NO_PLAYER);
            return false;
        }
        if (target.getUniqueId().equals(player.getUniqueId())) {
            gamemodeService.setGamemode(player, generic);
            return false;
        }
        if (target.getGameMode() == generic) {
            messageService.sendMessage(player, Message.GENERAL_ALREADY_IN_GM_OTHER);
            return false;
        }
        gamemodeService.setGamemode(target, generic);
        messageService.sendMessage(player,
                                    Message.CMD_MESSAGES_OTHER,
                                    (s) -> s.replace("%target%", target.getName())
                                            .replace("%gamemode%", target.getGameMode().toString().charAt(0)
                                                    + target.getGameMode().toString().substring(1).toLowerCase()));
        soundService.playSound(player, "Commands.Sound");
        return false;
    }
}
