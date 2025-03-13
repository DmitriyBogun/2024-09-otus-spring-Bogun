package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.CarResult;
import ru.otus.hw.domain.PrepareAssemblyResult;

@Service
@Slf4j
public class PrepareAssembleServiceImpl implements PrepareAssembleService {
    @Override
    public PrepareAssemblyResult prepare(CarResult carResult) {
        log.info("Prepare assemble {}", carResult.toString());
        PrepareAssemblyResult prepareAssemblyResult =  new PrepareAssemblyResult(carResult);
        log.info("Power steering installation");
        prepareAssemblyResult.setHasPowerSteering(true);
        log.info("Air conditioner installation");
        prepareAssemblyResult.setHasAirConditioner(true);
        return prepareAssemblyResult;
    }
}
