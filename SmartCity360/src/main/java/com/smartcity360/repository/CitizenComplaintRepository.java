package com.smartcity360.repository;

import com.smartcity360.model.CitizenComplaintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitizenComplaintRepository extends JpaRepository<CitizenComplaintEntity, Long> {
    List<CitizenComplaintEntity> findAllByOrderByIdDesc();
}
