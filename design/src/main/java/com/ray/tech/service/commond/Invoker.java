package com.ray.tech.service.commond;

public class Invoker {
    private Command slot;

    public void setSlot(Command slot) {
        this.slot = slot;
    }

    public void doCommand() {
        slot.execute();
    }
}
