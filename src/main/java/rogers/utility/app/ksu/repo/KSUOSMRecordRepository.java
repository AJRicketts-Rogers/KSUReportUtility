package rogers.utility.app.ksu.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rogers.utility.app.ksu.entity.OSMOrderTrackerEntity;


@Repository
public interface KSUOSMRecordRepository extends JpaRepository<OSMOrderTrackerEntity, Integer> {

	 @Query(value = "SELECT u FROM OSMOrderTrackerEntity u WHERE status= :status order by completedDate asc")
	    List<OSMOrderTrackerEntity> findOSMOrderTrackerEntityByStatu(@Param("status") String status,final Pageable pageable);
	 
	 
	 int countByOsmId(int osmId);
	 
	 /*
	  * entity.setStatus("LOADED");
		entity.setTaskName("OSMORDERLOADER");
		entity.setKsuStatus("PENDING");
		*/
}
