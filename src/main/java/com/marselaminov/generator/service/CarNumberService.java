package com.marselaminov.generator.service;

import com.marselaminov.generator.exception.CarNumberException;
import com.marselaminov.generator.model.CarNumber;
import com.marselaminov.generator.repository.CarNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarNumberService {
    @Autowired
    private CarNumberRepository repository;

    private final static String suffix = " 116 RUS";

    private final static List<Character> numberLetters =
            "АЕТОРНУКХСВМ".chars().mapToObj(c -> (char) c).collect(Collectors.toList());

    private int index = 995;
    private static int firstLetter = 11;
    private static int secondLetter = 11;
    private static int thirdLetter = 11;

    public String nextNum() throws CarNumberException {
        StringBuilder allNumber = new StringBuilder();
        StringBuilder onlyDigits = new StringBuilder();

        boundsCheck();

        allNumber.append(numberLetters.get(firstLetter));

        if (index < 10)
            onlyDigits.append("00");
        else if (index < 100)
            onlyDigits.append("0");
        onlyDigits.append(index);

        allNumber.append(onlyDigits);
        allNumber.append(numberLetters.get(secondLetter));
        allNumber.append(numberLetters.get(thirdLetter));
        allNumber.append(suffix);

        index += 1;

        return allNumber.toString();
    }

    public void boundsCheck() throws CarNumberException {
        if (index > 999) {
            firstLetter += 1;
            index = 0;
        }
        else if (firstLetter >= numberLetters.size()) { // как только перебрали все вариации первой буквы
            secondLetter += 1; // перебираем вторую
            firstLetter = 0; // смещаем первую обратно в начало
        }
        // то же самое производим со второй буквой номера
        else if (secondLetter >= numberLetters.size()) {
            thirdLetter += 1;
            secondLetter = 0;
        }
        else if (thirdLetter >= numberLetters.size())
            throw new CarNumberException();
    }

    public String randomNum() {
        StringBuilder allNumber = new StringBuilder();
        StringBuilder onlyDigits = new StringBuilder();

        allNumber.append(numberLetters.get((int) (Math.random() * numberLetters.size()))); // будут числа индекса от 0 до размера size - 1

        for (int i = 0; i <= 2; i++) {
            onlyDigits.append((int) (Math.random() * 10));
        }
        allNumber.append(onlyDigits);

        allNumber.append(numberLetters.get((int) (Math.random() * numberLetters.size())));
        allNumber.append(numberLetters.get((int) (Math.random() * numberLetters.size())));
        allNumber.append(suffix);

        return allNumber.toString();
    }

    public void save(CarNumber number) {
        repository.save(number);
    }

    public List<CarNumber> findAll() {
        return repository.findAll();
    }

    public Optional<CarNumber> findById(Long id) {
        return repository.findById(id);
    }
}
