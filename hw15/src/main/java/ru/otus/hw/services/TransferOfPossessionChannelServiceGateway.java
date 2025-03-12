package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.domain.FinishedAssembly;
import ru.otus.hw.domain.PrepareAssemblyResult;

import java.util.Collection;

@MessagingGateway
public interface TransferOfPossessionChannelServiceGateway {
    @Gateway(requestChannel = "prepareAssemblyChannel", replyChannel = "transferOfPossessionChannel")
    Collection<FinishedAssembly> transferOfPossession(Collection<PrepareAssemblyResult> res);
}
