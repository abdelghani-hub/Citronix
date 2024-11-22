package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Sale;
import com.youcode.citronix.web.vm.sale.SaleResponseVM;
import com.youcode.citronix.web.vm.sale.SaleVM;

public interface SaleVmMapper {
    Sale toEntity(SaleVM saleVM);
    Sale toEntity(SaleResponseVM saleResponseVm);

    SaleVM toSaleVM(Sale sale);
    SaleResponseVM toSaleResponseVM(Sale sale);

}
