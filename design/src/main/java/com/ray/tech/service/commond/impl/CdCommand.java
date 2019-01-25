package com.ray.tech.service.commond.impl;

import com.ray.tech.service.commond.Command;
import com.ray.tech.service.commond.Receiver;

public class CdCommand implements Command {
    private Receiver receiver;

    public CdCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.doSth();
    }
}
