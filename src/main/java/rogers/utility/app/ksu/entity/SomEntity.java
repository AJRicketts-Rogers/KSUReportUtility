package rogers.utility.app.ksu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "om_message_som_log")

public class SomEntity {

	@Id
	@Column(name = "MESSAGE_ID")
	Integer MESSAGE_ID;

	@Column(name = "OSM_ORDER_ID")
	private String OSM_ORDER_ID;
	
	@Column (name = "ORDER_TYPE")
	private String ORDER_TYPE;
	
	public Integer getMESSAGE_ID() {
		return MESSAGE_ID;
	}

	public void setMESSAGE_ID(Integer MESSAGE_ID) {
	  this.MESSAGE_ID = MESSAGE_ID;
	}

	public String getORDER_TYPE() {
		return ORDER_TYPE;
	}

	public void setORDER_TYPE(String ORDER_TYPE) {
		this.ORDER_TYPE = ORDER_TYPE;
    }
}