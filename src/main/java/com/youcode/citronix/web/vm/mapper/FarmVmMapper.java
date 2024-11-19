package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.web.vm.farm.FarmVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmVmMapper {
    Farm toEntity(FarmVM farmVM);
    FarmVM toVM(Farm farm);
}
