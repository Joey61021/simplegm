package com.simplegm.plugin.services.message;

import com.simplegm.plugin.utilities.Config;
import com.simplegm.plugin.utilities.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

import java.util.function.Function;

@RequiredArgsConstructor
public class MessageService {

    @NonNull
    private final Config config;

    public void sendMessage(CommandSender receiver, Message message) {
        sendMessage(receiver, message, Function.identity());
    }

    public void sendMessage(CommandSender receiver, Message message, Function<String, String> replacementFunction) {
        String path = message.getPath();
        if (config.isString(path))
            receiver.sendMessage(Utils.color(replacementFunction.apply(config.getString(path))));
        else if (config.isList(path)) {
            // Join all strings of list with a newline character so one message packet can be used to convey the whole message
            String joinedMessage = String.join("\n", config.getStringList(path));
            receiver.sendMessage(Utils.color(replacementFunction.apply(joinedMessage)));
        }
        else
            throw new IllegalArgumentException("Path \"" + path + "\" is not a string or list of strings in the config.yml");
    }
}
