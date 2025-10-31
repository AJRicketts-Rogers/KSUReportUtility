package rogers.utility.app.model;

import java.util.List;

public class OsmBean {

    private String osmId;
    private String status;
    private String createdDateTime;
    private String completedDateTime;
    private String orderType;
    private String orderAction;

    private List<KsuBean> ksuList;

    public String getOsmId() {
        return osmId;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public void setOsmId(String osmId) {
        this.osmId = osmId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(String completedDateTime) {
        this.completedDateTime = completedDateTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<KsuBean> getKsuList() {
        return ksuList;
    }

    public void setKsuList(List<KsuBean> ksuList) {
        this.ksuList = ksuList;
    }

    @Override
    public String toString() {
        return "OsmBean{" +
                "osmId='" + osmId + '\'' +
                ", status='" + status + '\'' +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", completedDateTime='" + completedDateTime + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderAction='" + orderAction + '\'' +
                ", ksuList=" + ksuList +
                '}';
    }
}
