package com.sofka.ms_users.service;

import com.sofka.ms_users.event.ClienteCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class EventProducerService {

    @Autowired
    private StreamBridge streamBridge;

    public void sendClienteCreatedEvent(ClienteCreatedEvent event) {
        streamBridge.send("clienteCreated-out-0", event);
    }
}