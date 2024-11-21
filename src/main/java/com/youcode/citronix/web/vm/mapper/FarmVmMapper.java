package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.web.vm.farm.FarmEditVM;
import com.youcode.citronix.web.vm.farm.FarmResponseVM;
import com.youcode.citronix.web.vm.farm.FarmVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmVmMapper {
    Farm toEntity(FarmVM farmVM);
    Farm toEntity(FarmEditVM FarmEditVM);
    Farm toEntity(FarmResponseVM farmResponseVM);

    FarmVM toFarmVM(Farm farm);
    FarmEditVM toEditVM(Farm farm);
    FarmResponseVM toResponseVM(Farm farm);
}
