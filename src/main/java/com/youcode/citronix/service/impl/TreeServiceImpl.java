package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Tree;
import com.youcode.citronix.exception.NotValidConstraintException;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.TreeRepository;
import com.youcode.citronix.service.FieldService;
import com.youcode.citronix.service.TreeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldService fieldService;

    public TreeServiceImpl(TreeRepository treeRepository, FieldService fieldService) {
        this.treeRepository = treeRepository;
        this.fieldService = fieldService;
    }

    @Override
    public Tree save(Tree tree) {
        this.validateTreeSpacing(tree.getField(), tree);
        return treeRepository.save(tree);
    }

    @Override
    public Tree update(Tree tree, UUID treeId) {
        Tree existingTree = treeRepository.findById(treeId).orElseThrow(() -> new ResourceNotFoundException("Tree"));
        existingTree.setPlantingDate(tree.getPlantingDate());
        return treeRepository.save(existingTree);
    }

    @Override
    public Tree findById(UUID id) {
        return treeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tree"));
    }

    @Override
    public Page<Tree> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(UUID id) {
        if (!treeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tree");
        }
        treeRepository.deleteById(id);
    }

    @Override
    public void deleteAllByField(Field field) {
        treeRepository.deleteAllByField(field);
    }

    @Override
    public Page<Tree> findAllByField(UUID fieldId, Pageable pageable) {
        Field field = fieldService.findById(fieldId);
        return treeRepository.findAllByField(field, pageable);
    }

    @Override
    public void validateTreeSpacing(Field field, Tree tree) {
        // Constraint n°4 : 100 Tree for 1 Hectare;
        double FieldAvailableArea = field.getArea() - (field.getTrees().size() * Field.TreeSpacingForHectare);
        if (FieldAvailableArea < Field.TreeSpacingForHectare) {
            throw new NotValidConstraintException("Field is full, you can't add more trees");
        }
    }
}