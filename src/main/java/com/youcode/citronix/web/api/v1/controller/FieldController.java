package com.youcode.citronix.web.api.v1.controller;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import com.youcode.citronix.service.FarmService;
import com.youcode.citronix.service.FieldService;
import com.youcode.citronix.web.vm.field.FieldResponseVM;
import com.youcode.citronix.web.vm.field.FieldVM;
import com.youcode.citronix.web.vm.mapper.FieldVmMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    private final FieldService fieldService;
    private final FarmService farmService;
    private final FieldVmMapper fieldVmMapper;

    public FieldController(FieldService fieldService, FieldVmMapper fieldVmMapper, @Qualifier("farmServiceImpl1") FarmService farmService) {
        this.fieldService = fieldService;
        this.fieldVmMapper = fieldVmMapper;
        this.farmService = farmService;
    }

    @PostMapping("/save")
    public ResponseEntity<FieldResponseVM> save(@RequestBody @Valid FieldVM fieldVM) {
        Field field = fieldVmMapper.toEntity(fieldVM);
        Farm fieldFarm = farmService.findById(UUID.fromString(fieldVM.getFarmId()));
        field.setFarm(fieldFarm);
        Field savedField = fieldService.save(field);
        fieldFarm.addField(savedField);
        savedField.setFarm(fieldFarm);
        FieldResponseVM response = fieldVmMapper.toFieldResponseVM(savedField);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{fieldId}")
    public ResponseEntity<FieldResponseVM> update(@RequestBody @Valid FieldVM fieldVM, @PathVariable UUID fieldId) {
        Field field = fieldVmMapper.toEntity(fieldVM);
        Field updatedField = fieldService.update(field, fieldId);
        FieldResponseVM fieldResponseVM = fieldVmMapper.toFieldResponseVM(updatedField);
        return new ResponseEntity<>(fieldResponseVM, HttpStatus.OK);
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<FieldResponseVM> findById(@PathVariable UUID fieldId) {
        Field field = fieldService.findById(fieldId);
        FieldResponseVM fieldResponseVM = fieldVmMapper.toFieldResponseVM(field);
        return new ResponseEntity<>(fieldResponseVM, HttpStatus.OK);
    }

    @GetMapping("/all/{farmId}")
    public ResponseEntity<Page<FieldResponseVM>> findAllByFarm(@PathVariable String farmId, Pageable pageable) {
        Page<Field> fields = fieldService.findAllByFarm(UUID.fromString(farmId), pageable);
        Page<FieldResponseVM> fieldResponseVMs = fields.map(fieldVmMapper::toFieldResponseVM);
        return new ResponseEntity<>(fieldResponseVMs, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fieldId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable UUID fieldId) {
        fieldService.delete(fieldId);
        return new ResponseEntity<>(Map.of("message", "Field deleted successfully"), HttpStatus.OK);
    }
}