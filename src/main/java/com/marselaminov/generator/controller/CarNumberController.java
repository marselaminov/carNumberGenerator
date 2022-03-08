package com.marselaminov.generator.controller;

import com.marselaminov.generator.model.CarNumber;
import com.marselaminov.generator.service.CarNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CarNumberController {
    @Autowired
    private CarNumberService service;

    // 1728000 = (12 * 10 * 10 * 10 * 12 * 12) - максимальный размер возможных комбинаций
    private static final long maxRange = 1728000;

    @GetMapping("/random")
    public String getRandom() {
        CarNumber carNumber = new CarNumber();
        String num = service.randomNum();

        if (service.getSizeOfCarNumbersTable() == maxRange) {
            return "There are no more car number combinations";
        }
        if (Objects.equals(num, "Car numbers is over!")) {
            return "Car numbers is over!";
        }
        carNumber.setNumber(num);
        return carNumber.getNumber();
    }

    @GetMapping("/next")
    public String getNext() {
        CarNumber carNumber = new CarNumber();
        String num = service.nextNum();

        if (service.getSizeOfCarNumbersTable() == maxRange) {
            return "There are no more car number combinations";
        }
        if (Objects.equals(num, "Car numbers is over!")) {
            return "Car numbers is over!";
        }
        carNumber.setNumber(num);
        return carNumber.getNumber();
    }
}
