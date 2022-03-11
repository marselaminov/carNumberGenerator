package com.marselaminov.generator.service;

import com.marselaminov.generator.model.CarNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarNumberServiceTest {
    @Autowired
    private CarNumberService service;

    @Test
    public void getNewThirdLetterAndZeroWhenNumberIsLastTest() {
        service.save(CarNumber.builder().number("А999АА 116 RUS").build());
        assertEquals("А000АВ 116 RUS", service.nextNum());
    }

    @Test
    public void overAllNumberCombinationTest() {
        if (service.randomNum().equals("Х999ХХ 116 RUS")) {
            assertEquals("Car numbers is over!", service.nextNum());
        }
    }

    @Test
    public void getErrorIfTableIsFull() {
        long maxTableSize = 1728000;
        if (service.getSizeOfCarNumbersTable() == maxTableSize) {
            assertEquals("There are no more car number combinations", service.nextNum());
        }
    }

    @Test
    public void getRandomAndThenCallNextTest() {
        if (service.randomNum().equals("Е187ОУ 116 RUS")) {
            assertEquals("Е188ОУ 116 RUS", service.nextNum());
        }
    }
}
