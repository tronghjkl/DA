package com.globits.da.repository;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Query("select count(entity.id) from Employee entity where entity.code =?1 and(entity.id <> ?2 or ?2 is null)")
    Long checkCode(String code, UUID id);

    @Query("select new com.globits.da.dto.EmployeeDTO(em) from Employee em")
    Page<EmployeeDTO> getListPage(Pageable pageable);

    @Query("select new com.globits.da.dto.EmployeeDTO(em) from Employee em")
    List<EmployeeDTO> getAllEmployee();

    Employee findOneByCode(String code);

//    @Query("select e from Employee e where e.code =?1")
//    Employee getByCode(String code);

    @Query("select e FROM Employee e WHERE e.code = ?1")
    Employee getByCode(String code);
}
