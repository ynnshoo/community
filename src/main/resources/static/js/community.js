/**
 * 提交回复
 */
function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content)
}
function comment2target(targetId,type,content){
    if (!content){
        alert("client----->回复列表不能为空")
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        // 给后端传入为json格式
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type":type
        }),
        success: function (response){
            if (response.code==200){
                // $("#comment_section").hide();
                window.location.reload();
            }else{
                //如果没有登录->确认是否登录->登录之后跳转到原来页面
                if (response.code=2003){
                    var isAccepted=confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.f65928dc9c38ef94&redirect_uri=http://localhost:2005/callback;&scope=user&state=STATE");
                        window.localStorage.setItem("closable", true);
                    }
                }else{
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    })
    console.log(questionId);
    console.log(content);
}
function comment(e){
    var commentId=e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}
/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    console.log("id");
    var comments = $("#comment-" + id);
    //获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active")
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    }))
                        .append($("<div/>", {
                            // "class": "media-heading",
                            "html": comment.content
                        }))
                        .append($("<div/>", {
                            "class": "menu",
                        }).append($("<span/>", {
                            "class": "pull-right",
                            "html": moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm:ss')
                        })));
                    var mediaElement = $("<div/>", {
                        "class": "media",
                    }).append(mediaLeftElement).append(mediaBodyElement);
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                        // html: comment.content
                    });
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                // var commentBody=$("comment-body-"+id);
                // var items = [];
                // commentBody.appendChild();
                // $.each( data.data, function( comment ) {
                //     var c=$("<div/>",{
                //         "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                //         html: comment.content
                //     });
                //     items.push(c);
                // });
                // commentBody.append($("<div/>",{
                //     "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
                //     "id":"comment-"+id,
                //     html: items.join("")
                // }));
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

/**
 * 展示tag页面
 */
function showSelectTag(){
    $("#select-tag").show();
}

/**
 * 标签
  */
function selectTag(e){
    var value = e.getAttribute("data-tag");
    var previous=$("#tag").val();
    if (previous.indexOf(value)==-1){
        if (previous){
            $("#tag").val(previous+','+value)
        }else{
            $("#tag").val(value);
        }
    }
}

