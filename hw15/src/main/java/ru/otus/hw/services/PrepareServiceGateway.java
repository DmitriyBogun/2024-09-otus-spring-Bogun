package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.domain.CarResult;
import ru.otus.hw.domain.PrepareAssemblyResult;

import java.util.Collection;

@MessagingGateway
public interface PrepareServiceGateway {
    @Gateway(requestChannel = "assemblingChannel", replyChannel = "prepareAssemblyChannel")
    Collection<PrepareAssemblyResult> prepare(Collection<CarResult> res);
}
