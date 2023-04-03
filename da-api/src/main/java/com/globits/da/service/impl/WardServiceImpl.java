package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Ward;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.WardSearchDto;
import com.globits.da.repository.WardReponsitory;
import com.globits.da.service.WardService;
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
public class WardServiceImpl extends GenericServiceImpl<Ward, UUID> implements WardService {
    @Autowired
    WardReponsitory reponsitory;

    @Override
    public Page<WardDto> getPage(int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return reponsitory.getListPage(pageable);
    }

    @Override
    public WardDto saveOrUpdate(UUID id, WardDto dto) {
        if (dto != null) {
            Ward entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.getOne(dto.getId());
            }
            if (entity == null) {
                entity = new Ward();
            }
            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
            entity.setPopulation(dto.getPopulation());
            entity.setArea(dto.getArea());

            entity = reponsitory.save(entity);
            return new WardDto(entity);
        }
        return null;
    }

    @Override
    public Boolean deleteKho(UUID id) {
        if (id != null) {
            reponsitory.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public WardDto getCertificate(UUID id) {
        return null;
    }

    @Override
    public Page<WardDto> searchByPage(WardSearchDto dto) {
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

        String sqlCount = "select count(entity.id) from  Ward as entity where (1=1)";
        String sql = "select new com.globits.da.dto.WardDto(entity) from  Ward as entity where (1=1)  ";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text OR entity.population LIKE :text OR entity.area LIKE :text )";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, WardDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return new PageImpl<WardDto>(entities, pageable, count);
    }

    @Override
    public Boolean checkCode(UUID id, String code) {
        return null;
    }

    @Override
    public List<WardDto> getAllWard() {
        return reponsitory.getAllWard();
    }

    @Override
    public Boolean deleteCheckById(UUID id) {
        return null;
    }
}
