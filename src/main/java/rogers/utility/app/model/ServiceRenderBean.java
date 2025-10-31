package rogers.utility.app.model;

import java.util.List;

public class ServiceRenderBean {

    private String name;
    private String id;
    private String parentId;
    private String unitOfOrder;
    private String parentName;
    private String parentAction;
    private String action;
    private List<MileStoneRenderBean> milestones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MileStoneRenderBean> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<MileStoneRenderBean> milestones) {
        this.milestones = milestones;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentAction() {
        return parentAction;
    }

    public void setParentAction(String parentAction) {
        this.parentAction = parentAction;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUnitOfOrder() {
		return unitOfOrder;
	}

	public void setUnitOfOrder(String unitOfOrder) {
		this.unitOfOrder = unitOfOrder;
	}

	@Override
    public String toString() {
        return "ServiceRenderBean{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", uintOfOrder='" + unitOfOrder + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentAction='" + parentAction + '\'' +
                ", action='" + action + '\'' +
                ", milestones=" + milestones +
                '}';
    }
}
