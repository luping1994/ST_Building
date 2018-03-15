package net.suntrans.building.domin;

import java.util.List;

/**
 * Created by Looney on 2018/3/15.
 */

public class AreaData {


    /**
     * code : 1
     * msg : 查询成功
     * data : [{"title":"办公一区","dev_id":"68","eletricityValue":"308.85"},{"title":"机房","dev_id":"65","eletricityValue":"3425.16"},{"title":"展厅电器","dev_id":"66","eletricityValue":"76.03"},{"title":"会议室","dev_id":"67","eletricityValue":"79.41"},{"title":"管理一区","dev_id":"76","eletricityValue":"2605.09"},{"title":"生产一区","dev_id":"73","eletricityValue":"109.77"},{"title":"生产二区","dev_id":"74","eletricityValue":"864.93"},{"title":"办公二区","dev_id":"71","eletricityValue":"436.24"},{"title":"办公三区","dev_id":"72","eletricityValue":"780.24"},{"title":"生产一区","dev_id":"81","eletricityValue":"374.11"},{"title":"员工二区","dev_id":"78","eletricityValue":"101.23"},{"title":"管理二区","dev_id":"79","eletricityValue":"496.67"},{"title":"电气实验室","dev_id":"75","eletricityValue":"321.89"},{"title":"环境实验室","dev_id":"77","eletricityValue":"752.03"},{"title":"公司总能耗","dev_id":"64","eletricityValue":"99.68"},{"title":"展厅展板","dev_id":"62","eletricityValue":"639.28"},{"title":"展厅右墙","dev_id":"63","eletricityValue":"49.27"},{"title":"会议室","dev_id":"64","eletricityValue":"99.68"},{"title":"产品库","dev_id":"95","eletricityValue":"1401.03"}]
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * title : 办公一区
         * dev_id : 68
         * eletricityValue : 308.85
         */

        public String title;
        public String dev_id;
        public String eletricityValue;
    }
}
