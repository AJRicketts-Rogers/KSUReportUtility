package rogers.utility.app.ksu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OM_KSU_UTILITY_TRACKER")
public class OSMOrderTrackerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "OM_KSU_UTILITY_TRACKER_SEQ")
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "OSM_ID" , unique = true)
	private Integer osmId;

	@Column(name = "ORDER_NUMBER")
	private String orderNumber;

	@Column(name = "CREATED_DATE")
	private Timestamp createDate;
	@Column(name = "COMPLETED_DATE")
	private Timestamp completedDate;
	@Column(name = "LAST_UPDATED")
	private Timestamp lastUpdatedDate;

	@Column(name = "EXECUTION_STATUS")
	private String status;

	@Column(name = "OPERATION_PAYLOAD")
	private String operationPayload;
	@Column(name = "RESULT_PAYLOAD")
	private String resultPayload;

	@Column(name = "TASK_NAME")
	private String taskName;
	@Column(name = "KSU_STATUS")
	private String ksuStatus;
    @Column(name="ERROR")
    private String errorDesc;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOsmId() {
		return osmId;
	}

	public void setOsmId(Integer osmId) {
		this.osmId = osmId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Timestamp completedDate) {
		this.completedDate = completedDate;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperationPayload() {
		return operationPayload;
	}

	public void setOperationPayload(String operationPayload) {
		this.operationPayload = operationPayload;
	}

	public String getResultPayload() {
		return resultPayload;
	}

	public void setResultPayload(String resultPayload) {
		this.resultPayload = resultPayload;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getKsuStatus() {
		return ksuStatus;
	}

	public void setKsuStatus(String ksuStatus) {
		this.ksuStatus = ksuStatus;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	
}
