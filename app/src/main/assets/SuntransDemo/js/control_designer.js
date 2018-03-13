var wsUri = "ws://us.suntrans.net:6300";//ebike.suntrans-cloud.com
var ws = null;
var closeTimer;



function webSocket() {
    try {
        ws = new WebSocket(wsUri);
        ws.onopen = function (evt) {
        };
        ws.onclose = function (evt) {
        };
        ws.onmessage = function (evt) {
            parseMessage(eval('(' + evt.data + ')'));
            // console.log("Message received :", evt.data);
        };
        ws.onerror = function (evt) {
            alert("无法连接服务器");
            control.alert("无法连接服务器");

        };
    } catch (exception) {
        alert("无法连接服务器");
        control.alert("无法连接服务器");
        // ws('ERROR: ' + exception);
    }
}

function checkSocket(stringify) {
    if (ws != null) {
        var stateStr;
        switch (ws.readyState) {
            case 0: {
                stateStr = "CONNECTING";
                control.alert("正在连接服务器");

                break;
            }
            case 1: {
                stateStr = "OPEN";
                ws.send(stringify);
                break;
            }
            case 2: {
                stateStr = "CLOSING";
                control.alert("连接已关闭");
                break;
            }
            case 3: {
                stateStr = "CLOSED";
                webSocket();
                control.alert("连接已关闭");
                break;
            }
            default: {
                stateStr = "UNKNOW";
                break;
            }
        }
    } else {
    }
}

function switchChannel(vtype, status, channel_id) {
    var command = "";
    if (status == '1') {
        command = "0";
    }else {
        command ="1";
    }
    var msg = new Object();
    msg.action = "switch";
    msg.device = vtype;
    msg.user_id = "1";
    msg.channel_id = channel_id;
    msg.command =command;
    var stringify = JSON.stringify(msg);
    checkSocket(stringify);
}

function parseMessage(json) {


    control.alert(JSON.stringify(json))

}