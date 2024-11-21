package com.youcode.citronix.repository;

import com.youcode.citronix.domain.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {

    @Query(value = "SELECT " +
                "CASE " +
                "   WHEN COUNT(hd) > 0 THEN true " +
                "   ELSE false " +
                "END " +
                "FROM harvest_detail hd " +
                "JOIN harvest h ON hd.harvest_id = h.id " +
                "JOIN tree t ON hd.tree_id = t.id " +
                "WHERE t.field_id = :fieldId " +
                "   AND h.season = :harvestSeason " +
                "   AND EXTRACT(YEAR FROM h.date) = :harvestYear",
            nativeQuery = true)
    boolean existsByFieldIdAndHarvestSeasonAndHarvestYear(UUID fieldId, String harvestSeason, int harvestYear);
}