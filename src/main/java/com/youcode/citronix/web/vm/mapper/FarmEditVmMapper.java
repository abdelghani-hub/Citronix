package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.web.vm.farm.FarmEditVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmEditVmMapper {
    Farm toEntity(FarmEditVM farmEditVM);
    FarmEditVM toVM(Farm farm);
}
