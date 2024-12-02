package com.spring0to1.demo.Services;

import com.spring0to1.demo.Entity.Department;
import com.spring0to1.demo.Entity.EmployeeEntity;
import com.spring0to1.demo.Repository.DepartmentRepository;
import com.spring0to1.demo.dto.DepartmentDTO;
import com.spring0to1.demo.dto.EmployeeDTO;
import com.spring0to1.demo.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper)
    {
        this.departmentRepository=departmentRepository;
        this.modelMapper=modelMapper;
    }

    public void isExistsByDepartmentId(Long departmentId) {
        boolean exists = departmentRepository.existsById(departmentId);
        if(!exists) throw new ResourceNotFoundException("Department not found with id: "+departmentId);
    }


    public ResponseEntity<Department> createDepartment(DepartmentDTO departmentDTO)
    {
            Department tosavedDepartment = modelMapper.map(departmentDTO, Department.class);
            Department savedDepartment= departmentRepository.save(tosavedDepartment);
            return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    public List<DepartmentDTO> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();

        return  departments
                .stream()
                .map(department -> modelMapper.map(department, DepartmentDTO.class))
                .collect(Collectors.toList());

    }

    public DepartmentDTO updateDepartment(Long id,DepartmentDTO departmentDTO) {
       isExistsByDepartmentId(id);
       Department department= modelMapper.map(departmentDTO, Department.class);
       department.setId(id);
       Department savedDepartment = departmentRepository.save(department);
       return  new ModelMapper().map(savedDepartment, DepartmentDTO.class);
    }


    public boolean deleteDepartmentById(Long departmentId) {
        isExistsByDepartmentId(departmentId);
        departmentRepository.deleteById(departmentId);
        return true;
    }
}
