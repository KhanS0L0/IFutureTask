package com.example.server.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

import org.hibernate.Hibernate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Objects;

/**
 * {@link Entity} модель счета.
 * @author KhanSOLO
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "balance")
public class Balance {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Balance balance = (Balance) o;
        return getId() != null && Objects.equals(getId(), balance.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
