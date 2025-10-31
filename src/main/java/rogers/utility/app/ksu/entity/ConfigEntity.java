package rogers.utility.app.ksu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OM_KSU_UTILITY_CONFIG")
public class ConfigEntity {
	

	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "OM_KSU_UTILITY_CONFIG_SEQ")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	@Column(name = "LAST_RUN_TIME")
	private Timestamp lastRunTime;
	@Column(name = "INTERVAL")
	private Integer interval;
	@Column(name = "START_TIME")
	private Timestamp startTime;
	@Column(name = "END_TIME")
	private Timestamp endTime;
	
	@Column(name = "OPERATION_LIMIT")
	private Integer operationLimit;
	
	@Column(name = "LAST_EXECUTION_STATUS")	
	private String description;
	
	@Column(name = "LOCKED")
	private String locked;
	
	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public Integer getOperationLimit() {
		return operationLimit;
	}




	public void setOperationLimit(Integer operationLimit) {
		this.operationLimit = operationLimit;
	}


	
	
	 public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public Timestamp getLastRunTime() {
		return lastRunTime;
	}




	public void setLastRunTime(Timestamp lastRunTime) {
		this.lastRunTime = lastRunTime;
	}




	public Integer getInterval() {
		return interval;
	}




	public void setInterval(Integer interval) {
		this.interval = interval;
	}




	public Timestamp getStartTime() {
		return startTime;
	}




	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}




	public Timestamp getEndTime() {
		return endTime;
	}




	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}


	


	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getLocked() {
		return locked;
	}




	public void setLocked(String locked) {
		this.locked = locked;
	}




	@Override
	    public String toString() {
	        return "JOb CONFIG{[ "+locked +
	                " ] name='" + name + '\'' +
	                ", lastRunTime='" + lastRunTime + '\'' +
	                ", interval='" + interval + '\'' +
	                ", startTime='" + startTime + '\'' +
	                ", endTime=" + endTime +
	                ", operationLimit=" + operationLimit +
	                ", description=" + description +
	                '}';
	    }

}
