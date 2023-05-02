/*发送消息*/
function send(headSrc, str) {
    var html = "<div class='msg'><img src=" + headSrc + " />" +
        "<p><i class='msg_input'></i>" + str + "</p></div>";
    upView(html);
}

/*发送消息*/
function send2(create_time, name, text) {
    var classnm = "send"
    if (name.indexOf("医生") == -1) {
        //患者
        classnm = "show"
    }
    var html = "<div class=\" " + classnm + " \"> <div class=\"time\">" + create_time + "</div>\n" +
        "\t\t\t\t<div class=\"msg\">\n" +
        "\t\t\t\t\t<h3>" + name + "</h3>\n" +
        "\t\t\t\t\t<p><i class=\"msg_input\"></i>" + text + "</p>\n" +
        "\t\t\t\t</div></div>";
    upView(name, html);
}

/*接受消息*/
function show(headSrc, str) {
    var html = "<div class='show'><div class='msg'><img src=" + headSrc + " />" +
        "<p><i class='msg_input'></i>" + str + "</p></div></div>";
    upView(html);
}

/*更新视图*/
function upView(name, html) {
    $('.message').append(html);
    $('body').animate({scrollTop: $('.message').outerHeight() - window.innerHeight}, 200)
}

function sj() {
    return parseInt(Math.random() * 10)
}

$(function () {
    $('.footer').on('keyup', 'input', function () {
        if ($(this).val().length > 0) {
            $(this).next().css('background', '#114F8E').prop('disabled', true);

        } else {
            $(this).next().css('background', '#ddd').prop('disabled', false);
        }
    })
    // $('.footer p').click(function () {
    //     show("./images/touxiangm.png", $(this).prev().val());
        test();
    // })
})

/*测试数据*/
var arr = ['我是小Q', '好久没联系了！', '你在想我么', '怎么不和我说话', '跟我聊会天吧'];
var imgarr = ['images/touxiang.png', 'images/touxiangm.png']
// test()

//定时获取数据
function test() {
    sessionStorage.removeItem("call_time")

    setInterval(() => {
        getMessage()
    }, 1000)


}

function sendMessage() {
    var ccsUrl = "http://localhost:8088/";
    if (!sessionStorage.getItem("user_id")) {
        alert('请登陆');
        window.location.href = ccsUrl + '/doctor/login.html'
        return
    }
    var text = $('.text').val()
    if (text == null || text == '') {
        alert('不能发空消息')
        return
    }
    var param = {
        "name": "患者:" + sessionStorage.getItem("user_phone"),
        "order_id": sessionStorage.getItem("call_order_id"),
        "text": text
    }
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/addChat",
        data: JSON.stringify(param),
        cache: false,
        success: function (data, status) {
            console.log(data.msg)
            $('.text').val('')
        },
        error: function () {
        }
    });
}

function getMessage() {
    var create_time = null;
    if (sessionStorage.getItem("call_time") != null) {
        create_time = sessionStorage.getItem("call_time")
    }
    var param = {"order_id": sessionStorage.getItem("call_order_id"), "create_time": create_time}
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/getChatList",
        data: JSON.stringify(param),
        cache: false,
        success: function (data) {
            console.log(data.chatList)
            var chatList = data.chatList
            for (var i = 0; i < chatList.length; i++) {
                var chat = chatList[i]
                send2(chat.create_time, chat.name, chat.text)
            }
            if (chatList.length > 0) {
                sessionStorage.setItem("call_time", chatList[chatList.length - 1].create_time)
                /*滚动到底部*/
                $('html, body').animate({ scrollTop: $(document).height()+5000 }, 500);
            }

        },
        error: function () {
        }
    })

}
