package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.domain.baseObject.ResponObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface ExcelService extends GenericService<Employee, UUID> {
    public ResponObject<List<EmployeeDTO>> readExcelData(MultipartFile file) throws IOException;

    ResponObject<List<EmployeeDTO>> export(List<EmployeeDTO> employees, String fileName) throws IOException;
}
