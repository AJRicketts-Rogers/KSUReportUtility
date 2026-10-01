package rogers.utility.app.osm.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rogers.utility.app.osm.entity.OsmEntity;
import rogers.utility.app.osm.entity.OsmOrderEntity;


@Repository
public interface OSMRepository extends JpaRepository<OsmEntity, Integer> {

    @Query(value = "SELECT u FROM OsmEntity u WHERE u.ordCreationDate between :startDate AND :endDate")
    List<OsmEntity> findOsmEntitiesByByDate(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

    @Query(value = "SELECT oh FROM OsmEntity oh "+
            "LEFT JOIN oh.ORDER_SOURCE_ID os LEFT JOIN oh.ORD_STATE_ID op "+
            "LEFT JOIN oh.amendMent oa "+
            "WHERE oh.ordCreationDate between :startDate AND :endDate "+
            " and os.ORDER_SOURCE_MNEMONIC ='COM_SalesOrderFulfillment'  and op.ID in (1,4,5,7,11) "+
            " and oh.ddId in (select odd.DATA_DICTIONARY_ID  from DataDictionaryEntity odd where odd.DATA_DICTIONARY_ID=oh.ddId and odd.DATA_DICTIONARY_MNEMONIC='OrderType') "+
            "order by oh.ordCreationDate DESC"
    )
    List<OsmEntity> findAllByOrdCreationDateBetween(Date startDate,Date endDate,final Pageable pageable);

    @Query(value = "SELECT oh FROM OsmEntity oh "+
            "LEFT JOIN oh.ORDER_SOURCE_ID os LEFT JOIN oh.ORD_STATE_ID op "+
            "LEFT JOIN oh.amendMent oa "+
            "WHERE oh.ordCreationDate between :startDate AND :endDate "+
            " and os.ORDER_SOURCE_MNEMONIC ='COM_SalesOrderFulfillment'  and op.ID = 7 "+
            " and oh.ddId in (select odd.DATA_DICTIONARY_ID  from DataDictionaryEntity odd where odd.DATA_DICTIONARY_ID=oh.ddId and odd.DATA_DICTIONARY_MNEMONIC='OrderType') "+
            "order by oh.ordCreationDate DESC"
    )
    List<OsmEntity> findAllCompletedByOrdCreationDateBetween(Date startDate,Date endDate,final Pageable pageable);
    
    @Query(value = "SELECT oh FROM OsmOrderEntity oh "+
            "LEFT JOIN oh.ORDER_SOURCE_ID os LEFT JOIN oh.ORD_STATE_ID op "+
            "LEFT JOIN oh.amendMent oa "+
            "WHERE oh.ordCompletionnDate between :startDate AND :endDate "+
            " and os.ORDER_SOURCE_MNEMONIC ='COM_SalesOrderFulfillment'  and op.ID = 7 "+
            "order by oh.ordCreationDate DESC"
    )
    List<OsmOrderEntity> findAllCompletedByDateBetween(Date startDate,Date endDate);

    @Query(value = "SELECT oh FROM OsmEntity oh "+
            "LEFT JOIN oh.ORDER_SOURCE_ID os LEFT JOIN oh.ORD_STATE_ID op "+
            "LEFT JOIN oh.amendMent oa "+
            "WHERE oh.REFERENCE_NUMBER = :id "+
            " and os.ORDER_SOURCE_MNEMONIC ='COM_SalesOrderFulfillment'  and op.ID in (1,4,5,7,11) "+
            " and oh.ddId in (select odd.DATA_DICTIONARY_ID  from DataDictionaryEntity odd where odd.DATA_DICTIONARY_ID=oh.ddId and odd.DATA_DICTIONARY_MNEMONIC='OrderType') "+
            "order by oh.ordCreationDate DESC"
    )
    List<OsmEntity> findOrderNumber(String id,final Pageable pageable);


    @Query(value = "SELECT oh FROM OsmEntity oh "+
            "LEFT JOIN oh.ORDER_SOURCE_ID os LEFT JOIN oh.ORD_STATE_ID op "+
            "LEFT JOIN oh.amendMent oa "+
            "WHERE oh.ORDER_SEQ_ID = :id "+
            " and os.ORDER_SOURCE_MNEMONIC ='COM_SalesOrderFulfillment'  and op.ID in (1,4,5,7,11) "+
            " and oh.ddId in (select odd.DATA_DICTIONARY_ID  from DataDictionaryEntity odd where odd.DATA_DICTIONARY_ID=oh.ddId and odd.DATA_DICTIONARY_MNEMONIC='OrderType') "+
            "order by oh.ordCreationDate DESC"
    )
    List<OsmEntity> findOsmId(Integer id);
}
