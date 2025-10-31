package rogers.utility.app.model;

import java.util.List;

public class OrderItem {
    public String getUnitofOrder() {
		return unitofOrder;
	}
	public void setUnitofOrder(String unitofOrder) {
		this.unitofOrder = unitofOrder;
	}

	private String action;
    private String name;
    private String id;
    private String unitofOrder;
    private List<OrderItem> childList;
    public OrderItem(){

    }
    public OrderItem(String action, String name, String id) {
        this.action = action;
        this.name = name;
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

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

    public List<OrderItem> getChildList() {
        return childList;
    }

    public void setChildList(List<OrderItem> childList) {
        this.childList = childList;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "action='" + action + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", childList=" + childList +
                '}';
    }
}
