package com.ansel.service.impl;

import com.ansel.bean.Employee;
import com.ansel.bean.User;
import com.ansel.bean.UserWithGroup;
import com.ansel.dao.IEmployeeDao;
import com.ansel.dao.IGroupDao;
import com.ansel.dao.IUserDao;
import com.ansel.dao.IUserWithGroupDao;
import com.ansel.service.IEmployeeService;
import com.ansel.util.AddPeopleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "employeeService")
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IGroupDao groupDao;

    @Autowired
    private IUserWithGroupDao userWithGroupDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IEmployeeDao employeeDao;

    @Override
    public boolean save(Employee employee, int condition) {
        try {
            String department = employee.getDepartment();
            String employeeCode = "";
            employeeCode += AddPeopleUtils.departmentPrefix(department);
            while (true) {
                employeeCode += AddPeopleUtils.randomCode();
                if (employeeDao.findByEmployeeCode(employeeCode)==null) {
                    break;
                }
            }
            if (condition==1) {
                User user = new User(employeeCode, "E10ADC3949BA59ABBE56E057F20F883E", false);
                userDao.save(user);
            }
            // 添加职工与组情况
            int groupId = groupDao.findByGroupName(department).getId();
            UserWithGroup userWithGroup = new UserWithGroup();
            userWithGroup.setGroupId(groupId);
            userWithGroup.setUserId(employeeCode);
            userWithGroupDao.save(userWithGroup);
            employee.setEmployeeCode(employeeCode);
            employeeDao.save(employee);
            return true;
        } catch (Exception e) {
            System.err.println("职员 | 用户 | 用户组关系表 信息插入失败！");
            return false;
        }
    }

    @Override
    public boolean delete(String employeeCode) {
        try {
            Employee employee = new Employee();
            employee.setEmployeeCode(employeeCode);
            employeeDao.delete(employee);
            UserWithGroup userWithGroup = userWithGroupDao.findByUserId(employeeCode);
            userWithGroupDao.delete(userWithGroup);
            User user = userDao.findByLoginId(employeeCode);
            if (user!=null) {
                userDao.delete(user);
            }
            return true;
        } catch (Exception e) {
            System.err.println("职员信息删除失败！");
            return false;
        }
    }

    @Override
    public boolean update(Employee employee, String employeeCode, int condition) {
        Employee emp = employeeDao.findByEmployeeCode(employeeCode);
        try {
            if (!emp.getDepartment().equals(employee.getDepartment())) {
                String department = employee.getDepartment();
                employeeDao.delete(emp);
                emp.setDepartment(department);

                //更新工号
                String newEmployeeCode = "";
                newEmployeeCode += AddPeopleUtils.departmentPrefix(department);
                while (true) {
                    newEmployeeCode += AddPeopleUtils.randomCode();
                    if (employeeDao.findByEmployeeCode(newEmployeeCode)==null) {
                        break;
                    }
                }
                emp.setEmployeeCode(newEmployeeCode);
                // 更新职工与组情况
                int groupId = groupDao.findByGroupName(department).getId();
                UserWithGroup userWithGroup = userWithGroupDao.findByUserId(employeeCode);
                userWithGroup.setGroupId(groupId);
                userWithGroupDao.save(userWithGroup);
            }
            emp.setPosition(employee.getPosition());
            employeeDao.save(emp);
            User user = userDao.findByLoginId(employeeCode);
            if (user==null && condition==1) {
                user = new User(employeeCode, "E10ADC3949BA59ABBE56E057F20F883E", false);
                userDao.save(user);
            } else if (user!=null && condition==0) {
                userDao.delete(user);
            }
            return true;
        } catch (Exception e) {
            System.err.println("职员 | 用户 | 用户组关系表 信息更新失败！");
            return false;
        }
    }

    @Override
    public Page<Employee> selectAllEmpByPage(Pageable pageable) {
        return employeeDao.findAll(pageable);
    }

    @Override
    public Employee selectByEmployeeCode(String employeeCode) {
        return employeeDao.findByEmployeeCode(employeeCode);
    }


}
