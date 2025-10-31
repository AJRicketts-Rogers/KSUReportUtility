package rogers.utility.app.model;

public class RequestBean {
    private String trackId;
    private String startDate;
    private String endDate;
    private String osmId;
    private String orderNumber;
    private String limit;
    private String orderSearch;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOsmId() {
        return osmId;
    }

    public void setOsmId(String osmId) {
        this.osmId = osmId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getOrderSearch() {
        return orderSearch;
    }

    public void setOrderSearch(String orderSearch) {
        this.orderSearch = orderSearch;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "trackId='" + trackId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", osmId='" + osmId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", limit='" + limit + '\'' +
                '}';
    }
}
