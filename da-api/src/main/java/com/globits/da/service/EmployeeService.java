package com.globits.da.service;

import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.excel.ResponObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeService {
    public Page<EmployeeDTO> getPage(int pageSize, int pageIndex);

    public EmployeeDTO saveOrUpdate(UUID id, EmployeeDTO dto);

    public List<EmployeeDTO> getAllEmployee();

    public Page<EmployeeDTO> searchEmployee(EmployeeSearchDTO dto);

    public Boolean deleteEmpployee(UUID id);

    ResponObject<EmployeeDTO> addEmployee(EmployeeDTO dto);

    public ResponObject<EmployeeDTO> addEmployee2(EmployeeDTO dto);

    public ResponObject<EmployeeDTO>update(UUID id, EmployeeDTO dto);
}
