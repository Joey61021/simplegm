package com.simplegm.plugin.services.message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Message {

    GENERAL_NO_CONSOLE("general.messages.no-console"),
    GENERAL_NO_PERMISSION("general.messages.no-permission"),
    GENERAL_NO_PLAYER("general.messages.no-player"),
    GENERAL_ALREADY_IN_GM_SELF("general.messages.already-in-gm.self"),
    GENERAL_ALREADY_IN_GM_OTHER("general.messages.already-in-gm.other"),

    CMD_MESSAGES_SELF("commands.messages.self"),
    CMD_MESSAGES_OTHER("commands.messages.other");

    @Getter
    @NonNull
    private final String path;
}
