package rogers.utility.app.osm.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "om_order_header")
@SecondaryTable(name = "om_order_instance", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ORDER_SEQ_ID"))
public class OsmEntity {

  @Id
  private Integer ORDER_SEQ_ID;

  @Column(name = "ORD_CREATION_DATE")
  private Date ordCreationDate;
  private String ORD_START_DATE;
  private String ORD_COMPLETION_DATE;

  @OneToOne
  @JoinColumn(name = "ORD_STATE_ID")
  private OrderStateEntity ORD_STATE_ID;

  private String REFERENCE_NUMBER;

  @OneToOne
  @JoinColumn(name = "ORDER_SOURCE_ID")
  private OrderSourceEntity ORDER_SOURCE_ID;

  @OneToOne
  @JoinColumn(name = "ORDER_SEQ_ID")
  private OrderAmendmentEntity amendMent;

  @Column(name = "NODE_VALUE_TEXT", table = "om_order_instance")
  private String action;
  @Column(name = "DATA_DICTIONARY_ID", table = "om_order_instance")
  private Integer ddId;


  public Integer getDdId() {
    return ddId;
  }

  public void setDdId(Integer ddId) {
    this.ddId = ddId;
  }

  public Integer getORDER_SEQ_ID() {
    return ORDER_SEQ_ID;
  }

  public void setORDER_SEQ_ID(Integer ORDER_SEQ_ID) {
    this.ORDER_SEQ_ID = ORDER_SEQ_ID;
  }

  public Date getOrdCreationDate() {
    return ordCreationDate;
  }

  public void setOrdCreationDate(Date ordCreationDate) {
    this.ordCreationDate = ordCreationDate;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getORD_START_DATE() {
    return ORD_START_DATE;
  }

  public void setORD_START_DATE(String ORD_START_DATE) {
    this.ORD_START_DATE = ORD_START_DATE;
  }

  public String getORD_COMPLETION_DATE() {
    return ORD_COMPLETION_DATE;
  }

  public void setORD_COMPLETION_DATE(String ORD_COMPLETION_DATE) {
    this.ORD_COMPLETION_DATE = ORD_COMPLETION_DATE;
  }

  public OrderStateEntity getORD_STATE_ID() {
    return ORD_STATE_ID;
  }

  public void setORD_STATE_ID(OrderStateEntity ORD_STATE_ID) {
    this.ORD_STATE_ID = ORD_STATE_ID;
  }

  public String getREFERENCE_NUMBER() {
    return REFERENCE_NUMBER;
  }

  public void setREFERENCE_NUMBER(String REFERENCE_NUMBER) {
    this.REFERENCE_NUMBER = REFERENCE_NUMBER;
  }

  public OrderSourceEntity getORDER_SOURCE_ID() {
    return ORDER_SOURCE_ID;
  }

  public void setORDER_SOURCE_ID(OrderSourceEntity ORDER_SOURCE_ID) {
    this.ORDER_SOURCE_ID = ORDER_SOURCE_ID;
  }

  public OrderAmendmentEntity getAmendMent() {
    return amendMent;
  }

  public void setAmendMent(OrderAmendmentEntity amendMent) {
    this.amendMent = amendMent;
  }
}
