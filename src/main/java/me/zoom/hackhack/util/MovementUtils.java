package me.zoom.hackhack.util;

import net.minecraft.client.Minecraft;

public class MovementUtils {
    static Minecraft mc = Minecraft.getMinecraft();

    public static double calcMoveYaw(float yawIn) {
        float moveForward = roundedForward;
        float moveString = roundedStrafing;

        float strafe = 90 * moveString;
        if (moveForward != 0f){
            strafe *= moveForward * 0.5f;
        } else strafe *= 1f;
 //       strafe *= if (moveForward != 0F) moveForward * 0.5F else 1F;
        float yaw = yawIn - strafe;
        if (moveForward < 0f){
            yaw -= 180;
        } else yaw -= 0;

        //yaw -= if (moveForward < 0F) 180 else 0;

        return Math.toRadians(yaw);
    }

    private static float roundedForward = getRoundedMovementInput(mc.player.movementInput.moveForward);

    private static float roundedStrafing = getRoundedMovementInput(mc.player.movementInput.moveStrafe);

    private static float getRoundedMovementInput(Float input){
        if (input > 0){
            input = 1f;
        } else if (input < 0){
            input = -1f;
        } else input = 0f;
        return input;
    }
}
