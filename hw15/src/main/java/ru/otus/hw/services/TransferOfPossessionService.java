package ru.otus.hw.services;

import ru.otus.hw.domain.FinishedAssembly;
import ru.otus.hw.domain.PrepareAssemblyResult;

public interface TransferOfPossessionService {
    FinishedAssembly transferOfPossession(PrepareAssemblyResult res);
}
