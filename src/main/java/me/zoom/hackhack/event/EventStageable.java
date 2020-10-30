package me.zoom.hackhack.event;

public class EventStageable
{
    private EventStage stage;

    public EventStageable() {
    }

    public EventStageable(final EventStage stage) {
        this.stage = stage;
    }

    public EventStage getStage() {
        return this.stage;
    }

    public enum EventStage
    {
        PRE,
        POST;
    }
}