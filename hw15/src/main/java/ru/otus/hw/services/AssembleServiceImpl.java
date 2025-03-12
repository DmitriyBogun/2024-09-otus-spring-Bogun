package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.CarColor;
import ru.otus.hw.domain.CarBodyTypes;
import ru.otus.hw.domain.CarOrder;
import ru.otus.hw.domain.CarResult;
import ru.otus.hw.domain.EngineTypes;


@Service
@Slf4j
public class AssembleServiceImpl implements AssembleService {

    @Override
    public CarResult assemble(CarOrder order) {
        log.info("Start assembling {}", order.toString());
        return new CarResult(CarBodyTypes.getFromTypeName(order.getCarBodyType()),
                EngineTypes.getFromTypeName(order.getEngineType()),
                CarColor.getFromColorName(order.getColor()));
    }
}
