package com.manyi.iw.soa.common.util;

public class CreateSeriNo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * 
     * 功能描述:生成每日流水号
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hu-bin:   2014年6月19日      新建
     * </pre>
     *
     * @return
     */
    public static String createSeehouseSerial() {

        //        String date = DateUtil.date2string(new Date(), "yyyyMMdd");
        //        
        //        String jpql = "From SequenceNum sn where sn.sq_name=?";
        //        
        //        Query query = this.getEntityManager().createQuery(jpql);
        //        query.setParameter(1, date);
        //        int num = 0;
        //        List<SequenceNum> list = query.getResultList();
        //        if(list.size()==0){
        //            SequenceNum sequenceNum = new SequenceNum();
        //            sequenceNum.setSq_name(date);
        //            sequenceNum.setSerial(1);
        //            this.getEntityManager().persist(sequenceNum);
        //            this.getEntityManager().flush();
        //            num=1;
        //        }
        //        else{
        //            SequenceNum snum = list.get(0);
        //            num = snum.getSerial();
        //            snum.setSerial(++num);
        //            this.getEntityManager().merge(snum);
        ////            this.getEntityManager().flush();
        //        }
        //        
        //        String xs=String.valueOf(num);
        //        String [] ss = {"0000","000","00","0"};
        //        String str = date+"-"+ss[xs.length()] + xs;
        //        
        //        return str;
        return null;
    }

}
