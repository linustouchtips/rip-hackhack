package me.zoom.hackhack.event.events;

import me.zoom.hackhack.event.Event;

public class PlayerLeaveEvent extends Event {

    private final String name;

    public PlayerLeaveEvent(String n){
        super();
        name = n;
    }

    public String getName(){
        return name;
    }
}
