package com.youcode.citronix.web.vm.mapper;

import com.youcode.citronix.domain.Tree;
import com.youcode.citronix.web.vm.tree.TreeEditVM;
import com.youcode.citronix.web.vm.tree.TreeResponseVM;
import com.youcode.citronix.web.vm.tree.TreeVM;

public interface TreeVmMapper {
    Tree toEntity(TreeVM treeVM);
    Tree toEntity(TreeEditVM treeEditVM);
    Tree toEntity(TreeResponseVM treeResponseVm);

    TreeVM toTreeVM(Tree tree);
    TreeEditVM toTreeEditVM(Tree tree);
    TreeResponseVM toTreeResponseVM(Tree tree);

}
