package rogers.utility.app.osm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OM_ORDER_INSTANCE")
public class OrderInstanceEntity {
    @Id
    private Integer ORDER_SEQ_ID;
    @Column(name = "NODE_VALUE_TEXT")
    private String action;

    private Integer DATA_DICTIONARY_ID;


    public Integer getORDER_SEQ_ID() {
        return ORDER_SEQ_ID;
    }

    public void setORDER_SEQ_ID(Integer ORDER_SEQ_ID) {
        this.ORDER_SEQ_ID = ORDER_SEQ_ID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getDATA_DICTIONARY_ID() {
        return DATA_DICTIONARY_ID;
    }

    public void setDATA_DICTIONARY_ID(Integer DATA_DICTIONARY_ID) {
        this.DATA_DICTIONARY_ID = DATA_DICTIONARY_ID;
    }
}
