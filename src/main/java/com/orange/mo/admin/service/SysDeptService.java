package com.orange.mo.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mo.admin.domain.SysDept;
import com.orange.mo.admin.domain.bo.Ztree;
import com.orange.mo.admin.enums.StatusEnum;
import com.orange.mo.admin.mapper.SysDeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> {

    /**
     * 查询部门树集合
     *
     * @return List<Ztree>
     */
    public List<Ztree> queryDeptZtreeData() {
        List<SysDept> deptList = this.list(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getStatus, StatusEnum.NOT.value().toString()).eq(SysDept::getDelFlag, StatusEnum.NOT.value().toString()));
        if (ObjectUtils.isEmpty(deptList)) {
            return null;
        }
        return initZtree(deptList);
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList) {
        return getChildPerms(deptList, 0);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<Ztree> getChildPerms(List<SysDept> list, int parentId) {
        List<Ztree> returnList = new ArrayList<Ztree>();
        for (Iterator<SysDept> iterator = list.iterator(); iterator.hasNext(); ) {
            SysDept t = (SysDept) iterator.next();
            Ztree z = this.convertToZtree(t);
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, z);
                returnList.add(z);
            }
        }
        return returnList;
    }

    private static Ztree convertToZtree(SysDept t) {
        Ztree ztree = new Ztree();
        ztree.setText(t.getDeptName());
        ztree.setId(t.getDeptId());
        ztree.setParentId(t.getParentId());
        return ztree;
    }

    private static List<Ztree> convertToZtrees(List<SysDept> sysDeptList) {
        List<Ztree> ztreeList = new ArrayList<>();
        for (SysDept sysDept : sysDeptList) {
            ztreeList.add(convertToZtree(sysDept));
        }
        return ztreeList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysDept> list, Ztree t) {
        // 得到子节点列表
        List<Ztree> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Ztree tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, Ztree t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 得到子节点列表
     */
    private List<Ztree> getChildList(List<SysDept> list, Ztree t) {
        List<Ztree> tlist = new ArrayList<Ztree>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = (SysDept) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                Ztree z = this.convertToZtree(n);
                tlist.add(z);
            }
        }
        return tlist;
    }
}