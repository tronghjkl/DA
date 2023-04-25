package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Ward;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.WardSearchDto;
import com.globits.da.repository.WardReponsitory;
import com.globits.da.service.WardService;
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
public class WardServiceImpl extends GenericServiceImpl<Ward, UUID> implements WardService {
    @Autowired
    WardReponsitory reponsitory;

    @Override
    public Page<WardDto> getPage(int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return reponsitory.getListPage(pageable);
    }

    @Override
    public ResponObject<WardDto> add(WardDto dto) {
        if (dto == null) {
            return new ResponObject<>("Input Ward", "BAD REQUEST", 400);
        }
        Ward entity = new Ward();

        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setPopulation(dto.getPopulation());
        entity.setArea(dto.getArea());

        reponsitory.save(entity);
        return new ResponObject<>("Successful", "OK", 200, new WardDto(entity));
    }


    @Override
    public ResponObject<WardDto> update(UUID id, WardDto dto) {
        if (dto == null) {
            return new ResponObject<>("Input Ward", "BAD REQUEST", 400);
        }
        Ward entity = null;
        if (dto.getId() != null) {
            if (dto.getId() != null && !dto.getId().equals(id)) {
                return new ResponObject<>("WardId not exsit", "BAD REQUEST", 400);
            }
            entity = reponsitory.getOne(dto.getId());
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setPopulation(dto.getPopulation());
        entity.setArea(dto.getArea());

        reponsitory.save(entity);
        return new ResponObject<>("Successful", "OK", 200, new WardDto(entity));
    }

    @Override
    public ResponObject<Boolean> deleteKho(UUID id) {
        if (id == null) {
            return new ResponObject<>("Input WardId", "BAD REQUEST", 400, false);
        }
        if (reponsitory.getOne(id) == null) {
            return new ResponObject<>("Not Found Ward Need Delete", "BAD REQUEST", 400, false);
        }
        reponsitory.deleteById(id);
        return new ResponObject<>("Delete Successful", "OK", 200, true);
    }


    @Override
    public ResponObject<Page<WardDto>> searchByPage(WardSearchDto dto) {
        if (dto == null) {
            return new ResponObject<>("Input Keyword", "BAD REQUEST", 400);
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

        return new ResponObject<>("Seaerch Succesful", "OK", 200, new PageImpl<WardDto>(entities, pageable, count));
    }


    @Override
    public ResponObject<List<WardDto>> getAll() {
        List<WardDto> wardDtos = reponsitory.getAllWard();
        if (CollectionUtils.isEmpty(wardDtos)) {
            return new ResponObject<>("Get All Ward Failed", "BAD REQUEST", 400);
        }
        return new ResponObject<>("Get All Ward Successful", "OK", 200, wardDtos);
    }


}
