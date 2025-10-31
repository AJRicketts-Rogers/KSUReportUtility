package rogers.utility.app.osm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OM_OSPOLICY_STATE")
public class OrderStateEntity {
    @Id
    private Integer ID;

    private String MNEMONIC;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getMNEMONIC() {
        return MNEMONIC;
    }

    public void setMNEMONIC(String MNEMONIC) {
        this.MNEMONIC = MNEMONIC;
    }
}
