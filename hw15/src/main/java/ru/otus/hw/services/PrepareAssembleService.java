package ru.otus.hw.services;

import ru.otus.hw.domain.CarResult;
import ru.otus.hw.domain.PrepareAssemblyResult;

public interface PrepareAssembleService {
    PrepareAssemblyResult prepare(CarResult result);
}
