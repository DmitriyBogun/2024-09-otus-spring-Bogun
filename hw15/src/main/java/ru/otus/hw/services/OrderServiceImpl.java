package ru.otus.hw.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.CarColor;
import ru.otus.hw.domain.CarOrder;
import ru.otus.hw.domain.CarBodyTypes;
import ru.otus.hw.domain.EngineTypes;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService, CommandLineRunner {

    private final AssembleServiceGateway build;

    @Override
    public void startGenerateOrdersLoop() {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        try (Closeable ignored = pool::shutdown) {
            for (int i = 0; i < 1; i++) {
                int num = i + 1;
                pool.execute(() -> {
                    Collection<CarOrder> items = generateOrderItems();
                    log.info("{}, New orderItems: {}", num, items.stream()
                            .map(CarOrder::toString)
                            .collect(Collectors.joining(", ")));
                    build.assembling(items);
                });
                delay();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private static CarOrder generateOrderItem() {
        Random random = new Random();
        CarBodyTypes[] foundations = CarBodyTypes.values();
        EngineTypes[] engineTypes = EngineTypes.values();
        CarColor[] carColors = CarColor.values();

        return new CarOrder(
                foundations[random.nextInt(0, foundations.length)].getTypeName(),
                engineTypes[random.nextInt(0, engineTypes.length)].getTypeName(),
                carColors[random.nextInt(0, carColors.length)].getColor()
        );
    }

    private static Collection<CarOrder> generateOrderItems() {
        Random random = new Random();
        List<CarOrder> items = new ArrayList<>();
        for (int idx = 0; idx < random.nextInt(1, 10); idx++) {
            items.add(generateOrderItem());
        }
        return items;
    }

    private void delay() {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run(String... args) {
        startGenerateOrdersLoop();
    }
}
