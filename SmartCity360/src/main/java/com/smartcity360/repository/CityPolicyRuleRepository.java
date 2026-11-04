package com.smartcity360.repository;

import com.smartcity360.model.CityPolicyRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityPolicyRuleRepository extends JpaRepository<CityPolicyRuleEntity, Long> {
}
