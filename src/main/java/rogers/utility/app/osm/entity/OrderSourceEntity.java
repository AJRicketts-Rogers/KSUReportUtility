package rogers.utility.app.osm.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OM_ORDER_SOURCE")
public class OrderSourceEntity {
    @Id
    private Integer ORDER_SOURCE_ID;

    private String ORDER_SOURCE_MNEMONIC;

    public Integer getORDER_SOURCE_ID() {
        return ORDER_SOURCE_ID;
    }

    public void setORDER_SOURCE_ID(Integer ORDER_SOURCE_ID) {
        this.ORDER_SOURCE_ID = ORDER_SOURCE_ID;
    }

    public String getORDER_SOURCE_MNEMONIC() {
        return ORDER_SOURCE_MNEMONIC;
    }

    public void setORDER_SOURCE_MNEMONIC(String ORDER_SOURCE_MNEMONIC) {
        this.ORDER_SOURCE_MNEMONIC = ORDER_SOURCE_MNEMONIC;
    }
}
