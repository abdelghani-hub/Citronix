package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Tree;
import com.youcode.citronix.exception.NotValidConstraintException;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.repository.FieldRepository;
import com.youcode.citronix.service.TreeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private TreeService treeService;

    @InjectMocks
    private FieldServiceImpl fieldService;

    private Farm farm;
    private Field field;
    private UUID fieldId;
    private UUID farmId;

    @BeforeEach
    void setUp() {
        fieldId = UUID.randomUUID();
        farmId = UUID.randomUUID();

        farm = new Farm();
        farm.setId(farmId);
        farm.setArea(100.0); // 100 hectares

        field = new Field();
        field.setId(fieldId);
        field.setArea(10.0); // 10 hectares
        field.setFarm(farm);
        List<Tree> trees = new ArrayList<>();
        Tree tree1 = new Tree();
        Tree tree2 = new Tree();
        trees.add(tree1);
        trees.add(tree2);
        field.setTrees(trees);

        farm.addField(field);

        fieldService.setTreeService(treeService);
    }

    @Test
    void save_ValidField_ShouldSaveSuccessfully() {
        when(fieldRepository.save(any(Field.class))).thenReturn(field);

        Field savedField = fieldService.save(field);

        assertNotNull(savedField);
        assertEquals(field.getArea(), savedField.getArea());
        verify(fieldRepository).save(field);
    }

    @Test
    void save_FieldAreaTooSmall_ShouldThrowException() {
        field.setArea(0.05); // Less than minimum 0.1 hectares

        assertThrows(RuntimeException.class, () -> fieldService.save(field));
        verify(fieldRepository, never()).save(any());
    }

    @Test
    void save_FieldAreaExceedsFarmLimit_ShouldThrowException() {
        field.setArea(60.0); // More than 50% of farm area

        assertThrows(RuntimeException.class, () -> fieldService.save(field));
        verify(fieldRepository, never()).save(any());
    }

    @Test
    void update_ValidField_ShouldUpdateSuccessfully() {
        Field updatedField = new Field();
        updatedField.setArea(15.0);
        updatedField.setFarm(farm);
        List<Tree> trees = new ArrayList<>();
        Tree tree1 = new Tree();
        Tree tree2 = new Tree();
        trees.add(tree1);
        trees.add(tree2);
        updatedField.setTrees(trees);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
        when(fieldRepository.save(any(Field.class))).thenReturn(field);

        Field result = fieldService.update(updatedField, fieldId);

        assertNotNull(result);
        assertEquals(15.0, result.getArea());
        verify(fieldRepository).save(any(Field.class));
    }

    @Test
    void update_NonExistentField_ShouldThrowResourceNotFoundException() {
        when(fieldRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fieldService.update(field, UUID.randomUUID()));
    }

    @Test
    void update_AreaLessThanTreeSpacing_ShouldThrowException() {
        Field existingField = new Field();
        existingField.setFarm(farm);
        existingField.setArea(20.0);

        List<Tree> trees = new ArrayList<>();
        Tree tree1 = new Tree();
        Tree tree2 = new Tree();
        trees.add(tree1);
        trees.add(tree2);
        existingField.setTrees(trees);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));

        Field updateField = new Field();
        updateField.setFarm(farm);
        updateField.setTrees(trees);
        updateField.setArea(0.01); // Area too small for existing trees

        assertThrows(NotValidConstraintException.class,
                () -> fieldService.update(updateField, fieldId));
    }

    @Test
    void findById_ExistingField_ShouldReturnField() {
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        Field foundField = fieldService.findById(fieldId);

        assertNotNull(foundField);
        assertEquals(fieldId, foundField.getId());
    }

    @Test
    void findById_NonExistentField_ShouldThrowResourceNotFoundException() {
        when(fieldRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fieldService.findById(UUID.randomUUID()));
    }

    @Test
    void findAll_ShouldReturnPageOfFields() {
        List<Field> fields = Arrays.asList(field);
        Page<Field> fieldPage = new PageImpl<>(fields);
        Pageable pageable = PageRequest.of(0, 10);

        when(fieldRepository.findAll(pageable)).thenReturn(fieldPage);

        Page<Field> result = fieldService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(fieldRepository).findAll(pageable);
    }

    @Test
    void findAllByFarm_ValidFarm_ShouldReturnFields() {
        List<Field> fields = Arrays.asList(field);
        Page<Field> fieldPage = new PageImpl<>(fields);
        Pageable pageable = PageRequest.of(0, 10);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findAllByFarm(farm, pageable)).thenReturn(fieldPage);

        Page<Field> result = fieldService.findAllByFarm(farmId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(fieldRepository).findAllByFarm(farm, pageable);
    }

    @Test
    void findAllByFarm_NonExistentFarm_ShouldThrowResourceNotFoundException() {
        when(farmRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fieldService.findAllByFarm(UUID.randomUUID(), PageRequest.of(0, 10)));
    }

    @Test
    void delete_ExistingField_ShouldDeleteSuccessfully() {
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        fieldService.delete(fieldId);

        verify(treeService).deleteAllByField(field);
        verify(fieldRepository).delete(field);
    }

    @Test
    void delete_NonExistentField_ShouldThrowResourceNotFoundException() {
        when(fieldRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fieldService.delete(UUID.randomUUID()));
        verify(fieldRepository, never()).delete(any());
    }

    @Test
    void validate_TotalFieldsAreaExceedsFarmArea_ShouldThrowException() {
        Field newField = new Field();
        newField.setArea(95.0); // This would exceed farm's total area

        assertThrows(RuntimeException.class, () -> fieldService.validate(farm, newField));
    }

    @Test
    void validate_MaxFieldsLimitExceeded_ShouldThrowException() {
        // Add 10 fields to the farm
        for (int i = 0; i < 10; i++) {
            Field newField = new Field();
            newField.setArea(1.0);
            farm.addField(newField);
        }

        Field oneMoreField = new Field();
        oneMoreField.setArea(1.0);

        assertThrows(RuntimeException.class, () -> fieldService.validate(farm, oneMoreField));
    }
}