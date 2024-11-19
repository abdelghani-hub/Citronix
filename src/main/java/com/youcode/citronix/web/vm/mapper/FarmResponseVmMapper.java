package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.web.vm.farm.FarmResponseVM;
import com.youcode.citronix.web.vm.farm.FarmVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmResponseVmMapper {
    Farm toEntity(FarmResponseVM farmVM);
    FarmResponseVM toVM(Farm farm);
}
