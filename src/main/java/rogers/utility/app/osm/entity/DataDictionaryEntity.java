package rogers.utility.app.osm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OM_ORDER_DATA_DICTIONARY")
public class DataDictionaryEntity {
    @Id
    private Integer DATA_DICTIONARY_ID;

    private String DATA_DICTIONARY_MNEMONIC;

    public Integer getDATA_DICTIONARY_ID() {
        return DATA_DICTIONARY_ID;
    }

    public void setDATA_DICTIONARY_ID(Integer DATA_DICTIONARY_ID) {
        this.DATA_DICTIONARY_ID = DATA_DICTIONARY_ID;
    }

    public String getDATA_DICTIONARY_MNEMONIC() {
        return DATA_DICTIONARY_MNEMONIC;
    }

    public void setDATA_DICTIONARY_MNEMONIC(String DATA_DICTIONARY_MNEMONIC) {
        this.DATA_DICTIONARY_MNEMONIC = DATA_DICTIONARY_MNEMONIC;
    }
}
