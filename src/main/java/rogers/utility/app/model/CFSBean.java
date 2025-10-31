package rogers.utility.app.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

public class CFSBean {
    @JacksonXmlProperty(localName = "SERVICE_NAME",isAttribute = true)
    protected String ServiceName;
    @JacksonXmlProperty(localName = "Action",isAttribute = true)
    protected String Action;
    @JacksonXmlProperty(localName = "ROOT_SERVICE_NAME",isAttribute = true)
    protected String ParentServiceName;
    @JacksonXmlProperty(localName = "RootParentAction",isAttribute = true)
    protected String ParentAction;

    @JacksonXmlProperty(localName ="KSU")
    @JacksonXmlElementWrapper(useWrapping = false)
    protected List<String> ksu;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getParentServiceName() {
        return ParentServiceName;
    }

    public void setParentServiceName(String parentServiceName) {
        ParentServiceName = parentServiceName;
    }

    public String getParentAction() {
        return ParentAction;
    }

    public void setParentAction(String parentAction) {
        ParentAction = parentAction;
    }

    public List<String> getKsu() {
        return ksu;
    }

    public void setKsu(List<String> ksu) {
        this.ksu = ksu;
    }

    @Override
    public String toString() {
        return "CFSBean{" +
                "ServiceName='" + ServiceName + '\'' +
                ", Action='" + Action + '\'' +
                ", ParentServiceName='" + ParentServiceName + '\'' +
                ", ParentAction='" + ParentAction + '\'' +
                ", ksu=" + ksu +
                '}';
    }
}
