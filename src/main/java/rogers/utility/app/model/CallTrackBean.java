package rogers.utility.app.model;

public class CallTrackBean {

    String osmId;
    String orderNumber;
    boolean wscall=false;
    boolean ksucall=false;
    long wscallMilli=0;
    long ksucallMilli=0;

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

    public boolean isWscall() {
        return wscall;
    }

    public void setWscall(boolean wscall) {
        this.wscall = wscall;
    }

    public boolean isKsucall() {
        return ksucall;
    }

    public void setKsucall(boolean ksucall) {
        this.ksucall = ksucall;
    }

    public long getWscallMilli() {
        return wscallMilli;
    }

    public void setWscallMilli(long wscallMilli) {
        this.wscallMilli = wscallMilli;
    }

    public long getKsucallMilli() {
        return ksucallMilli;
    }

    public void setKsucallMilli(long ksucallMilli) {
        this.ksucallMilli = ksucallMilli;
    }
}
