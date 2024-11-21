package com.youcode.citronix.web.api.v1.controller;

import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Tree;
import com.youcode.citronix.service.FieldService;
import com.youcode.citronix.service.TreeService;
import com.youcode.citronix.web.vm.mapper.TreeVmMapper;
import com.youcode.citronix.web.vm.tree.TreeEditVM;
import com.youcode.citronix.web.vm.tree.TreeResponseVM;
import com.youcode.citronix.web.vm.tree.TreeVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trees")
public class TreeController {

    private final TreeService treeService;
    private final FieldService fieldService;
    private final TreeVmMapper treeVmMapper;

    public TreeController(TreeService treeService, FieldService fieldService, TreeVmMapper treeVmMapper) {
        this.treeService = treeService;
        this.fieldService = fieldService;
        this.treeVmMapper = treeVmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<TreeResponseVM> save(@RequestBody @Valid TreeVM treeVM) {
        Tree tree = treeVmMapper.toEntity(treeVM);
        Field treeField = fieldService.findById(UUID.fromString(treeVM.getFieldId()));
        tree.setField(treeField);
        Tree savedTree = treeService.save(tree);
        treeField.addTree(savedTree);
        TreeResponseVM response = treeVmMapper.toTreeResponseVM(savedTree);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{treeId}")
    public ResponseEntity<TreeResponseVM> update(@RequestBody @Valid TreeEditVM treeEditVM, @PathVariable UUID treeId) {
        Tree tree = treeVmMapper.toEntity(treeEditVM);
        Tree updatedTree = treeService.update(tree, treeId);
        TreeResponseVM treeResponseVM = treeVmMapper.toTreeResponseVM(updatedTree);
        return new ResponseEntity<>(treeResponseVM, HttpStatus.OK);
    }

    @GetMapping("/{treeId}")
    public ResponseEntity<TreeResponseVM> findById(@PathVariable UUID treeId) {
        Tree tree = treeService.findById(treeId);
        TreeResponseVM treeResponseVM = treeVmMapper.toTreeResponseVM(tree);
        return new ResponseEntity<>(treeResponseVM, HttpStatus.OK);
    }

    @GetMapping("/all/{fieldId}")
    public ResponseEntity<Page<TreeResponseVM>> findAllByFarm(@PathVariable String fieldId, Pageable pageable) {
        Page<Tree> trees = treeService.findAllByField(UUID.fromString(fieldId), pageable);
        Page<TreeResponseVM> fieldResponseVMs = trees.map(treeVmMapper::toTreeResponseVM);
        return new ResponseEntity<>(fieldResponseVMs, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{treeId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable UUID treeId) {
        treeService.delete(treeId);
        return new ResponseEntity<>(Map.of("message", "Tree deleted successfully"), HttpStatus.OK);
    }
}