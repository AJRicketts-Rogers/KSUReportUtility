package rogers.utility.app.osm.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OM_ORDER_AMENDMENT")
public class OrderAmendmentEntity {
    @Id
    private Integer AMENDMENT_ORDER_ID;

    public Integer getORDER_SEQ_ID() {
        return AMENDMENT_ORDER_ID;
    }

    public void setORDER_SEQ_ID(Integer ORDER_SEQ_ID) {
        this.AMENDMENT_ORDER_ID = ORDER_SEQ_ID;
    }
}
