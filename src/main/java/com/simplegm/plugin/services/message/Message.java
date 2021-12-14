package com.simplegm.plugin.services.message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Message {

    GENERAL_NO_CONSOLE("General.Messages.No-Console"),
    GENERAL_NO_PERMISSION("General.Messages.No-Permission"),
    GENERAL_NO_PLAYER("General.Messages.No-Player"),
    GENERAL_ALREADY_IN_GM_SELF("General.Messages.Already-In-GM.Self"),
    GENERAL_ALREADY_IN_GM_OTHER("General.Messages.Already-In-GM.Other"),

    CMD_MESSAGES_SELF("Commands.Messages.Self"),
    CMD_MESSAGES_OTHER("Commands.Messages.Other");

    @Getter
    @NonNull
    private final String path;
}
