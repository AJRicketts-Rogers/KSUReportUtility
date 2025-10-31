package rogers.utility.app.model;

public class KsuBean {

    private String name;
    private String serviceName;
    private String orderId;
    private String productOrderId;
    private String unitOfOrder;
    private String cbp;
    private String createdTime;
    private ErrorBean error;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(String productOrderId) {
        this.productOrderId = productOrderId;
    }

    public String getCbp() {
        return cbp;
    }

    public void setCbp(String cbp) {
        this.cbp = cbp;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    
    
    public String getUnitOfOrder() {
		return unitOfOrder;
	}

	public void setUnitOfOrder(String unitOfOrder) {
		this.unitOfOrder = unitOfOrder;
	}

	@Override
    public String toString() {
        return "KsuBean{" +
                "name='" + name + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", productOrderId='" + productOrderId + '\'' +
                ", cbp='" + cbp + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", error=" + error +
                '}';
    }
}
