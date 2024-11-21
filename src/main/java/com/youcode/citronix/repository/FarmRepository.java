package com.youcode.citronix.repository;

import com.youcode.citronix.domain.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm , UUID> {

    Optional<Farm> findByName(String name);
    Page<Farm> findAll(Pageable pageable);

    boolean existsByNameAndIdNot(String name, UUID id);
}
