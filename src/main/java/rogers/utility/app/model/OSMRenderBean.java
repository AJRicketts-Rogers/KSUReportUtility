package rogers.utility.app.model;

import java.util.List;

public class OSMRenderBean {

    private String orderNumber;
    private String osmOrderId;
    private String createdDate;
    private String status;
    private String compledDate;
    private String orderAction;
    private String cbp;
    private String ksuStatus;
    private OsmBean raw;
    private String errorDescription;
    public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	private List<ParentServiceRenderBean> serviceList;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOsmOrderId() {
        return osmOrderId;
    }

    public void setOsmOrderId(String osmOrderId) {
        this.osmOrderId = osmOrderId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompledDate() {
        return compledDate;
    }

    public void setCompledDate(String compledDate) {
        this.compledDate = compledDate;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getCbp() {
        return cbp;
    }

    public void setCbp(String cbp) {
        this.cbp = cbp;
    }

    public List<ParentServiceRenderBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ParentServiceRenderBean> serviceList) {
        this.serviceList = serviceList;
    }

    public String getKsuStatus() {
        return ksuStatus;
    }

    public void setKsuStatus(String ksuStatus) {
        this.ksuStatus = ksuStatus;
    }

    public OsmBean getRaw() {
        return raw;
    }

    public void setRaw(OsmBean raw) {
        this.raw = raw;
    }
}
