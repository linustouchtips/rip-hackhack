package me.zoom.hackhack.module.modules.chat;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.event.events.PacketEvent;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.command.Command;
import me.zoom.hackhack.HackHack;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.EventHandler;
import me.zoom.hackhack.setting.Setting;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;

//Made by _KA1 :)
/*acc cant belive this works rofl
*/

public class ChatSuffix extends Module{
    public ChatSuffix(){super("ChatSuffix", "Adds a suffix to the end of your message", Category.Chat);}
    Setting.Mode Separator;

    Setting.Mode Mode;

    public void setup() {
// settings etc
        ArrayList<String> Separators;
        Separators = new ArrayList<>();
        Separators.add(">>");
        Separators.add("|");

        Separator = registerMode("Separator", "Separator", Separators, "|");

        ArrayList<String> Modes;
        Modes = new ArrayList<>();
        Modes.add("HACKHACK");
        Modes.add("KAiKLiENT");
        Modes.add("ZOOMGOD.CC");
        Modes.add("MAXHAX.CA");

        Mode = registerMode("Mode", "Mode", Modes, "\uD835\uDDDB\uD835\uDDD4\uD835\uDDD6\uD835\uDDDE\uD835\uDDDB\uD835\uDDD4\uD835\uDDD6\uD835\uDDDE");
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketChatMessage) {
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith("!") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return; //disables suffix if "/" or "!" is at the beginning
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith(";") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith(".") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith(":") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;

            String Separator2 = null;
            if (Separator.getValue().equalsIgnoreCase(">>")) {
                Separator2 = " \u300b";

            } else if (Separator.getValue().equalsIgnoreCase("|")) {
                Separator2 = " \u23D0 ";
            // seperators
            }

            String Mode2 = null;
            if (Mode.getValue().equalsIgnoreCase("HACKHACK")) {
        Mode2 = "HACKHACK";

            }
            else if (Mode.getValue().equalsIgnoreCase("KAiKLiENT")) {
                Mode2 = "KAiKLiENT";

            }
            else if (Mode.getValue().equalsIgnoreCase("ZOOMGOD.CC")) {
                Mode2 = "ZOOMGOD.CC";
            }
            else if (Mode.getValue().equalsIgnoreCase("MAXHAX.CA")) {
                Mode2 = "MAXHAX";

                //suffixes
            }
            
            String old = ((CPacketChatMessage) event.getPacket()).getMessage();
            String suffix = Separator2 + toUnicode(Mode2);
            String s = old + suffix;
            if (s.length() > 255) return;
            ((CPacketChatMessage) event.getPacket()).message = s;
        } // where all the magic happens, adds suffix onto msg!
    });

    public void onEnable() {
        HackHack.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        HackHack.EVENT_BUS.unsubscribe(this);
    }
    public String toUnicode(String s) {
        return s.toLowerCase()
                .replace("a", "\u1d00")
                .replace("b", "\u0299")
                .replace("c", "\u1d04")
                .replace("d", "\u1d05")
                .replace("e", "\u1d07")
                .replace("f", "\ua730")
                .replace("g", "\u0262")
                .replace("h", "\u029c")
                .replace("i", "\u026a")
                .replace("j", "\u1d0a")
                .replace("k", "\u1d0b")
                .replace("l", "\u029f")
                .replace("m", "\u1d0d")
                .replace("n", "\u0274")
                .replace("o", "\u1d0f")
                .replace("p", "\u1d18")
                .replace("q", "\u01eb")
                .replace("r", "\u0280")
                .replace("s", "\ua731")
                .replace("t", "\u1d1b")
                .replace("u", "\u1d1c")
                .replace("v", "\u1d20")
                .replace("w", "\u1d21")
                .replace("x", "\u02e3")
                .replace("y", "\u028f")
                .replace("z", "\u1d22");
    }
 //convs plaintext into unicode so suffix looks based af
}
