package rogers.utility.app.ksu.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "om_message_ksu_log")
public class KsuEntity {

  @Id
  Integer MESSAGE_ID;

  String UPSTREAM_ORDER_ID;
  String OSM_ORDER_ID;
  String CBP;
  String SAM;
  String TIMESTAMP;
  String KSU_NAME;
  String MESSAGE;
  String CORRELATION_ID;
  String SEQUENCE_NUMBER;
  String UNIT_OF_ORDER;
  String UPSTREAM_PRODUCT_ORDER_ID;
  String SERVICE_NAME;
  String ERROR_CODE;
  String ERROR_DESCRIPTION;

  public Integer getMESSAGE_ID() {
    return MESSAGE_ID;
  }

  public void setMESSAGE_ID(Integer MESSAGE_ID) {
    this.MESSAGE_ID = MESSAGE_ID;
  }

  public String getUPSTREAM_ORDER_ID() {
    return UPSTREAM_ORDER_ID;
  }

  public void setUPSTREAM_ORDER_ID(String UPSTREAM_ORDER_ID) {
    this.UPSTREAM_ORDER_ID = UPSTREAM_ORDER_ID;
  }

  public String getOSM_ORDER_ID() {
    return OSM_ORDER_ID;
  }

  public void setOSM_ORDER_ID(String OSM_ORDER_ID) {
    this.OSM_ORDER_ID = OSM_ORDER_ID;
  }

  public String getCBP() {
    return CBP;
  }

  public void setCBP(String CBP) {
    this.CBP = CBP;
  }

  public String getSAM() {
    return SAM;
  }

  public void setSAM(String SAM) {
    this.SAM = SAM;
  }

  public String getTIMESTAMP() {
    return TIMESTAMP;
  }

  public void setTIMESTAMP(String TIMESTAMP) {
    this.TIMESTAMP = TIMESTAMP;
  }

  public String getKSU_NAME() {
    return KSU_NAME;
  }

  public void setKSU_NAME(String KSU_NAME) {
    this.KSU_NAME = KSU_NAME;
  }

  public String getMESSAGE() {
    return MESSAGE;
  }

  public void setMESSAGE(String MESSAGE) {
    this.MESSAGE = MESSAGE;
  }

  public String getCORRELATION_ID() {
    return CORRELATION_ID;
  }

  public void setCORRELATION_ID(String CORRELATION_ID) {
    this.CORRELATION_ID = CORRELATION_ID;
  }

  public String getSEQUENCE_NUMBER() {
    return SEQUENCE_NUMBER;
  }

  public void setSEQUENCE_NUMBER(String SEQUENCE_NUMBER) {
    this.SEQUENCE_NUMBER = SEQUENCE_NUMBER;
  }

  public String getUNIT_OF_ORDER() {
    return UNIT_OF_ORDER;
  }

  public void setUNIT_OF_ORDER(String UNIT_OF_ORDER) {
    this.UNIT_OF_ORDER = UNIT_OF_ORDER;
  }

  public String getUPSTREAM_PRODUCT_ORDER_ID() {
    return UPSTREAM_PRODUCT_ORDER_ID;
  }

  public void setUPSTREAM_PRODUCT_ORDER_ID(String UPSTREAM_PRODUCT_ORDER_ID) {
    this.UPSTREAM_PRODUCT_ORDER_ID = UPSTREAM_PRODUCT_ORDER_ID;
  }

  public String getSERVICE_NAME() {
    return SERVICE_NAME;
  }

  public void setSERVICE_NAME(String SERVICE_NAME) {
    this.SERVICE_NAME = SERVICE_NAME;
  }

  public String getERROR_CODE() {
    return ERROR_CODE;
  }

  public void setERROR_CODE(String ERROR_CODE) {
    this.ERROR_CODE = ERROR_CODE;
  }

  public String getERROR_DESCRIPTION() {
    return ERROR_DESCRIPTION;
  }

  public void setERROR_DESCRIPTION(String ERROR_DESCRIPTION) {
    this.ERROR_DESCRIPTION = ERROR_DESCRIPTION;
  }
}
