package com.marselaminov.generator.model;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "car_number")
public class CarNumber {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Override
    public String toString() {
        return "CarNumber{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarNumber carNumber = (CarNumber) o;
        return id == carNumber.id && Objects.equals(number, carNumber.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
