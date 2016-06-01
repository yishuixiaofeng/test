/**
 * Created by LiYonglei on 2016/4/27.
 * 提交表单和服务器响应调用的方法
 */
function beforeSend(btn) {
    $(btn).closest(".modal").find("button,.btn,:input").prop({ "disabled": true }).find(".spinning").removeClass("hide");
}
/*
* isKeepModal是否保持模态框不关闭
* */
function afterReceive(btn,isKeepModal) {
    var modal=$(btn).closest(".modal");
    $(btn).closest(".modal").find("button,.btn,:input").prop({ "disabled": false }).find(".spinning").addClass("hide");
    if(!isKeepModal){
        modal.modal('hide');
    }
}

