package rogers.utility.app.model;

import java.util.List;

public class ResponseBean {

    private RequestBean input;
    private List<OSMRenderBean> osmList;
    private List<OsmBean> osmRawList;
    private String status;
    private ErrorBean error;


    public RequestBean getInput() {
        return input;
    }

    public void setInput(RequestBean input) {
        this.input = input;
    }

    public List<OSMRenderBean> getOsmList() {
        return osmList;
    }

    public void setOsmList(List<OSMRenderBean> osmList) {
        this.osmList = osmList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public List<OsmBean> getOsmRawList() {
        return osmRawList;
    }

    public void setOSMRawList(List<OsmBean> osmRawList) {
        this.osmRawList = osmRawList;
    }
}
