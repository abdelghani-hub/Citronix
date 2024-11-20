package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Field;
import com.youcode.citronix.web.vm.field.FieldEditVM;
import com.youcode.citronix.web.vm.field.FieldResponseVM;
import com.youcode.citronix.web.vm.field.FieldVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldVmMapper {
    Field toEntity(FieldVM fieldVM);
    Field toEntity(FieldEditVM fieldEditVM);
    Field toEntity(FieldResponseVM fieldResponseVm);

    FieldVM toFieldVM(Field field);
    FieldEditVM toFieldEditVM(Field field);
    FieldResponseVM toFieldResponseVM(Field field);

}
