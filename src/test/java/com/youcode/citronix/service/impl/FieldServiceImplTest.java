package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.repository.FieldRepository;
import com.youcode.citronix.service.TreeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private FieldServiceImpl fieldService;
    private Farm testFarm;
    private Field testField;

    @BeforeEach
    void setUp() {
        fieldService = new FieldServiceImpl(fieldRepository, farmRepository);
        fieldService.setTreeService(treeService);

        // Initialize test data
        testFarm = new Farm();
        testFarm.setId(UUID.randomUUID());
        testFarm.setArea(100.0); // 100 hectares
        testFarm.setFields(new ArrayList<>());

        testField = new Field();
        testField.setId(UUID.randomUUID());
        testField.setArea(10.0); // 10 hectares
        testField.setFarm(testFarm);
    }

    @Test
    void save_ValidField_ShouldSaveSuccessfully() {
        // Arrange
        when(fieldRepository.save(any(Field.class))).thenReturn(testField);

        // Act
        Field savedField = fieldService.save(testField);

        // Assert
        assertNotNull(savedField);
        assertEquals(testField.getId(), savedField.getId());
        verify(fieldRepository).save(testField);
    }

    @Test
    void save_FieldAreaTooSmall_ShouldThrowException() {
        // Arrange
        testField.setArea(0.05); // Less than minimum 0.1 hectares

        // Act & Assert
        assertThrows(RuntimeException.class, () -> fieldService.save(testField));
    }

    @Test
    void save_FieldAreaTooLarge_ShouldThrowException() {
        // Arrange
        testField.setArea(60.0); // More than 50% of farm area

        // Act & Assert
        assertThrows(RuntimeException.class, () -> fieldService.save(testField));
    }

    @Test
    void save_TotalAreaExceedsFarmArea_ShouldThrowException() {
        // Arrange
        testField.setArea(101.0); // Exceeds farm area

        // Act & Assert
        assertThrows(RuntimeException.class, () -> fieldService.save(testField));
    }

    @Test
    void save_MaxFieldsExceeded_ShouldThrowException() {
        // Arrange
        for (int i = 0; i < 10; i++) {
            Field field = new Field();
            field.setArea(1.0);
            testFarm.addField(field);
        }

        // Act & Assert
        assertThrows(RuntimeException.class, () -> fieldService.save(testField));
    }

    @Test
    void update_ExistingField_ShouldUpdateSuccessfully() {
        // Arrange
        Field updatedField = new Field();
        updatedField.setId(testField.getId());
        updatedField.setArea(15.0);
        updatedField.setFarm(testFarm);

        when(fieldRepository.findById(testField.getId())).thenReturn(Optional.of(testField));
        when(fieldRepository.save(any(Field.class))).thenReturn(updatedField);

        // Act
        Field result = fieldService.update(updatedField);

        // Assert
        assertNotNull(result);
        assertEquals(15.0, result.getArea());
        verify(fieldRepository).save(any(Field.class));
    }

    @Test
    void findById_ExistingField_ShouldReturnField() {
        // Arrange
        when(fieldRepository.findById(testField.getId())).thenReturn(Optional.of(testField));

        // Act
        Field result = fieldService.findById(testField.getId());

        // Assert
        assertNotNull(result);
        assertEquals(testField.getId(), result.getId());
    }

    @Test
    void findById_NonExistingField_ShouldThrowException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(fieldRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> fieldService.findById(nonExistingId));
    }

    @Test
    void findAll_ShouldReturnPageOfFields() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Field> expectedPage = new PageImpl<>(Arrays.asList(testField));
        when(fieldRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Field> result = fieldService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(fieldRepository).findAll(pageable);
    }

    @Test
    void findAllByFarm_ValidFarmId_ShouldReturnFields() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Field> expectedPage = new PageImpl<>(Arrays.asList(testField));
        when(farmRepository.findById(testFarm.getId())).thenReturn(Optional.of(testFarm));
        when(fieldRepository.findAllByFarm(testFarm, pageable)).thenReturn(expectedPage);

        // Act
        Page<Field> result = fieldService.findAllByFarm(testFarm.getId(), pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(fieldRepository).findAllByFarm(testFarm, pageable);
    }

    @Test
    void delete_ExistingField_ShouldDeleteSuccessfully() {
        // Arrange
        when(fieldRepository.findById(testField.getId())).thenReturn(Optional.of(testField));

        // Act
        fieldService.delete(testField.getId());

        // Assert
        verify(treeService).deleteAllByField(testField);
        verify(fieldRepository).delete(testField);
    }
}