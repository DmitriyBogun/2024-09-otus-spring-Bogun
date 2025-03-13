package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.domain.CarOrder;
import ru.otus.hw.domain.CarResult;

import java.util.Collection;

@MessagingGateway
public interface AssembleServiceGateway {
    @Gateway(requestChannel = "orderChannel", replyChannel = "assemblingChannel")
    Collection<CarResult> assembling(Collection<CarOrder> orders);
}
