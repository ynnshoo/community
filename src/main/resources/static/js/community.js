function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if (!content){
        alert("client----->回复列表不能为空")
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        // 给后端传入为json格式
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type":1
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