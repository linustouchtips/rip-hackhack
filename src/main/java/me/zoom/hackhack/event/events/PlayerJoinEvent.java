package me.zoom.hackhack.event.events;

import me.zoom.hackhack.event.Event;

public class PlayerJoinEvent extends Event {
    private final String name;

    public PlayerJoinEvent(String n){
        super();
        name = n;
    }

    public String getName(){
        return name;
    }
}
