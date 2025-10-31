package rogers.utility.app.transformer;

import rogers.utility.app.ksu.entity.KsuEntity;
import rogers.utility.app.model.ErrorBean;
import rogers.utility.app.model.KsuBean;
import rogers.utility.app.model.OsmBean;
import rogers.utility.app.model.ResponseBean;
import rogers.utility.app.osm.entity.OsmEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BeanTransformer {



    public static OsmBean convertEtoBean(OsmEntity entity, List<KsuEntity> ksuEntityList){

        OsmBean  bean=new OsmBean();
        if(entity!=null){
            bean.setCreatedDateTime(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(entity.getOrdCreationDate()));
            bean.setCompletedDateTime(entity.getORD_COMPLETION_DATE());
            bean.setOrderType(entity.getORDER_SOURCE_ID().getORDER_SOURCE_MNEMONIC());
            bean.setStatus(entity.getORD_STATE_ID().getMNEMONIC());
            bean.setOsmId(String.valueOf(entity.getORDER_SEQ_ID()));
            if(ksuEntityList!=null)
            	bean.setKsuList(convertEtoBean(ksuEntityList));
            else {
            	
            }
            bean.setOrderAction(entity.getAction());
        }

        return bean;

    }

    private static String convertOrderType(String order_source_id) {
          switch (order_source_id){
              default: return "data";
          }

    }

    public static List<KsuBean> convertEtoBean( List<KsuEntity> ksuEntityList){
        List<KsuBean> ksuBeans=new ArrayList<KsuBean>();


        if(ksuEntityList!=null && !ksuEntityList.isEmpty()){
            for (KsuEntity entity:ksuEntityList) {
                KsuBean  bean=convertEtoBean(entity);
                ksuBeans.add(bean);
            }

        }
        return ksuBeans;

    }

    public static KsuBean convertEtoBean(KsuEntity entity){

        KsuBean  bean=new KsuBean();
        if(entity!=null){
            bean.setName(entity.getKSU_NAME());
            bean.setCbp(entity.getCBP());
            bean.setOrderId(entity.getUPSTREAM_ORDER_ID());
            bean.setProductOrderId(entity.getUPSTREAM_PRODUCT_ORDER_ID());
            bean.setServiceName(entity.getSERVICE_NAME());
            bean.setCreatedTime(entity.getTIMESTAMP());
            bean.setError(convertEBeantoBean(entity));
            bean.setUnitOfOrder(entity.getUNIT_OF_ORDER());
        }

        return bean;

    }


    public static ErrorBean convertEBeantoBean(KsuEntity entity){

        ErrorBean  bean=new ErrorBean();
        if(entity!=null){
            bean.setCode(entity.getERROR_CODE());
            bean.setDescription(entity.getERROR_DESCRIPTION());
        }
        return bean;

    }
}
