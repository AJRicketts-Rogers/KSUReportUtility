package rogers.utility.app.ksu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rogers.utility.app.ksu.entity.SomEntity;

import java.util.List;


@Repository
public interface SOMRepository extends JpaRepository<SomEntity, Integer> {

    @Query(value = "SELECT u FROM SomEntity u WHERE u.OSM_ORDER_ID = :osmId")
    List<SomEntity> findSomEntitiesByOSM_ORDER_ID(@Param("osmId") String osmId);
}