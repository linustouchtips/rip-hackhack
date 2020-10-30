package me.zoom.hackhack.module.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zoom.hackhack.HackHack;
import me.zoom.hackhack.command.Command;
import me.zoom.hackhack.event.events.PacketEvent;
import me.zoom.hackhack.module.Module;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Xcarry extends Module {
    public Xcarry() {
        super("X Carry", "Lets you store items in crafting slots", Category.Player);
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketCloseWindow){
            event.cancel();
        }

    });

    public void onEnable(){
        HackHack.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        HackHack.EVENT_BUS.unsubscribe(this);
    }
}
