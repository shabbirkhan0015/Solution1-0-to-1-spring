package com.spring0to1.demo.Controller;

import com.spring0to1.demo.Entity.Department;
import com.spring0to1.demo.Services.DepartmentService;
import com.spring0to1.demo.dto.DepartmentDTO;
import com.spring0to1.demo.dto.EmployeeDTO;
import com.spring0to1.demo.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/departments")
public class departmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> createDepartment( @RequestBody @Valid DepartmentDTO departmentDTO)
    {

        return departmentService.createDepartment(departmentDTO);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments()
    {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }


    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(name = "departmentId") Long id) {
        Optional<DepartmentDTO> departmentDTO = departmentService.getDepartmentId(id);
        return departmentDTO
                .map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: "+id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO)
    {
        DepartmentDTO savedDepartmeent =  departmentService.updateDepartment(id,departmentDTO);
        return new ResponseEntity<>(savedDepartmeent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable Long id) {
        boolean gotDeleted = departmentService.deleteDepartmentById(id);
        if (gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }
}
