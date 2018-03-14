package net.suntrans.building.domin;

import java.util.List;

/**
 * Created by Looney on 2018/3/14.
 * Des:
 */

public class WeekData {

    /**
     * code : 1
     * msg : 查询成功
     * data : [{"title":"周四","eletricity":"87.20"},{"title":"周五","eletricity":"95.20"},{"title":"周六","eletricity":"61.60"},{"title":"周天","eletricity":"64.00"},{"title":"周一","eletricity":"103.20"},{"title":"周二","eletricity":"108.80"},{"title":"周三","eletricity":"46.40"}]
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * title : 周四
         * eletricity : 87.20
         */

        public String title;
        public String eletricity;
    }
}
