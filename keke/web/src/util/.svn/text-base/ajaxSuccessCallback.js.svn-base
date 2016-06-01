/**
 * Created by LiYonglei on 2016/4/27.
 * 倘若session失效了；就跳转到登录页去
 */
/*
* 忽视验证的errorCode
* */
var ignoreDataFilterErrorCode={"user.1013":true};
$.ajaxSetup({
    dataFilter:function(data,type){
        try{
            var jsonData=JSON.parse(data);
            if(jsonData.errorCode=="sys.0003"){
                $.each(BootstrapDialog.dialogs, function(id, dialog){
                    dialog.close();
                 });
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: '由于您长时间未操作,为了您的数据安全,请重新登录!',
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                            location.href=appContextPath+"/";
                        }
                    }]
                });
            }else if(!jsonData.success&&!ignoreDataFilterErrorCode[jsonData.errorCode]){
                $.each(BootstrapDialog.dialogs, function(id, dialog){
                    dialog.close();
                 });
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message:jsonData.errorMsg,
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
                return;
            }
        }catch (event){
            return data;
        }
        return data;
    }
});
/*
* 当发出ajax请求的时候让加载中出现,ajax完毕的时候让加载中消失
* 当发出ajax请求的时候将ajax请求的url和request保存到一个json中去；当请求完毕或者又发出相同的url地址的请求的时候
* 就将前面的请求abort掉
* */
var ajaxArray={};
$(document).ajaxSend(function(evt, request, settings){
    (function(){
        /*
         * 若是ajax json中已经拥有了ajax的路径，那么就将对应的request abort掉，再将新的request放入
         * */
        var url=settings.url;
        var path=url.substring(0,url.indexOf("?")!=-1?url.indexOf("?"):url.length);
        if(ajaxArray[path]){
            ajaxArray[path].abort();
        }
        ajaxArray[path]=request;
    }())
    $(".load").removeClass("loaded");
}).ajaxComplete(function(evt,request, settings){
    (function(){
        /*
         * 将ajax json中的此次请求的ajax对应的数据清除掉
         * */
        var url=settings.url;
        var path=url.substring(0,url.indexOf("?")!=-1?url.indexOf("?"):url.length);
        if(ajaxArray[path]){
            delete ajaxArray[path];
        }
    }())
    $(".load").addClass("loaded");
}).ajaxError(function(event,request, settings){
    /*
    * 如果是abort的；就不要进行错误处理(status==0是abort的)
    * */
    if(!request.status){
        return;
    }
    $.each(BootstrapDialog.dialogs, function(id, dialog){
        dialog.close();
    });
    var errorMsg="";
    try{
        errorMsg=JSON.parse(request.responseText).errorMsg;
    }catch (e){
        errorMsg="系统发生未知错误";
    }
    BootstrapDialog.show({
        type: BootstrapDialog.TYPE_WARNING,
        title: '错误',
        draggable: true,
        message:errorMsg,
        buttons: [{
            label: '确定',
            action: function (dialog) {
                dialog.close();
            }
        }]
    });
    $("[data-operate=delete]").prop({"disabled":false}).find(".spinning").addClass("hide");
});