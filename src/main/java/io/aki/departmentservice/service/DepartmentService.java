package io.aki.departmentservice.service;

import io.aki.departmentservice.entity.Department;
import io.aki.departmentservice.repository.DepartmentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {

        log.info("Inside saveDeparment Method of DepartmentService");
        return departmentRepository.save(department);
    }

    @CircuitBreaker(name = "findDepartmentById",fallbackMethod = "fallbackFindDepartmentById")
    public Department findDepartmentById(Long departmentId) {

        log.info("Inside findDepartmentById Method of DepartmentService");
//        if (true) {try {
//            throw(new Exception());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }}
        return departmentRepository.findByDepartmentId(departmentId);
    }
    public Department fallbackFindDepartmentById(Long departmentId,Throwable t) {
        log.info("Fallback from findByDepartmentId in Department Service with error: "+t.getMessage());
        log.info("Inside findDepartmentByIdfallback Method of DepartmentService");
        return departmentRepository.findByDepartmentId(departmentId);
    }
}
