package rogers.utility.app.model;

import java.util.List;

public class ParentServiceRenderBean {

    private String name;
    private String id;
    private String uniOfOrder;
    private String action;
    private List<ServiceRenderBean> childService;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ServiceRenderBean> getChildService() {
        return childService;
    }

    public void setChildService(List<ServiceRenderBean> childService) {
        this.childService = childService;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    

    public String getUniOfOrder() {
		return uniOfOrder;
	}

	public void setUniOfOrder(String uniOfOrder) {
		this.uniOfOrder = uniOfOrder;
	}

	@Override
    public String toString() {
        return "ParentServiceRenderBean{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", childService=" + childService +
                '}';
    }
}
