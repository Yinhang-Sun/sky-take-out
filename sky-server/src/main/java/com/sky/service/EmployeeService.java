package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * employee login
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * Add new employee
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * Employee Pagination query
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Enable or Disable employee account
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * Query employee based on id
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * Edit employee info
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
