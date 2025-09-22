package com.sofka.ms_users.service;

import com.sofka.ms_users.event.ClienteCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducerService {

    private final StreamBridge streamBridge;

    public void sendClienteCreatedEvent(ClienteCreatedEvent event) {
        log.info("Publishing ClienteCreatedEvent: {}", event);
        boolean sent = streamBridge.send("clienteCreated-out-0", event);
        if (!sent) {
            log.error("Failed to publish ClienteCreatedEvent for clienteId={} ident={}.", event.getClienteId(), event.getIdentificacion());
            throw new IllegalStateException("Unable to publish ClienteCreatedEvent");
        }
    }
}
