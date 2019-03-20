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

import java.util.LinkedList;
import java.util.List;

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
        log.info("functionIds:" + functionId.toString());
        List<Integer> list = new LinkedList<>();
        for (int i : functionId) {
            list.add(i);
        }
        log.info("新增权限list:" + list.toString());

        for (int i = 0; i < 11; i++) {
            //查询该用户组原来没有的权限
            if (functionWithGroupDao.findByFunctionIdAndGroupId(i + 1, groupId)==null) {
                log.info("该用户组原来没有的权限:" + i);
                if (list.contains(i + 1)) {
                    //为该用户添加新权限
                    FunctionWithGroup functionWithGroup = new FunctionWithGroup();
                    functionWithGroup.setFunctionId(i + 1);
                    functionWithGroup.setGroupId(groupId);
                    functionWithGroupDao.save(functionWithGroup);
                }
            }
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
