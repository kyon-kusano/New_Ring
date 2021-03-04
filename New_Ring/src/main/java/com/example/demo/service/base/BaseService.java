package com.example.demo.service.base;

import com.example.demo.bean.selectBean;
import com.example.demo.model.entity.*;
import com.example.demo.repository.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BaseService extends selectBean implements UserDetailsService {

    protected final PasswordEncoder passwordEncoder;

    protected final EmployeeRepository employeeRepository;
    protected final DepartmentRepository departmentRepository;
    protected final AddressRepository addressRepository;
    protected final DetailsRepository detailsRepository;
    protected final ProgrammingLanguageRepository programmingLanguageRepository;
    protected final EmployeeRepositoryCustomImpl employeeRepositoryCustomImpl;

    public BaseService(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AddressRepository addressRepository, DetailsRepository detailsRepository, ProgrammingLanguageRepository programmingLanguageRepository, EmployeeRepositoryCustomImpl employeeRepositoryCustomImpl) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
        this.detailsRepository = detailsRepository;
        this.programmingLanguageRepository = programmingLanguageRepository;
        this.employeeRepositoryCustomImpl = employeeRepositoryCustomImpl;
    }

    /**
     * emailでログイン処理
     **/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || "".equals(email)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        Employee user = employeeRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return user;
    }

    /**
     * 初回起動時、テストユーザ（admin）追加
     **/
    @Transactional
    public void registerAdmin(String username, String password, String email, Date birthday, String sex,
                              Department department, List<ProgrammingLanguage> programming_Language, String telephone_Number,
                              Address address, Details details, Date join_Date, Date updated_at, Date deleted_at) {
        Employee employee = new Employee(username, passwordEncoder.encode(password), email, birthday, sex, department,
                programming_Language, telephone_Number, address, details, join_Date, updated_at, deleted_at);
        employee.setAdmin(true);
        employeeRepository.save(employee);
    }

    @Transactional
    public void registerAdmin2(String username, String password, String email, Date birthday, String sex,
                               Department department, List<ProgrammingLanguage> programming_Language, String telephone_Number,
                               Address address, Details details, Date join_Date, Date updated_at, Date deleted_at) {
        Employee employee = new Employee(username, passwordEncoder.encode(password), email, birthday, sex, department,
                programming_Language, telephone_Number, address, details, join_Date, updated_at, deleted_at);
        employee.setAdmin(true);
        employeeRepository.save(employee);
    }

    public Address createAddress(String post_Number, String address1, String address2) {
        Address address;
        address = new Address(post_Number, address1, address2);
        return address;
    }

    public Details createDetails(String image, String comment) {
        return new Details(image, comment);
    }

    public void createDepartment(String name) {
        Department department = new Department(name);
        departmentRepository.save(department);
    }

    public void createProgrammingLanguage(String name) {
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage(name);
        programmingLanguageRepository.save(programmingLanguage);
    }

    /**
     * 従業員情報全権取得 起動時テストユーザ登録で使用
     **/
    public List<Employee> findAllList() {
        return employeeRepository.findAll();
    }

    /**
     * 部署テーブル全権取得 起動時部署名登録
     **/
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department findDepartment(int id) {
        return departmentRepository.findById(id);
    }

    public List<ProgrammingLanguage> findAllProgrammingLanguage() {
        return programmingLanguageRepository.findAll();
    }


    public List<ProgrammingLanguage> findProgrammingLanguage(String name1, String name2, String name3) {
        List<ProgrammingLanguage> list = new ArrayList<>();
        list.add(programmingLanguageRepository.findByName(name1));
        list.add(programmingLanguageRepository.findByName(name2));
        list.add(programmingLanguageRepository.findByName(name3));
        return list;

    }

    /**
     * id に紐づく従業員情報の取得
     **/
    public Employee findById(Long id) {
        Employee employee;
        employee = employeeRepository.findById(id).get();
        return employee;
    }

    public Employee findByEmail(String Email) {
        return employeeRepository.findByEmail(Email);
    }

    public Details findByDetails(Long id) {
        return detailsRepository.findById(id).get();
    }

    public ProgrammingLanguage findByProgrammingLanguageName(String name) {
        return programmingLanguageRepository.findByName(name);
    }

}
