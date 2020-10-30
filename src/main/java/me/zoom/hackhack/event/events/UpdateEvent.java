package me.zoom.hackhack.event.events;

import me.zoom.hackhack.event.EventCancellable;

public class UpdateEvent extends EventCancellable
{
    private float yaw;
    private float pitch;
    private double x;
    private double y;
    private double z;
    private boolean onGround;

    public UpdateEvent(final EventStage stage, final float yaw, final float pitch, final double x, final double y, final double z, final boolean onGround) {
        super(stage);
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
    }

    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}


