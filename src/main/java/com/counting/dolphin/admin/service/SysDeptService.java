package com.counting.dolphin.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.counting.dolphin.admin.domain.SysDept;
import com.counting.dolphin.admin.domain.bo.Ztree;
import com.counting.dolphin.admin.enums.StatusEnum;
import com.counting.dolphin.admin.mapper.SysDeptMapper;
import com.counting.dolphin.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> {

    /**
     * 查询部门树集合
     *
     * @return List<Ztree>
     */
    public List<Ztree> queryDeptZtreeData() {
        List<SysDept> deptList = this.list(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getStatus, StatusEnum.YES.value().toString()).eq(SysDept::getDelFlag, false).orderByAsc(SysDept::getOrderNum));
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

    public List<SysDept> deptListData() {
        List<SysDept> list = this.list(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDelFlag, false).orderByAsc(SysDept::getOrderNum));
        if (StringUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Integer> parentIds = list.stream().map(sysDept -> sysDept.getParentId()).collect(Collectors.toList());
        List<SysDept> sysDeptList = this.list(Wrappers.lambdaQuery(SysDept.class).in(SysDept::getParentId, parentIds));
        if (!StringUtils.isEmpty(sysDeptList)) {
            Map<Integer, SysDept> sysDeptMap = sysDeptList.stream().collect(Collectors.toMap(SysDept::getDeptId,
                    Function.identity()));
            for (SysDept sysDept : list) {
                SysDept menu = sysDeptMap.get(sysDept.getParentId());
                if (null != menu) {
                    sysDept.setParentName(menu.getDeptName());
                }
            }
        }
        return list;

    }

    public Boolean removeDeptByDeptId(String deptId) {
        List<SysDept> sysDeptList = this.list(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getParentId, Long.valueOf(deptId)).eq(SysDept::getDelFlag, false));
        if (!ObjectUtils.isEmpty(sysDeptList)) {
            throw new BusinessException("存在子部门，无法删除");
        }
        SysDept one = this.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, Long.valueOf(deptId)).eq(SysDept::getDelFlag, false));
        one.setDelFlag(true);
        return this.update(one, Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, Long.valueOf(deptId)));
    }

    public Boolean createOrUpdate(SysDept sysDept) {
        if (ObjectUtils.isEmpty(sysDept.getDeptId())) {
            SysDept dept = this.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptName, sysDept.getDeptName()).eq(SysDept::getDelFlag, false));
            if (null != dept) {
                throw new BusinessException("存在相同的组织名");
            }
            SysDept parentSysDept = this.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, sysDept.getParentId()));
            sysDept.setAncestors(parentSysDept.getAncestors() + "," + sysDept.getParentId());
            sysDept.setCreateTime(LocalDateTime.now());
            sysDept.setStatus(StatusEnum.YES.value().toString());
            sysDept.setDelFlag(false);
            return this.save(sysDept);
        } else {
            SysDept one = this.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptName, sysDept.getDeptName()).eq(SysDept::getDelFlag, false));
            this.updateDeptChildren(sysDept);
            if (null != one && !one.getDeptName().equals(sysDept.getDeptName())) {
                throw new BusinessException("存在相同的组织名");
            }
        }
        return this.update(sysDept, Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, sysDept.getDeptId()));
    }

    /**
     * 更新子组织
     *
     * @param sysDept
     */
    public void updateDeptChildren(SysDept sysDept) {
        if (sysDept.getParentId() == 0) {
            return;
        }
        SysDept parentSysDept = this.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, sysDept.getParentId()));
        if (StringUtils.isEmpty(parentSysDept)) {
            throw new BusinessException("父组织不存在");
        }
        String newAncestors = parentSysDept.getAncestors() + "," + parentSysDept.getDeptId();
        SysDept byId = this.getById(sysDept.getDeptId());
        String oldAncestors = byId.getAncestors();
        sysDept.setAncestors(newAncestors);
        List<SysDept> children = this.list(Wrappers.lambdaQuery(SysDept.class).apply(sysDept.getDeptId() != null, "FIND_IN_SET('" + sysDept.getDeptId() + "',ancestors)"));
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            this.updateBatchById(children);
        }
    }

    public Boolean modifyStatus(String deptId, String status) {
        SysDept one = this.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, Long.valueOf(deptId)));
        one.setStatus(status);
        return this.update(one, Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, one.getDeptId()));
    }
}