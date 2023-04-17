package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.domain.baseObject.ResponObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeService extends GenericService<Employee, UUID> {
    Page<EmployeeDTO> getPage(int pageSize, int pageIndex);

    ResponObject<List<EmployeeDTO>> getAll();

    ResponObject<Page<EmployeeDTO>> searchByPage(EmployeeSearchDTO dto);

    ResponObject<Boolean> deleteKho(UUID id);

    ResponObject<EmployeeDTO> add(EmployeeDTO dto);

    ResponObject<EmployeeDTO> addEmployeeWithAddress(EmployeeDTO dto);

    ResponObject<EmployeeDTO> update(UUID id, EmployeeDTO dto);

}
