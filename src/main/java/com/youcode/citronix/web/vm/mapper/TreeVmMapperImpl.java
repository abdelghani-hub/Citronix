package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Tree;
import com.youcode.citronix.web.vm.tree.TreeEditVM;
import com.youcode.citronix.web.vm.tree.TreeResponseVM;
import com.youcode.citronix.web.vm.tree.TreeVM;
import org.springframework.stereotype.Component;

@Component
public class TreeVmMapperImpl implements TreeVmMapper {
    public TreeVmMapperImpl() {
    }

    public Tree toEntity(TreeVM treeVM) {
        if (treeVM == null) {
            return null;
        } else {
            Tree tree = new Tree();
            tree.setPlantingDate(treeVM.getPlantingDate());
            return tree;
        }
    }

    public Tree toEntity(TreeEditVM treeEditVM) {
        if (treeEditVM == null) {
            return null;
        } else {
            Tree tree = new Tree();
            if (treeEditVM.getPlantingDate() != null) {
                tree.setPlantingDate(treeEditVM.getPlantingDate());
            }

            return tree;
        }
    }

    public Tree toEntity(TreeResponseVM treeResponseVm) {
        if (treeResponseVm == null) {
            return null;
        } else {
            Tree tree = new Tree();
            tree.setPlantingDate(treeResponseVm.getPlantingDate());
            return tree;
        }
    }

    public TreeVM toTreeVM(Tree tree) {
        if (tree == null) {
            return null;
        } else {
            TreeVM treeVM = new TreeVM();
            treeVM.setPlantingDate(tree.getPlantingDate());
            treeVM.setFieldId(tree.getField().getId().toString());
            return treeVM;
        }
    }

    public TreeEditVM toTreeEditVM(Tree tree) {
        if (tree == null) {
            return null;
        } else {
            TreeEditVM treeEditVM = new TreeEditVM();
            if (tree.getPlantingDate() != null) {
                treeEditVM.setPlantingDate(tree.getPlantingDate());
            }

            return treeEditVM;
        }
    }

    public TreeResponseVM toTreeResponseVM(Tree tree) {
        if (tree == null) {
            return null;
        } else {
            TreeResponseVM treeResponseVM = new TreeResponseVM();
            treeResponseVM.setPlantingDate(tree.getPlantingDate());
            treeResponseVM.setFieldId(tree.getField().getId());
            treeResponseVM.setFarmName(tree.getField().getFarm().getName());
            return treeResponseVM;
        }
    }
}
