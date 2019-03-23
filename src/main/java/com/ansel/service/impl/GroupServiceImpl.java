package com.ansel.service.impl;

import com.ansel.bean.Function;
import com.ansel.bean.FunctionWithGroup;
import com.ansel.bean.UserGroup;
import com.ansel.dao.*;
import com.ansel.service.IGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(value = "groupService")
@Transactional(rollbackFor = Exception.class)
public class GroupServiceImpl implements IGroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private IGroupDao groupDao;

    @Autowired
    private IEmployeeDao employeeDao;

    @Autowired
    private IFunctionDao functionDao;

    @Autowired
    private IFunctionWithGroupDao functionWithGroupDao;

    @Autowired
    private IUserWithGroupDao userWithGroupDao;

    /**
     * @return boolean
     * @description 新增一个用户组
     * @params [userGroup]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean save(UserGroup userGroup) {

        try {
            groupDao.save(userGroup);
            return true;
        } catch (Exception e) {
            log.error("用户组表插入失败！" + e.getMessage());
            return false;
        }
    }

    /**
     * @return boolean
     * @description 删除一个用户组
     * @params [id]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean delete(int id) {

        try {
            UserGroup userGroup = groupDao.findById(id);
            groupDao.delete(userGroup);
            employeeDao.updateDepartment("临时组", userGroup.getGroupName());
            return true;
        } catch (Exception e) {
            log.error("用户组表删除 | 职工部门更新 失败！" + e.getMessage());
            return false;
        }
    }

    /**
     * @return org.springframework.data.domain.Page<com.ansel.bean.UserGroup>
     * @description 查询所有用户组
     * @params [pageable]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public Page<UserGroup> selectAllGroup(Pageable pageable) {
        return groupDao.findAll(pageable);
    }

    /**
     * @return com.ansel.bean.UserGroup
     * @description 查询某个用户组
     * @params [id]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public UserGroup findById(int id) {
        return groupDao.findById(id);
    }

    /**
     * @return boolean
     * @description 修改某个用户组的描述
     * @params [id, description]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean update(int id, String description) {

        UserGroup userGroup = groupDao.findById(id);
        userGroup.setDescription(description);
        try {
            groupDao.save(userGroup);
            return true;
        } catch (Exception e) {
            log.error("用户组描述更新失败！" + e.getMessage());
            return false;
        }
    }

    /**
     * @return java.util.List<com.ansel.bean.UserGroup>
     * @description 查询所有用户组
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<UserGroup> findAll() {
        return groupDao.findAll();
    }

    /**
     * @return java.util.List<com.ansel.bean.Function>
     * @description 查询所有权限
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<Function> findAllFunction() {
        return functionDao.findAll();
    }

    /**
     * @return java.util.List<com.ansel.bean.FunctionWithGroup>
     * @description 查询某个组的权限id
     * @params [groupId]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<FunctionWithGroup> findAllFunctionWithGroups(int groupId) {
        return functionWithGroupDao.findByGroupId(groupId);
    }

    /**
     * @return boolean
     * @description 给每个用户组重新分配权限
     * @params [groupId, functionId]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public boolean addFuncGro(int groupId, int[] functionId) {

        log.info("groupId:" + groupId);
        //新的权限id集合
        List<Integer> newFunctionIds = new LinkedList<>();
        for (int i : functionId) {
            newFunctionIds.add(i);
        }
        log.info("新增权限list:" + newFunctionIds.toString());
        //旧的权限id集合
        List<FunctionWithGroup> functionWithGroups = functionWithGroupDao.findByGroupId(groupId);
        List<Integer> oldFunctionIds = new ArrayList<>();
        functionWithGroups.stream().forEach(functionWithGroup -> {
            oldFunctionIds.add(functionWithGroup.getFunctionId());
        });
        log.info("原来权限list:" + oldFunctionIds.toString());
        //求出两个集合的交集
        Set<Integer> functionSet = new HashSet<Integer>();
        functionSet.addAll(oldFunctionIds);
        functionSet.retainAll(newFunctionIds);
        //set转为list
        List<Integer> functionList = new ArrayList<>(functionSet);
        //需要新增权限的差集
        List<Integer> insertFunctionIds = new ArrayList<>();
        for (int i = 0; i < newFunctionIds.size(); i++) {
            if (!functionList.contains(newFunctionIds.get(i))) {
                insertFunctionIds.add(newFunctionIds.get(i));
            }
        }
        //需要删除权限的差集
        List<Integer> removeFunctionIds = new ArrayList<>();
        for (int i = 0; i < oldFunctionIds.size(); i++) {
            if (!functionList.contains(oldFunctionIds.get(i))) {
                removeFunctionIds.add(oldFunctionIds.get(i));
            }
        }
        //移除某些权限
        for (int i = 0; i < removeFunctionIds.size(); i++) {
            Integer functionId1 = removeFunctionIds.get(i).intValue();
            int count = functionWithGroupDao.deleteByFunctionIdAndAndGroupId(functionId1, groupId);
            if (count > 0) {
                log.info("删除成功groupId：" + groupId + "functionId: " + functionId1);
            }
        }
        //新增某些权限
        for (int i = 0; i < insertFunctionIds.size(); i++) {
            //为该用户添加新权限
            FunctionWithGroup functionWithGroup = new FunctionWithGroup();
            functionWithGroup.setFunctionId(insertFunctionIds.get(i));
            functionWithGroup.setGroupId(groupId);
            functionWithGroupDao.save(functionWithGroup);
        }

        return true;
    }

    /**
     * @return java.util.List<com.ansel.bean.FunctionWithGroup>
     * @description 根据登陆id查询它的权限id
     * @params [loginId]
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Override
    public List<FunctionWithGroup> findByLoginId(String loginId) {
        int groupId = userWithGroupDao.findByUserId(loginId).getGroupId();
        return functionWithGroupDao.findByGroupId(groupId);
    }

}
