/**
 * Created by BlackZXK on 4/7/2017.
 */
$(function() {
    $(".chat-list").css("height",window.innerHeight/2);

    $("#uplodeImg").change(function() {
        $("#showLocation").html($(this).val());
    });

    $("#change").click(function() {
        if ($(this).html() == "白天") {
            $(this).html("黑夜");
            $("body").addClass("night-style");
            $(".content-main a").addClass("night-style");
            $(".modal-content").addClass("night-style");
            $(".modal-content input").addClass("night-style");
            $(".modal-content textarea").addClass("night-style");
            $.ajax({
                url: "/square/night",
                data: null,
                type: "POST",
                dataType: "json",
                success: function (r) {
                    var str="";
                    for(var i=0;i<r.data.length;i++){
                        str+='<div class="row post">'
                            +'<div class="col-md-1">'
                            +'<a href="/square/postview/'+r.data[i].id+'">'
                            +'<img class="img-responsive img-circle" src="../../../static/img/videotest.png">'
                            +'<span>匿名</span>'
                            +'</a>'
                            +'</div>'
                            +'<div class="col-xs-12 col-md-7">'
                            +'<h5><a href="/square/postview/'+r.data[i].id+'">'+r.data[i].postTitle+'</a></h5>'
                            +'<div class="long">'+r.data[i].postContent+'</div>'
                            +'</div>'
                            +'<div class="col-xs-12 col-md-4 text-right text-bottom">'
                            +'<small>回复人数</small>&nbsp;&nbsp;<small>发布时间:'+r.data[i].gmtModified+'</small>'
                            +'</div>'
                            +'</div>';
                    }

                    $(".post-list").html(str);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status==0){
                        alert("error");
                    }
                },
            });

        } else {
            $(this).html("白天");
            $("body").removeClass("night-style");
            $(".content-main a").removeClass("night-style");
            $(".modal-content").removeClass("night-style");
            $(".modal-content input").removeClass("night-style");
            $(".modal-content textarea").removeClass("night-style");
            $.ajax({
                url: "/square/night",
                data: null,
                type: "POST",
                dataType: "json",
                success: function (r) {
                    var str="";
                    for(var i=0;i<r.data.length;i++){
                        str+='<div class="row post">'
                            +'<div class="col-md-1">'
                            +'<a href="/square/postview/'+r.data[i].id+'">'
                            +'<img class="img-responsive img-circle" src="../../../static/img/videotest.png">'
                            +'<span>匿名</span>'
                            +'</a>'
                            +'</div>'
                            +'<div class="col-xs-12 col-md-7">'
                            +'<h5><a href="/square/postview/'+r.data[i].id+'">'+r.data[i].postTitle+'</a></h5>'
                            +'<div class="long">'+r.data[i].postContent+'</div>'
                            +'</div>'
                            +'<div class="col-xs-12 col-md-4 text-right text-bottom">'
                            +'<small>回复人数</small>&nbsp;&nbsp;<small>发布时间:'+r.data[i].gmtModified+'</small>'
                            +'</div>'
                            +'</div>';
                    }

                    $(".post-list").html(str);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status==0){
                        alert("error");
                    }
                },
            });
        }
    });

    $("#send-msg").click(function() {
        if ($("#msg").val().trim() == "") {
            return;
        }
        $(".chat-list").append("<div class='chat row text-right'><div class='col-xs-10 col-md-10'><span>" + $("#msg").val() + "</span></div><div class='col-xs-2 col-md-2' ><img src='../../../static/img/user-big.jpeg' class='img-responsive img-circle'></div></div>");
        setTimeout("test()", 5000);
    });
});

function test() {
    $(".chat-list").append("<div class='chat row'><div class='col-xs-2 col-md-2'><img src='../../../static/img/user-big.jpeg' class='img-responsive img-circle'></div><div class='col-xs-10 col-md-10'><span>我喜欢你</span></div></div>");
}


$(function () {
    $("#send").click(function () {
        var title = $("#titlein").val();
        var content= $("#contentin").val();
        var data = {
            "title":  title,
            "content":content
        };
        $.ajax({
            url: "/square/addpost",
            data: data,
            type: "POST",
            dataType: "json",
            success: function (r) {
                if(r.status==0) {
                    window.location.reload();
                }
                else{
                    if(r.status==-1){
                        window.location.href="/user";
                        alert(r.info);
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==0){
                    alert("error");
                }
            },
        });
    });
});

$("#uplodeImg").change(function(){
    $("#showLocation").html($(this).val());
});