var floor = 1;

if ($("#openType").val() == "alarm") {
    $("#btnAlarm").trigger("click");
}




function openConfirmDialog(d) {
//    console.log(d.href);
    sendCommand(d)

    // var htmlStr = '';
    // htmlStr += '<input type="hidden" id="channel_id" value="0">';
    // htmlStr += '<div class="weui-mask"></div>';
    // htmlStr += '<div class="weui-dialog__hd"><strong class="weui-dialog__title">弹窗标题</strong></div>';
    // htmlStr += '<div class="weui-dialog__bd">';
    // htmlStr += d.title + '</div>';
    // htmlStr += '</div>';
    // htmlStr += '<div class="weui-dialog__ft">';
    // htmlStr += '<a id="qvxiao" href="javascript:void (0);" class="weui-dialog__btn weui-dialog__btn_default">取消</a>';
    // htmlStr += '<a id="queding" href="javascript:void (0);" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>';
    // htmlStr += '</div></div>';

    // var tips = d.status ? "是否关闭" : "是否打开";
    //
    // $("#title")
    //     .text('关闭' + d.title);
    // $("#alertDialog")
    //     .show()
    //     .data("data", d)
    // $("#queding")
    //     .click(function () {
    //         sendCommand(d)
    //         $("#alertDialog").hide();
    //
    //     });
    // $("#qvxiao")
    //     .click(function () {
    //         $("#alertDialog").hide();
    //     });
}

function sendCommand(d) {
//    console.log('channel_id为：' + d.channel_id);
    switchChannel(d.vtype,d.status,d.channel_id);
}

//加载容器属性和元件
// initContainer();
// setInterval("refreshContainer()", 2000);


var tokens;
var house_ids;

function init(token, house_id) {
    tokens = token;
    house_ids = house_id;
    floor = house_id;
    //加载容器属性和元件
    initContainerByToken(tokens, floor);
    setInterval("refreshContainerByToken(tokens,house_ids)", 8000);
}


function refreshContainerByToken(token, house_id) {
    $.ajax({
        url: 'http://smarthome.suntrans.net/api.php/ScreenShow/plan',
        type: 'POST',
        data: {'area_id': house_id},

        dataType: "json",
        success: function (json) {
            // setPlan1Data(json);
            var bgImage = "";
            if (floor == '3') {
                bgImage = "img/zhanting.png";
            }else if (floor=='4'){
                bgImage = "img/floor2.png";

            }else if (floor == '5'){
                bgImage = "img/floor3.png";
            }else {
                bgImage = "img/floor3.png";

            }
            var con = json.container;
            if (con) {
                var width = 1080;
                var height = 1692;
                scale = $("body").width() / width;
                $("div.full-wrapper").css("height", height * scale);
                $("svg.designer").css("transform", "scale(" + scale + ")");
                $("svg.designer").css("width", width);
                $("svg.designer").css("height", height);
                $("svg.designer").css("background-color", "#ffffff");
                $("svg.designer").css("background-image", "url(" + bgImage+ ")");
                $("svg.designer").empty();
                $("body").css("background-color", "#ffffff");
            }
            // json.signals.map(function(signal){
            //  if(signal){
            // 	 signalMap[''+signal.id]=signal;
            //  }
            // });

            json.elements.map(createElement);
        }
    });
}

//初始化容器
function initContainerByToken(token, house_id) {
    $.ajax({
        url: 'http://smarthome.suntrans.net/api.php/ScreenShow/plan',
        type: 'POST',
        data: {'area_id': house_id},

        dataType: "json",
        success: function (json) {
            // setPlan1Data(json);
            var bgImage = "";
            if (floor == '3') {
                bgImage = "img/zhanting.png";
            }else if (floor=='4'){
                bgImage = "img/floor2.png";

            }else if (floor == '5'){
                bgImage = "img/floor3.png";
            }else {
                bgImage = "img/floor3.png";

            }
            var con = json.container;
            if (con) {
                var width = 1080;
                var height = 1692;
                scale = $("body").width() / width;
                $("div.full-wrapper").css("height", height * scale);
                $("svg.designer").css("transform", "scale(" + scale + ")");
                $("svg.designer").css("width", width);
                $("svg.designer").css("height", height);
                $("svg.designer").css("background-color", "#ffffff");
                $("svg.designer").css("background-image", "url(" + bgImage+ ")");
                $("svg.designer").empty();
                $("body").css("background-color", "#ffffff");
            }
            // json.signals.map(function(signal){
            //  if(signal){
            // 	 signalMap[''+signal.id]=signal;
            //  }
            // });

            json.elements.map(createElement);
        }
    });
}

//创建元素
function createElement(ele) {
    // if (ele.type == "image") {
    createImage(ele);
    // }
}

//创建一张图片
function createImage(data) {
    var imageGroup = d3.select("#floorPlanSvg")
        .append("g")
        .data([data]);

    if (data.status) {
        if (data.vtype == 1) {
            imageGroup.classed("ele", true)
        }
    }


    imageGroup.append("image")
        .classed("control-image", true)
        .attr("x", function (d) {
            return d.x
        })
        .attr("y", function (d) {
            return d.y - 228
        })
        .attr("width", function (d) {
            return d.w;

        })
        .attr("height", function (d) {
            return d.h;
        })
        .attr("xlink:href", function (d) {
            if (d.status==1) {
                    return d.openUrl;

            } else {
                return d.closeUrl;


            }
        });
    imageGroup.on("click", openConfirmDialog);

}


