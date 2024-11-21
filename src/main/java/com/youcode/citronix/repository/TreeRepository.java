package com.youcode.citronix.repository;

import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Tree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {
    Page<Tree> findAllByField(Field field, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Tree t WHERE t.field = :field")
    void deleteAllByField(Field field);
}
