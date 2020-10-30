package me.zoom.hackhack.module.modules.movement;

import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;

public class ReverseStep extends Module {
    public ReverseStep() {super("ReverseStep", Category.Movement);}

    Setting.Double height;

    public void setup() {
        height = registerDouble("Height", "Height", 2.5, 0.5, 15);
    }

    public void onUpdate() {
        if (mc.world == null || mc.player == null || mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder()
                || mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }

        if (mc.player != null && mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
            for (double y = 0.0; y < this.height.getValue() + 0.5; y += 0.01) {
                if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                    mc.player.motionY = -10.0;
                    break;
                }
            }
        }
    }
}
