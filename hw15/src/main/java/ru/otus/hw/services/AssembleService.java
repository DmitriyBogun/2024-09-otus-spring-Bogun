package ru.otus.hw.services;

import ru.otus.hw.domain.CarOrder;
import ru.otus.hw.domain.CarResult;

public interface AssembleService {
    CarResult assemble(CarOrder order);
}
