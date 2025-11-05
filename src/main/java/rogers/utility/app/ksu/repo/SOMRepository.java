package rogers.utility.app.ksu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rogers.utility.app.ksu.entity.SomEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface SOMRepository extends JpaRepository<SomEntity, Integer> {

	@Query(value = "SELECT ORDER_TYPE FROM om_message_som_log WHERE OSM_ORDER_ID = :osmId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	Optional<String> findOrderTypeByOSM_ORDER_ID(@Param("osmId") String osmId);

}