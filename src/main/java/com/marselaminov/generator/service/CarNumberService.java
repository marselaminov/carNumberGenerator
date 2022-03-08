package com.marselaminov.generator.service;

import com.marselaminov.generator.model.CarNumber;
import com.marselaminov.generator.repository.CarNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarNumberService {
    @Autowired
    private CarNumberRepository repository;

    private final static String SUFFIX = " 116 RUS";

    // храню в списке согласно условию проекта
    private final static List<Character> NUMBER_LETTERS =
            "АЕТОРНУКХСВМ".chars().sorted().mapToObj(c -> (char) c).collect(Collectors.toList());

    private static int index;
    private static int firstLetter;
    private static int secondLetter;
    private static int thirdLetter;

    @Transactional
    public String nextNum() {
        StringBuilder allNumber = new StringBuilder();
        StringBuilder onlyDigits = new StringBuilder();
        CarNumber lastCarNumber = repository.getLastElementFromTable();

        // получаем последнюю запись таблицы и если ее нет возвращаем "А000АА 116 RUS"
        if (init(lastCarNumber) == null) {
            return "А000АА 116 RUS";
        }
        if (boundsCheck() == null) { // проверяем границы диапазонов
            return "Car numbers is over!";
        }

        // далее соединяем полный номер частями
        allNumber.append(NUMBER_LETTERS.get(firstLetter)); //1
        if (index < 10) {
            onlyDigits.append("00");
        } else if (index < 100) {
            onlyDigits.append("0");
        }
        onlyDigits.append(index);
        allNumber.append(onlyDigits); // 2
        allNumber.append(NUMBER_LETTERS.get(secondLetter)); // 3
        allNumber.append(NUMBER_LETTERS.get(thirdLetter)); // 4
        allNumber.append(SUFFIX); // const
        if (lastCarNumber.getNumber().equals("Х999ХХ 116 RUS")) {
            return "Car numbers is over!";
        } else if (allNumber.toString().equals(lastCarNumber.getNumber())) {
            return "The number must not be repeated, please try again!";
        }
        repository.save(CarNumber.builder().number(allNumber.toString()).build()); // сохраняем в БД
        return allNumber.toString();
    }

    private String init(CarNumber lastCarNumber) {
        if (lastCarNumber == null) {
            repository.save(CarNumber.builder().number("А000АА 116 RUS").build());
            return null;
        }
        index = Integer.parseInt(lastCarNumber.getNumber().substring(1, 4));
        index += 1;
        firstLetter = NUMBER_LETTERS.indexOf(lastCarNumber.getNumber().charAt(0));
        secondLetter = NUMBER_LETTERS.indexOf(lastCarNumber.getNumber().charAt(4));
        thirdLetter = NUMBER_LETTERS.indexOf(lastCarNumber.getNumber().charAt(5));
        return "ok";
    }

    private String boundsCheck() throws IndexOutOfBoundsException{
        if (index > 999 && thirdLetter + 1 >= NUMBER_LETTERS.size()) {
            return null;
        } else if (index > 999) {
            thirdLetter += 1;
            index = 0;
        } else if (thirdLetter >= NUMBER_LETTERS.size()) { // как только перебрали все вариации третьей буквы
            secondLetter += 1; // перебираем вторую
            thirdLetter = 0; // смещаем первую обратно в начало
        } else if (secondLetter >= NUMBER_LETTERS.size()) { // то же самое производим со второй буквой номера
            firstLetter += 1;
            secondLetter = 0;
        } else if (firstLetter >= NUMBER_LETTERS.size()) { // если перебрали все комбинации
            firstLetter = 0;
        }
        return "ok";
    }

    @Transactional
    public String randomNum() {
        StringBuilder allNumber = new StringBuilder();
        StringBuilder onlyDigits = new StringBuilder();
        CarNumber lastCarNumber = repository.getLastElementFromTable();

        // будут числа индекса от 0 до размера size - 1
        allNumber.append(NUMBER_LETTERS.get((int) (Math.random() * NUMBER_LETTERS.size())));
        for (int i = 0; i <= 2; i++) {
            onlyDigits.append((int) (Math.random() * 10));
        }
        allNumber.append(onlyDigits);
        allNumber.append(NUMBER_LETTERS.get((int) (Math.random() * NUMBER_LETTERS.size())));
        allNumber.append(NUMBER_LETTERS.get((int) (Math.random() * NUMBER_LETTERS.size())));
        allNumber.append(SUFFIX);
        if (lastCarNumber == null) {
            repository.save(CarNumber.builder().number(allNumber.toString()).build());
            return allNumber.toString();
        } else if (lastCarNumber.getNumber().equals("Х999ХХ 116 RUS")) {
            return "Car numbers is over!";
        } else if (allNumber.toString().equals(lastCarNumber.getNumber())) {
            return "The number must not be repeated, please try again!";
        }
        repository.save(CarNumber.builder().number(allNumber.toString()).build()); // сохраняем в БД
        return allNumber.toString();
    }

    @Transactional
    public void save(CarNumber number) { // для теста
        repository.save(number);
    }

    @Transactional
    public Long getSizeOfCarNumbersTable() {
        return repository.getCarNumberAllSize();
    }
}
