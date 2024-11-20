package com.youcode.citronix.repository;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    Page<Field> findAllByFarm(Farm farm, Pageable pageable);
}
