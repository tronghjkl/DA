package com.globits.da.service;

import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.domain.baseObject.ResponObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeService {
    Page<EmployeeDTO> getPage(int pageSize, int pageIndex);

    ResponObject<EmployeeDTO> save(EmployeeDTO dto);

    ResponObject<List<EmployeeDTO>> getAllEmployee();

    ResponObject<Page<EmployeeDTO>> searchEmployee(EmployeeSearchDTO dto);

    ResponObject<Boolean> deleteEmpployee(UUID id);

    ResponObject<EmployeeDTO> addEmployee(EmployeeDTO dto);

    ResponObject<EmployeeDTO> addEmployeeWithAddress(EmployeeDTO dto);

    ResponObject<EmployeeDTO> update(UUID id, EmployeeDTO dto);

}
