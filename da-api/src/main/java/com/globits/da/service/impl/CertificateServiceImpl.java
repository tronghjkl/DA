package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.repository.CertificateReponsitory;
import com.globits.da.service.CertificateService;
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
public class CertificateServiceImpl extends GenericServiceImpl<Certificate, UUID> implements CertificateService {
    @Autowired
    CertificateReponsitory reponsitory;

    @Override
    public Page<CertificateDto> getPage(int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return reponsitory.getListPage(pageable);
    }

    @Override
    public ResponObject<CertificateDto> saveOrUpdate(UUID id, CertificateDto dto) {
        if (dto != null) {
            Certificate entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.getOne(dto.getId());
            }
            if (entity == null) {
                entity = new Certificate();
            }
            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
//            entity.setStartDate(dto.getStartDate());
//            entity.setEndDate(dto.getEndDate());

            entity = reponsitory.save(entity);
            if (entity != null) {
                return new ResponObject<CertificateDto>("Add Certificate Successfuly", "OK", 200, dto);
            }
        }
        return new ResponObject<CertificateDto>("Add Certificate Failed", "BAD REQUEST", 400);
    }

    @Override
    public ResponObject<Boolean> deleteKho(UUID id) {
        if (id != null) {
            reponsitory.deleteById(id);
            return new ResponObject<>(true);
        }
        return new ResponObject<>(false);
    }

    @Override
    public CertificateDto getCertificate(UUID id) {
        return null;
    }

    @Override
    public ResponObject<Page<CertificateDto>> searchByPage(CertificateSearchDto dto) {
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

        String sqlCount = "select count(entity.id) from  Certificate as entity where (1=1)";
        String sql = "select new com.globits.da.dto.CertificateDto(entity) from  Certificate as entity where (1=1)  ";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text OR entity.startDate LIKE :text OR entity.endDate LIKE :text)";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, CertificateDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<CertificateDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<CertificateDto> result = new PageImpl<CertificateDto>(entities, pageable, count);
        return new ResponObject<>("Get Page Successfuly", "OK", 200, result);
    }

    @Override
    public Boolean checkCode(UUID id, String code) {
        return null;
    }

    @Override
    public ResponObject<List<CertificateDto>> getAllCertificate() {
        List<CertificateDto> listCertificate = reponsitory.getAllCertificate();
        return new ResponObject<List<CertificateDto>>(listCertificate);
    }

    @Override
    public Boolean deleteCheckById(UUID id) {
        return null;
    }
}
