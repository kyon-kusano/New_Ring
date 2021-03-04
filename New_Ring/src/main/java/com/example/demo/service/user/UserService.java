package com.example.demo.service.user;

import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.dto.PasswordRequest;
import com.example.demo.model.entity.Details;
import com.example.demo.model.entity.Employee;
import com.example.demo.model.entity.ProgrammingLanguage;
import com.example.demo.repository.*;
import com.example.demo.service.base.BaseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends BaseService {
    public UserService(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AddressRepository addressRepository, DetailsRepository detailsRepository, ProgrammingLanguageRepository programmingLanguageRepository, EmployeeRepositoryCustomImpl employeeRepositoryCustomImpl) {
        super(passwordEncoder, employeeRepository, departmentRepository, addressRepository, detailsRepository, programmingLanguageRepository, employeeRepositoryCustomImpl);
    }

    /** id に紐づく従業員情報の取得 **/

    /**
     * ログインユーザの編集処理
     **/
    public void myUpdate(EmployeeUpdateRequest employeeUpdateRequest) {

        employeeRepository.save(setUpdate(employeeUpdateRequest));

    }

    /**
     * パスワード変更
     **/
    public void updatePassword(PasswordRequest passwordRequest) {

        Employee loggedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = loggedEmployee.getId();
        Employee employee = findById(id);
        employee.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        employee.setUpdated_at(new Date());
        employeeRepository.save(employee);
    }

    public void updateDetails(Long id, String image, String comment) {
        Employee employee = findById(id);
        Details details = findByDetails(id);
        details.setImage(image);
        details.setComment(comment);
        employee.setDetails(details);
        employeeRepository.save(employee);
    }

    public void updateProgrammingLanguage(Long id, String[] programmingLanguageNames) {
        ProgrammingLanguage programmingLanguage = findByProgrammingLanguageName("Ruby");
        Employee employee = findById(id);
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        programmingLanguage.setEmployees(list);
        programmingLanguageRepository.save(programmingLanguage);
    }

    public Employee setUpdate(EmployeeUpdateRequest employeeUpdateRequest) {

        Employee employee = findById(employeeUpdateRequest.getId());

        employee.setUsername(employeeUpdateRequest.getUsername());
        employee.setEmail(employeeUpdateRequest.getEmail());
        employee.setBirthday(employeeUpdateRequest.getBirthday());
        employee.setSex(employeeUpdateRequest.getSex());
        employee.setAddress(employeeUpdateRequest.getAddress());
        employee.setDepartment(findDepartment(employeeUpdateRequest.getDepartment()));
        employee.setTelephone_Number(employeeUpdateRequest.getTelephone_Number());
        employee.setJoin_Date(employeeUpdateRequest.getJoin_Date());

        return employee;
    }
}
