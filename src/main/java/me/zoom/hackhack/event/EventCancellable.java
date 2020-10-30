package me.zoom.hackhack.event;

public class EventCancellable extends EventStageable
{
    private boolean canceled;

    public EventCancellable() {
    }

    public EventCancellable(final EventStage stage) {
        super(stage);
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }
}
