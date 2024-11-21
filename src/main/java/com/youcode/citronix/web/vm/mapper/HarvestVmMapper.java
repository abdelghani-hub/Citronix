package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.web.vm.harvest.HarvestVM;
import com.youcode.citronix.web.vm.harvest.HarvestResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestVmMapper {
    Harvest toEntity(HarvestVM harvestVM);
    Harvest toEntity(HarvestResponseVM harvestResponseVm);

    HarvestVM toHarvestVM(Harvest harvest);
    HarvestResponseVM toHarvestResponseVM(Harvest harvest);

}
