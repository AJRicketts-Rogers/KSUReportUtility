package rogers.utility.app.ksu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rogers.utility.app.ksu.entity.SomEntity;

import java.util.List;
//import java.util.Optional;


@Repository
public interface SOMRepository extends JpaRepository<SomEntity, Integer> {
	


	@Query(value = 
	    "SELECT OSM_ORDER_ID, ORDER_TYPE " +
	    "FROM ( " +
	    "    SELECT OSM_ORDER_ID, ORDER_TYPE, " +
	    "           ROW_NUMBER() OVER ( " +
	    "               PARTITION BY OSM_ORDER_ID " +
	    "               ORDER BY CASE WHEN ORDER_TYPE IS NOT NULL THEN 0 ELSE 1 END " +
	    "           ) AS rn " +
	    "    FROM om_message_som_log " +
	    "    WHERE OSM_ORDER_ID IN (:osmIds) " +
	    ") " +
	    "WHERE rn = 1",
	    nativeQuery = true)
	
	List<Object[]> findOrderTypesByOSM_ORDER_IDs(@Param("osmIds") List<String> osmIds);

}