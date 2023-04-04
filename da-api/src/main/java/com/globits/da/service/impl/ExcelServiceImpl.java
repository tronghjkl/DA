package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.domain.Ward;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.repository.DistrictReponsitory;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceReponsitory;
import com.globits.da.repository.WardReponsitory;
import com.globits.da.service.ExcelService;
import com.globits.da.validation.Validate;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelServiceImpl extends GenericServiceImpl<Employee, UUID> implements ExcelService {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    ProvinceReponsitory provinceReponsitory;
    @Autowired
    DistrictReponsitory districtReponsitory;
    @Autowired
    WardReponsitory wardReponsitory;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    Validate validate;

    @Override
    public ResponObject<List<EmployeeDTO>> readExcelData(MultipartFile file) throws IOException {
        List<EmployeeDTO> employeeDtoList = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        StringBuilder error = new StringBuilder();
        // Mở file Excel
        Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()));

        // Lấy sheet đầu tiên
        Sheet sheet = workbook.getSheetAt(0);

        // Lấy header row
        Row headerRow = sheet.getRow(0);

        // Lấy index của các cột trong header row
        int nameIndex = findColumnIndex(headerRow, "name");
        int codeIndex = findColumnIndex(headerRow, "code");
        int emailIndex = findColumnIndex(headerRow, "email");
        int phoneIndex = findColumnIndex(headerRow, "phone");
        int ageIndex = findColumnIndex(headerRow, "age");
        int provinceIdIndex = findColumnIndex(headerRow, "provinceId");
        int districtIdIndex = findColumnIndex(headerRow, "districtId");
        int wardIdIndex = findColumnIndex(headerRow, "wardId");

        // Lặp qua các row từ row thứ 2 trở đi
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                error.append(i).append(1).append(": row is not blank");
                continue;
            }

            // Đọc dữ liệu từ các cột
            String name = getStringCellValue(row.getCell(nameIndex));
            String code = getStringCellValue(row.getCell(codeIndex));
            String email = getStringCellValue(row.getCell(emailIndex));
            String phone = getStringCellValue(row.getCell(phoneIndex));
            int age = getIntCellValue(row.getCell(ageIndex));
            UUID provinceId = getUuidCellValue(row.getCell(provinceIdIndex));
            UUID districtId = getUuidCellValue(row.getCell(districtIdIndex));
            UUID wardId = getUuidCellValue(row.getCell(wardIdIndex));
            // Tạo đối tượng EmployeeDTO từ dữ liệu trong row
            EmployeeDTO employeeDto = new EmployeeDTO();
            employeeDto.setName(name);
            employeeDto.setCode(code);
            employeeDto.setEmail(email);
            employeeDto.setPhone(phone);
            employeeDto.setAge(age);
            employeeDto.setProvinceId(provinceId);
            employeeDto.setDistrictId(districtId);
            employeeDto.setWardId(wardId);

            ResponObject result = validate.validateEmployee(employeeDto);
            Boolean responProvinceDistrictWard = validate.validateProvince(employeeDto);

            if (result.getValid() && responProvinceDistrictWard) {
                employeeDtoList.add(employeeDto);
            } else {
                error.append(i).append(1).append(": ").append(result.getMessager()).append("; ");
            }

        }
        if (employeeDtoList.isEmpty()) {
            return new ResponObject<>("import failed!!!");
        }

        for (EmployeeDTO employeeDTO : employeeDtoList) {
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setCode(employeeDTO.getCode());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhone(employeeDTO.getPhone());
            employee.setAge(employeeDTO.getAge());

            Province province = provinceReponsitory.findById(employeeDTO.getProvinceId()).orElse(null);
            District district = districtReponsitory.findById(employeeDTO.getDistrictId()).orElse(null);
            Ward ward = wardReponsitory.findById(employeeDTO.getWardId()).orElse(null);

            employee.setProvince(province);
            employee.setDistrict(district);
            employee.setWard(ward);

            employees.add(employee);
        }
        repository.saveAll(employees);
        return new ResponObject<>("import successfuly, but failed row: " + error, employeeDtoList);

    }


    private int findColumnIndex(Row row, String columnName) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && columnName.equals(cell.getStringCellValue().trim())) {
                return i;
            }
        }
        return -1;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return null;
    }

    private int getIntCellValue(Cell cell) {
        if (cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
            return 0;
        }
        return (int) cell.getNumericCellValue();
    }

    private UUID getUuidCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String uuidString = cell.getStringCellValue();
        return UUID.fromString(uuidString);
    }

    private boolean existsByCode(String code) {
        try {
            Employee employee = employeeRepository.getByCode(code);
            return employee != null;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponObject<List<EmployeeDTO>> export(List<EmployeeDTO> employees, String fileName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("employees");

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Code");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Phone");
        headerRow.createCell(5).setCellValue("Age");
        headerRow.createCell(6).setCellValue("provinceId");
        headerRow.createCell(7).setCellValue("districtId");
        headerRow.createCell(8).setCellValue("wardId");

        int roww = 1;
        int stt = 1;
        for (EmployeeDTO employeeDTO : employees) {
            Row row = sheet.createRow(roww++);


            row.createCell(0).setCellValue(stt);
            row.createCell(1).setCellValue(employeeDTO.getName());
            row.createCell(2).setCellValue(employeeDTO.getCode());
            row.createCell(3).setCellValue(employeeDTO.getEmail());
            row.createCell(4).setCellValue(employeeDTO.getPhone());
            row.createCell(5).setCellValue(employeeDTO.getAge());
            if (employeeDTO.getProvinceId() != null) {
                row.createCell(6).setCellValue(employeeDTO.getProvinceId().toString());
            }
            if (employeeDTO.getDistrictId() != null) {
                row.createCell(7).setCellValue(employeeDTO.getDistrictId().toString());
            }
            if (employeeDTO.getDistrictId() != null) {
                row.createCell(8).setCellValue(employeeDTO.getWardId().toString());
            }
            stt++;
        }


        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();

        return new ResponObject<>(employees);
    }

}