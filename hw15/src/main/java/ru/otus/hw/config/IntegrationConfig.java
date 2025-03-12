package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.hw.services.AssembleService;
import ru.otus.hw.services.TransferOfPossessionService;
import ru.otus.hw.services.PrepareAssembleService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> orderChannel() {
        return MessageChannels.queue(20);
    }

    @Bean
    public MessageChannelSpec<?, ?> assemblingChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> prepareAssemblyChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> transferOfPossessionChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public IntegrationFlow buildFlow(AssembleService service) {
        return IntegrationFlow.from(orderChannel())
                .split()
                .handle(service, "assemble")
                .aggregate()
                .channel(prepareAssemblyChannel())
                .get();
    }

    @Bean
    public IntegrationFlow prepareFlow(PrepareAssembleService prepareService,
                                       TransferOfPossessionService transferOfPossessionService) {
        return IntegrationFlow.from(prepareAssemblyChannel())
                .split()
                .handle(prepareService, "prepare")
                .handle(transferOfPossessionService, "transferOfPossession")
                .aggregate()
                .channel(transferOfPossessionChannel())
                .get();
    }
}
