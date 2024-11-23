package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Sale;
import com.youcode.citronix.web.vm.sale.SaleResponseVM;
import com.youcode.citronix.web.vm.sale.SaleVM;
import org.springframework.stereotype.Component;

@Component
public class SaleVmMapperImpl implements SaleVmMapper {
    public SaleVmMapperImpl() {
    }

    public Sale toEntity(SaleVM saleVM) {
        if (saleVM == null) {
            return null;
        } else {
            Sale sale = new Sale();
            if (saleVM.getUnitPrice() != null) {
                sale.setUnitPrice(Double.parseDouble(saleVM.getUnitPrice()));
            }

            sale.setClientName(saleVM.getClientName());
            sale.setQuantity(saleVM.getQuantity());
            sale.setDate(saleVM.getDate());
            return sale;
        }
    }

    public Sale toEntity(SaleResponseVM saleResponseVm) {
        if (saleResponseVm == null) {
            return null;
        } else {
            Sale sale = new Sale();
            if (saleResponseVm.getUnitPrice() != null) {
                sale.setUnitPrice(Double.parseDouble(saleResponseVm.getUnitPrice()));
            }

            sale.setClientName(saleResponseVm.getClientName());
            sale.setQuantity(saleResponseVm.getQuantity());
            sale.setDate(saleResponseVm.getDate());
            return sale;
        }
    }

    public SaleVM toSaleVM(Sale sale) {
        if (sale == null) {
            return null;
        } else {
            SaleVM saleVM = new SaleVM();
            saleVM.setUnitPrice(String.valueOf(sale.getUnitPrice()));
            saleVM.setClientName(sale.getClientName());
            saleVM.setQuantity(sale.getQuantity());
            saleVM.setDate(sale.getDate());
            return saleVM;
        }
    }

    public SaleResponseVM toSaleResponseVM(Sale sale) {
        if (sale == null) {
            return null;
        } else {
            SaleResponseVM saleResponseVM = new SaleResponseVM();
            saleResponseVM.setId(sale.getId().toString());
            saleResponseVM.setUnitPrice(String.valueOf(sale.getUnitPrice()));
            saleResponseVM.setClientName(sale.getClientName());
            saleResponseVM.setQuantity(sale.getQuantity());
            saleResponseVM.setDate(sale.getDate());
            saleResponseVM.setRevenue(sale.getRevenue());
            saleResponseVM.setLocation("/sales/" + sale.getId());
            return saleResponseVM;
        }
    }
}
