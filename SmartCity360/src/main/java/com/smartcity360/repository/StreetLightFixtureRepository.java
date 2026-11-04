package com.smartcity360.repository;

import com.smartcity360.model.StreetLightFixtureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetLightFixtureRepository extends JpaRepository<StreetLightFixtureEntity, Long> {
}
