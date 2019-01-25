package com.ray.tech.service.commond;

import com.ray.tech.service.commond.impl.CdCommand;
import org.springframework.stereotype.Service;

@Service
public class CommandService {
    private Invoker invoker = new Invoker();

    public void invoke() {
        invoker.setSlot(new CdCommand(new Receiver()));
        invoker.doCommand();
    }
}
