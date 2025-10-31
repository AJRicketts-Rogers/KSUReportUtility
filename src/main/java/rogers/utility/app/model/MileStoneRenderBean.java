package rogers.utility.app.model;

public class MileStoneRenderBean {
    private String name;
    private String unitOfOrder;
    private String status;
    private String createdTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    
    
    public String getUnitOfOrder() {
		return unitOfOrder;
	}

	public void setUnitOfOrder(String unitOfOrder) {
		this.unitOfOrder = unitOfOrder;
	}

	@Override
    public String toString() {
        return "MileStoneRenderBean{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}
