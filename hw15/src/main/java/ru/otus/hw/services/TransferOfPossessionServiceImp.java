package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.FinishedAssembly;
import ru.otus.hw.domain.PrepareAssemblyResult;

@Service
@Slf4j
public class TransferOfPossessionServiceImp implements TransferOfPossessionService {
    @Override
    public FinishedAssembly transferOfPossession(PrepareAssemblyResult assemblyResult) {
        log.info("Transfer of possession {}", assemblyResult.toString());
        return new FinishedAssembly(assemblyResult, true);
    }
}
