package com.smartcity360.repository;

import com.smartcity360.model.ZoneNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ZoneNodeRepository extends JpaRepository<ZoneNodeEntity, Long> {
    List<ZoneNodeEntity> findByParentId(Long parentId);
}
