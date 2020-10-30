package me.zoom.hackhack.module.modules.movement;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.MovementUtils;

public class LavaFlight extends Module {
    public LavaFlight() {
        super("Lava Flight", "Allows you to fly fast in lava", Category.Movement);
    }

    Setting.Double hSpeed;
    Setting.Double ySpeed;

    @Override
    public void setup() {
        hSpeed = registerDouble("H Speed", "hSpeed", 1, 0.1, 2.5);
        ySpeed = registerDouble("Y Speed", "ySpeed", 1, 0.1, 1);
    }

    @Override
    public void onUpdate() {
          //          mc.player.capabilities.isFlying = true;
            if (mc.gameSettings.keyBindJump.isKeyDown()){
   //             mc.player.capabilities.setFlySpeed(((float) ySpeed.getValue()) / 2);

                mc.player.motionY = ySpeed.getValue();
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()){
                mc.player.motionY = (ySpeed.getValue() * -1);
 //               mc.player.motionY = 0;
 //               mc.player.capabilities.setFlySpeed(((float) hSpeed.getValue()) / 2);
            } else mc.player.motionY = 0;

            double yaw = MovementUtils.calcMoveYaw(mc.player.rotationYaw);
            double motX = 0;
            double motZ = 0;

            yaw -= mc.player.moveStrafing * 90;

            if (mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindForward.isKeyDown()){
                motX = (-Math.sin(yaw) * hSpeed.getValue()) * -1;
                motZ = (Math.cos(yaw) * hSpeed.getValue()) * -1;
            } else if (mc.gameSettings.keyBindForward.isKeyDown()){
                motX = -Math.sin(yaw) * hSpeed.getValue();
                motZ = Math.cos(yaw) * hSpeed.getValue();
            }



            mc.player.motionX = motX;
            mc.player.motionZ = motZ;

            if (mc.player.moveStrafing == 0 && mc.player.moveForward == 0){
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }
    }
}
