package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Tree;
import com.youcode.citronix.web.vm.tree.TreeResponseVM;
import com.youcode.citronix.web.vm.tree.TreeVM;

public interface TreeVmMapper {
    Tree toEntity(TreeVM treeVM);
    Tree toEntity(TreeResponseVM treeResponseVm);

    TreeVM toTreeVM(Tree tree);
    TreeResponseVM toTreeResponseVM(Tree tree);

}
