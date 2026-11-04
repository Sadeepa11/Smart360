package com.smartcity360.repository;

import com.smartcity360.model.EnergyTelemetryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyTelemetryRepository extends JpaRepository<EnergyTelemetryEntity, Long> {
}
