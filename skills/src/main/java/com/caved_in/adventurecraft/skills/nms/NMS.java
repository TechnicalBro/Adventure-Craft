package com.caved_in.adventurecraft.skills.nms;

import com.caved_in.adventurecraft.skills.nms.minecraft_1_8_R3.NativeHandler_1_8_R3;
import com.caved_in.adventurecraft.skills.nms.no_implementation.DefaultHandler;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.plugin.Plugins;

public class NMS {
    private static NativeActionHandler nmsHandler = null;

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            throw new IllegalAccessError("Unable to re-initialize NMS Handler.");
        }

        Chat.debug("NMS Version is: '" + Plugins.getNmsVersion() + "'");

        switch (Plugins.getNmsVersion()) {
            case "v1_8_R3":
                nmsHandler = new NativeHandler_1_8_R3();
                break;
            default:
                nmsHandler = new DefaultHandler();
                break;
        }
        initialized = true;
    }

    public static boolean isHooked() {
        return nmsHandler != null;
    }

    public static NativeActionHandler getNmsHandler() {
        return nmsHandler;
    }
}
