package com.spring0to1.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring0to1.demo.annotations.DepartmentEmailValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO  implements Serializable {
    private Long id;
    private String title;
    @JsonProperty("isActive")
    private boolean isActive;

    @DepartmentEmailValidation
    private String departmentEmail;

}
