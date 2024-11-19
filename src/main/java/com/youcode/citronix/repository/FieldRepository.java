package com.youcode.citronix.repository;

import com.youcode.citronix.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {

}
