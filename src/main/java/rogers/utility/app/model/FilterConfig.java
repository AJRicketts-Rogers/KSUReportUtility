package rogers.utility.app.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterConfig", propOrder = {"CFS"})
public class FilterConfig {

    @JacksonXmlProperty(localName ="CFS")
    @JacksonXmlElementWrapper(useWrapping = false)
    protected List<CFSBean> cfsList;

    public List<CFSBean> getCfsList() {
        return cfsList;
    }

    public void setCfsList(List<CFSBean> cfsList) {
        this.cfsList = cfsList;
    }

    @Override
    public String toString() {
        return "FilterConfig{" +
                "cfsList=" + cfsList +
                '}';
    }
}
