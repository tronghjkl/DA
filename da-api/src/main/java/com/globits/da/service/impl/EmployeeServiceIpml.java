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
    public EmployeeDTO saveOrUpdate(UUID id, EmployeeDTO dto) {
        if (dto != null) {
            Employee entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = employeeRepository.getOne(dto.getId());
            }
            if (entity == null) {
                entity = new Employee();
            }
            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPhone(dto.getPhone());
            entity.setAge(dto.getAge());
            entity = employeeRepository.save(entity);
            if (entity != null) {
                return new EmployeeDTO(entity);
            }
        }
        return null;
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeDTO> listEmployee = employeeRepository.getAllEmployee();
        return listEmployee;
    }

    @Override
    public Page<EmployeeDTO> searchEmployee(EmployeeSearchDTO dto) {
        if (dto == null) {
            return null;
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
        Page<EmployeeDTO> result = new PageImpl<EmployeeDTO>(entities, pageable, count);

        return result;
    }

    @Override
    public Boolean deleteEmpployee(UUID id) {
        if (id != null) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // V2
    @Override
    public ResponObject<EmployeeDTO> addEmployee(EmployeeDTO dto) {
        ResponObject<EmployeeDTO> result = validate.validateEmployee(dto);
        if (result.getValid()) {

            Employee e = new Employee();
//            e.setId(dto.getId());
            e.setCode(dto.getCode());
            e.setName(dto.getName());
            e.setEmail(dto.getEmail());
            e.setPhone(dto.getPhone());
            e.setAge(dto.getAge());
            employeeRepository.save(e);


            return new ResponObject<>("add successful", new EmployeeDTO(e));


        } else {
            return result;
        }
    }

    @Override
    public ResponObject<EmployeeDTO> addEmployee2(EmployeeDTO dto) {

        Employee e = new Employee();
        if (!validate.existsByCode(dto.getCode())) {
            if (validateEmployee(dto)) {
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
            } else {
                return new ResponObject<EmployeeDTO>("province, district, ward not available");
            }


        } else {
            return new ResponObject<>("code is exist", "BAD REQUEST", 400);
        }

        return new ResponObject<>("add successful", new EmployeeDTO(e));

    }

    public Boolean validateEmployee(EmployeeDTO dto) {

        Province province = provinceReponsitory.findById(dto.getProvinceId()).orElse(null);

        if (province != null) {
            List<District> districts = province.getDistricts();
            for (District district : districts) {
                if (dto.getDistrictId().equals(district.getId())) {
                    List<Ward> wards = district.getWards();
                    for (Ward ward : wards) {
                        if (dto.getWardId().equals(ward.getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ResponObject<EmployeeDTO> update(UUID id, EmployeeDTO dto) {
        Employee entity = employeeRepository.findById(id).orElse(null);
        if (entity != null) {
            if (!validate.existsByCode(dto.getCode())) {
                if (validateEmployee(dto)) {
                    entity.setCode(dto.getCode());
                    entity.setName(dto.getName());
                    entity.setEmail(dto.getEmail());
                    entity.setPhone(dto.getPhone());
                    entity.setAge(dto.getAge());

                    Province province = provinceReponsitory.findById(dto.getProvinceId()).orElse(null);
                    District district = districtReponsitory.findById(dto.getDistrictId()).orElse(null);
                    Ward ward = wardReponsitory.findById(dto.getWardId()).orElse(null);

                    if (province != null && district != null && ward != null) {
                        entity.setProvince(province);
                        entity.setWard(ward);
                        entity.setDistrict(district);
                    } else {
                        return new ResponObject<>("missing data", "BAD REQUEST", 400);
                    }


                    employeeRepository.save(entity);

                } else {
                    return new ResponObject<>("province, district, commune is not valid", "BAD REQUEST", 400);
                }

            } else {
                return new ResponObject<>("code is exist", "BAD REQUEST", 400);
            }

            return new ResponObject<>(ErrorCode.SUCCESS.getMessage(), new EmployeeDTO(entity));
        }
        return null;
    }


//    private ResponObject validate(EmployeeDTO dto) {
//
//        if (dto.getCode().contains(" ")) {
//            return new ResponObject(false, "the code does not contain spaces");
//        }
//        if (dto.getCode().length() < 6 || dto.getCode().length() > 10) {
//            return new ResponObject(false, "code length from 6 to 10 characters");
//        }
//        if (existsByCode(dto.getCode())) {
//            return new ResponObject(false, ": code already exist");
//        }
//        if (dto.getName() == null) {
//            return new ResponObject(false, " name is required");
//        }
//        if (dto.getEmail() == null && !Pattern.matches(EMAIL_PATTERN, dto.getEmail())) {
//            return new ResponObject(false, "email is required");
//        }
//
//        if (dto.getPhone() == null) {
//            return new ResponObject(false, "Input phone");
//        }
//        if (dto.getPhone().length() > 11) {
//            return new ResponObject(false, "phone contains max 11 numbers");
//        }
//        if (dto.getPhone().length() < 10) {
//            return new ResponObject(false, "phone contains min 10 numbers");
//        }
//
//        if (dto.getAge() < 0) {
//            return new ResponObject(false, "age cannot be negative");
//        }
//
//        return new ResponObject(true, "ok");
//
//    }
//
//    private boolean existsByCode(String code) {
//        try {
//            Employee employee = employeeRepository.getByCode(code);
//            return employee != null;
//        } catch (Exception e) {
//            return false;
//        }
//    }


}
