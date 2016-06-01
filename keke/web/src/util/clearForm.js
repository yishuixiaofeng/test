/**
 * Created by LiYonglei on 2016/4/22.
 * 将表单中的所有的元素的值全部清空
 */
(function($){
    $.fn.clearForm=function(){
        $(":text,[type=hidden]",this).val("");
        $("textarea",this).val("");
        $("radio",this).prop({"checked":false});
        $("checkbox",this).prop({"checked":false});
        $("option:first-child",this).prop({"selected":true});
        return this;
    }
}(jQuery))
