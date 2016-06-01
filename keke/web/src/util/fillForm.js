/**
 * Created by LiYonglei on 2016/4/18.
 * 简单的填充表单的方法
 */
(function($){
    $.fn.fillForm=function(data){
        var form=this.eq(0);
        $.each(data,function(key,idx){
            if(data[key]==null||data[key]==undefined){
                data[key]="";
            }
            var value=data[key];
            var input=$("[name="+key+"]",form);
            if(input.is(":radio")){
                input.filter("[value="+value+"]").prop({"checked":true});
                return true;
            }else if(input.is(":checkbox,select")){
                var separators=(""+value).match(/,/g);
                if(separators){
                    value=value.split(",");
                }
            }
            input.val(value);
        })
        return this;
    }
}(jQuery))
