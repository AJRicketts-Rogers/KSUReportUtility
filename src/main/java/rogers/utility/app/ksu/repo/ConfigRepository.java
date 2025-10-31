package rogers.utility.app.ksu.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rogers.utility.app.ksu.entity.ConfigEntity;


@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Integer> {

    @Query(value = "SELECT u FROM ConfigEntity u where name = :name")
    Optional<ConfigEntity> findByName(@Param("name") String name);

}
