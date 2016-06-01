/**
 * Created by LiYonglei on 2016/4/15.
 * 序列化表单中元素为json
 */
(function($){
    $.fn.serializeForm=function(){
        var hashKey={};
        var json={};
        var array=this.eq(0).serializeArray();
        array.forEach(function(item,idx){
            if(hashKey[item.name]){
                json[item.name]+=","+item.value.trim();
            }else{
                hashKey[item.name]=true;
                json[item.name]=item.value.trim();
            }
        })
        return json;
    }
}(jQuery))
