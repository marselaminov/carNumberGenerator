package com.marselaminov.generator.repository;

import com.marselaminov.generator.model.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarNumberRepository extends JpaRepository<CarNumber, Long> {
}
