package net.suntrans.building.domin;

import java.util.List;

/**
 * Created by Looney on 2018/3/15.
 */

public class EnergyHisData {


    /**
     * code : 1
     * msg : 查询成功
     * data : {"today":[{"UsedValue":"0.00","updated_at":"2018-03-15 00:11:43"},{"UsedValue":"3.20","updated_at":"2018-03-15 01:11:42"},{"UsedValue":"2.40","updated_at":"2018-03-15 02:11:42"},{"UsedValue":"3.20","updated_at":"2018-03-15 03:11:43"},{"UsedValue":"2.40","updated_at":"2018-03-15 04:11:42"},{"UsedValue":"3.20","updated_at":"2018-03-15 05:11:42"},{"UsedValue":"2.40","updated_at":"2018-03-15 06:11:43"},{"UsedValue":"3.20","updated_at":"2018-03-15 07:11:43"}],"yesterday":[{"UsedValue":"0.00","updated_at":"2018-03-14 00:11:39"},{"UsedValue":"1.60","updated_at":"2018-03-14 01:11:39"},{"UsedValue":"2.40","updated_at":"2018-03-14 02:11:39"},{"UsedValue":"2.40","updated_at":"2018-03-14 03:11:40"},{"UsedValue":"1.60","updated_at":"2018-03-14 04:11:40"},{"UsedValue":"2.40","updated_at":"2018-03-14 05:11:40"},{"UsedValue":"2.40","updated_at":"2018-03-14 06:11:40"},{"UsedValue":"2.40","updated_at":"2018-03-14 07:11:40"},{"UsedValue":"2.40","updated_at":"2018-03-14 08:11:40"},{"UsedValue":"5.60","updated_at":"2018-03-14 09:11:40"},{"UsedValue":"5.60","updated_at":"2018-03-14 10:11:41"},{"UsedValue":"6.40","updated_at":"2018-03-14 11:11:40"},{"UsedValue":"6.40","updated_at":"2018-03-14 12:11:41"},{"UsedValue":"4.80","updated_at":"2018-03-14 13:11:41"},{"UsedValue":"6.40","updated_at":"2018-03-14 14:11:41"},{"UsedValue":"6.40","updated_at":"2018-03-14 15:11:41"},{"UsedValue":"6.40","updated_at":"2018-03-14 16:11:41"},{"UsedValue":"6.40","updated_at":"2018-03-14 17:11:42"},{"UsedValue":"4.80","updated_at":"2018-03-14 18:11:41"},{"UsedValue":"4.80","updated_at":"2018-03-14 19:11:42"},{"UsedValue":"4.80","updated_at":"2018-03-14 20:11:41"},{"UsedValue":"4.00","updated_at":"2018-03-14 21:11:42"},{"UsedValue":"4.80","updated_at":"2018-03-14 22:11:42"},{"UsedValue":"4.00","updated_at":"2018-03-14 23:11:42"},{"UsedValue":"4.00","updated_at":"2018-03-15 00:11:43"}],"temp":[{"Temp":"29.60","created_at":"2018-03-15 00:01:02"},{"Temp":"29.60","created_at":"2018-03-15 00:17:34"},{"Temp":"22.30","created_at":"2018-03-15 00:33:50"},{"Temp":"22.60","created_at":"2018-03-15 00:50:20"},{"Temp":"29.50","created_at":"2018-03-15 01:07:05"},{"Temp":"22.90","created_at":"2018-03-15 01:22:36"},{"Temp":"22.30","created_at":"2018-03-15 01:38:16"},{"Temp":"22.30","created_at":"2018-03-15 01:54:48"},{"Temp":"22.30","created_at":"2018-03-15 02:12:19"},{"Temp":"23.30","created_at":"2018-03-15 02:29:52"},{"Temp":"22.60","created_at":"2018-03-15 02:48:37"},{"Temp":"29.80","created_at":"2018-03-15 03:06:01"},{"Temp":"22.50","created_at":"2018-03-15 03:22:39"},{"Temp":"29.30","created_at":"2018-03-15 03:40:51"},{"Temp":"22.70","created_at":"2018-03-15 03:57:27"},{"Temp":"22.50","created_at":"2018-03-15 04:13:57"},{"Temp":"22.20","created_at":"2018-03-15 04:31:30"},{"Temp":"29.50","created_at":"2018-03-15 04:46:51"},{"Temp":"23.30","created_at":"2018-03-15 05:03:39"},{"Temp":"22.20","created_at":"2018-03-15 05:18:20"},{"Temp":"29.30","created_at":"2018-03-15 05:34:32"},{"Temp":"22.30","created_at":"2018-03-15 05:52:24"},{"Temp":"29.30","created_at":"2018-03-15 06:09:23"},{"Temp":"29.80","created_at":"2018-03-15 06:25:52"},{"Temp":"22.60","created_at":"2018-03-15 06:42:09"},{"Temp":"22.50","created_at":"2018-03-15 07:01:33"}]}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        public List<TodayBean> today;
        public List<YesterdayBean> yesterday;
        public List<TempBean> temp;

        public static class TodayBean {
            /**
             * UsedValue : 0.00
             * updated_at : 2018-03-15 00:11:43
             */

            public String UsedValue;
            public String updated_at;
        }

        public static class YesterdayBean {
            /**
             * UsedValue : 0.00
             * updated_at : 2018-03-14 00:11:39
             */

            public String UsedValue;
            public String updated_at;
        }

        public static class TempBean {
            /**
             * Temp : 29.60
             * created_at : 2018-03-15 00:01:02
             */

            public String Temp;
            public String created_at;
        }
    }
}
