package rogers.utility.app.ksu.repo;

import rogers.utility.app.ksu.entity.KsuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KSURepository extends JpaRepository<KsuEntity, Integer> {

    @Query(value = "SELECT u FROM KsuEntity u WHERE u.OSM_ORDER_ID = :osmId")
    List<KsuEntity> findKsuEntitiesByOSM_ORDER_ID(@Param("osmId") String osmId);
}
