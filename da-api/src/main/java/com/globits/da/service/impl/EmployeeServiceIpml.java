package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.constants.ErrorCode;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.domain.Ward;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.repository.DistrictReponsitory;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceReponsitory;
import com.globits.da.repository.WardReponsitory;
import com.globits.da.service.EmployeeService;
import com.globits.da.validation.Validate;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.util.List;
import java.util.UUID;


@Service
public class EmployeeServiceIpml extends GenericServiceImpl<Employee, UUID> implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProvinceReponsitory provinceReponsitory;
    @Autowired
    DistrictReponsitory districtReponsitory;
    @Autowired
    WardReponsitory wardReponsitory;
    @Autowired
    Validate validate;

    // V1
    @Override
    public Page<EmployeeDTO> getPage(int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return employeeRepository.getListPage(pageable);
    }

    @Override
    public ResponObject<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> listEmployee = employeeRepository.getAllEmployee();
        if (CollectionUtils.isEmpty(listEmployee)) {
            return new ResponObject<>("Not Found Employee", "BAD REQUEST", 400);
        }
        return new ResponObject<>("Get List Employee Successful", "OK", 200, listEmployee);
    }

    @Override
    public ResponObject<Page<EmployeeDTO>> searchByPage(EmployeeSearchDTO dto) {
        if (dto.getKeyword() == null) {
            return new ResponObject<>("Input keyword", "BAD REQUEST", 400);
        }
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = "";

        String orderBy = " ORDER BY entity.createDate DESC";

        String sqlCount = "select count(entity.id) from  Employee as entity where (1=1)";

        String sql = "select new com.globits.da.dto.EmployeeDTO(entity) from  Employee as entity where (1=1)  ";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text OR entity.email LIKE :text OR entity.phone LIKE :text OR entity.age LIKE :text )";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, EmployeeDTO.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<EmployeeDTO> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<EmployeeDTO> result = new PageImpl<>(entities, pageable, count);

        return new ResponObject<>("Search Successful", "OK", 200, result);
    }

    @Override
    public ResponObject<Boolean> deleteKho(UUID id) {
        if (id == null) {
            return new ResponObject<>("Input Id", "BAD REQUEST", 400, false);
        }
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
            return new ResponObject<>("Delete Successful", "OK", 200, true);
        } else return new ResponObject<>("Not Found Employee Need Delete", "BAD REQUEST", 400, false);

    }

    // V2
    @Override
    public ResponObject<EmployeeDTO> add(EmployeeDTO dto) {
        ResponObject<EmployeeDTO> result = validate.validateEmployee(dto);
        if (!result.getValid()) {
            return new ResponObject<>("Add Employee Failed", "BAD REQUEST", 400);
        }

        Employee e = new Employee();
        e.setCode(dto.getCode());
        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        e.setPhone(dto.getPhone());
        e.setAge(dto.getAge());
        employeeRepository.save(e);

        return new ResponObject<>("add successful", "OK", 200, new EmployeeDTO(e));

    }

    @Override
    public ResponObject<EmployeeDTO> addEmployeeWithAddress(EmployeeDTO dto) {

        Employee e = new Employee();
        if (validate.existsByCode(dto.getCode())) {
            return new ResponObject<>("code is exist", "BAD REQUEST", 400);
        }
        if (validate.validateProvince(dto)) {
            return new ResponObject<>("province, district, ward not available", "BAD REQUEST", 400);
        }

        e.setCode(dto.getCode());
        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        e.setPhone(dto.getPhone());
        e.setAge(dto.getAge());

        Province province = provinceReponsitory.findById(dto.getProvinceId()).orElse(null);
        District district = districtReponsitory.findById(dto.getDistrictId()).orElse(null);
        Ward ward = wardReponsitory.findById(dto.getWardId()).orElse(null);

        e.setProvince(province);
        e.setWard(ward);
        e.setDistrict(district);

        employeeRepository.save(e);


        return new ResponObject<>("add successful", new EmployeeDTO(e));

    }

    @Override
    public ResponObject<EmployeeDTO> update(UUID id, EmployeeDTO dto) {
        Employee entity = employeeRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponObject<>("Not found employee need update", "BAD REQUEST", 400);
        }
        if (!validate.existsByCode(dto.getCode())) {
            return new ResponObject<>("code is exist", "BAD REQUEST", 400);
        }
        if (validate.validateProvince(dto)) {
            return new ResponObject<>("province, district, commune is not valid", "BAD REQUEST", 400);
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAge(dto.getAge());

        Province province = provinceReponsitory.findById(dto.getProvinceId()).orElse(null);
        District district = districtReponsitory.findById(dto.getDistrictId()).orElse(null);
        Ward ward = wardReponsitory.findById(dto.getWardId()).orElse(null);

        if (province != null && district != null && ward != null) {
            return new ResponObject<>("missing data", "BAD REQUEST", 400);
        }
        entity.setProvince(province);
        entity.setWard(ward);
        entity.setDistrict(district);
        employeeRepository.save(entity);

        return new ResponObject<>(ErrorCode.SUCCESS.getMessage(), "OK", new EmployeeDTO(entity));
    }

}
