package com.youcode.citronix.config;

import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.repository.FieldRepository;
import com.youcode.citronix.repository.TreeRepository;
import com.youcode.citronix.service.FieldService;
import com.youcode.citronix.service.TreeService;
import com.youcode.citronix.service.impl.FieldServiceImpl;
import com.youcode.citronix.service.impl.TreeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public FieldService fieldService(FieldRepository fieldRepository, FarmRepository farmRepository) {
        FieldServiceImpl fieldService = new FieldServiceImpl(fieldRepository, farmRepository);
        return fieldService;
    }

    @Bean
    public TreeService treeService(TreeRepository treeRepository, FieldService fieldService) {
        TreeServiceImpl treeService = new TreeServiceImpl(treeRepository, fieldService);
        ((FieldServiceImpl) fieldService).setTreeService(treeService);
        return treeService;
    }
}