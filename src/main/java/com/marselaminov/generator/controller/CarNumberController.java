package com.marselaminov.generator.controller;
import com.marselaminov.generator.exception.CarNumberException;
import com.marselaminov.generator.model.CarNumber;
import com.marselaminov.generator.service.CarNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarNumberController {
    @Autowired
    private CarNumberService service;

    // 1728000 = (12 * 10 * 10 * 10 * 12 * 12) - максимальный размер возможных комбинаций
    private static final long maxRange = 1728000;

    @GetMapping("/random")
    public String getRandom() throws CarNumberException {
        CarNumber carNumber = new CarNumber();

        if (service.getSizeOfCarNumbersTable() == maxRange)
            throw new CarNumberException();

        carNumber.setNumber(service.randomNum());
        service.save(carNumber);

        return carNumber.getNumber();
    }

    @GetMapping("/next")
    public String getNext() throws CarNumberException {
        CarNumber carNumber = new CarNumber();

        if (service.getSizeOfCarNumbersTable() == maxRange)
            throw new CarNumberException();

        carNumber.setNumber(service.nextNum());
        service.save(carNumber);

        return carNumber.getNumber();
    }
}
