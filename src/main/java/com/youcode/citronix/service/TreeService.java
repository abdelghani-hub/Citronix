package com.youcode.citronix.service;

import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Tree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TreeService {

    Tree save(Tree tree);
    Tree update(Tree tree, UUID treeId);
    Tree findById(UUID id);
    Page<Tree> findAll(Pageable pageable);
    void delete(UUID id);
    Page<Tree> findAllByField(UUID fieldId, Pageable pageable);

    void validate(Field field, Tree tree);

    void deleteAllByField(Field filed);
}
