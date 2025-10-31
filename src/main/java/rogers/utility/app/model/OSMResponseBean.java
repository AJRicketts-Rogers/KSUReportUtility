package rogers.utility.app.model;

public class OSMResponseBean {
	  private String errorDescription;
	  private OSMRenderBean osmBean;
	  private OsmBean osmRawBean;
	  
	  public OSMRenderBean getOsmBean()
	  {
	    return this.osmBean;
	  }
	  
	  public void setOsmBean(OSMRenderBean osmBean)
	  {
	    this.osmBean = osmBean;
	  }
	  
	  public OsmBean getOsmRawBean()
	  {
	    return this.osmRawBean;
	  }
	  
	  public void setOsmRawBean(OsmBean osmRawBean)
	  {
	    this.osmRawBean = osmRawBean;
	  }
	  
	  public String getErrorDescription()
	  {
	    return this.errorDescription;
	  }
	  
	  public void setErrorDescription(String errorDescription)
	  {
	    this.errorDescription = errorDescription;
	  }
    

}
