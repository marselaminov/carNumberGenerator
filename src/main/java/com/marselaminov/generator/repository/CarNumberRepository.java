package com.marselaminov.generator.repository;

import com.marselaminov.generator.model.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarNumberRepository extends JpaRepository<CarNumber, Long> {
    @Query(value ="select * from car_number order by id desc limit 1", nativeQuery = true)
    CarNumber getLastElementFromTable();

    @Query(value = "select count(*) from car_number", nativeQuery = true)
    Long getCarNumberAllSize();
}
