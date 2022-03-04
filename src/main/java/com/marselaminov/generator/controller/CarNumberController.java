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

    @GetMapping("/random")
    public String getRandom() {
        CarNumber carNumber = new CarNumber();
        carNumber.setNumber(service.randomNum());
        service.save(carNumber);
        System.out.println(service.findById(carNumber.getId()));
        return carNumber.getNumber();
    }

    @GetMapping("/next")
    public String getNext() throws CarNumberException {
        CarNumber carNumber = new CarNumber();
        carNumber.setNumber(service.nextNum());
        service.save(carNumber);
        System.out.println(service.findById(carNumber.getId()));
        return carNumber.getNumber();
    }
}
