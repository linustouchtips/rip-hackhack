package me.zoom.hackhack.module.modules.movement;

import me.zoom.hackhack.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Makes you sprint", Category.Movement);
    }

    int rotation = 0;

    @Override
    public void onUpdate() {
        if (!mc.player.isSprinting()){
            mc.player.setSprinting(true);

        }
    }
}
