/**
 * Created by LiYonglei on 2016/4/14.
 * jquery ajax加载的页面文件中包含script引入的时候,script文件总是自动加上一个随机参数；不利于线上调试；因此就写了这个方法
 * jquery load自动添加随机参数是必要的，因为若是页面中已经加载过某个js文件就可能不会重新加载，但是js中有很大的可能需要在
 * js重新加载的的时候执行脚本,但是这种带随机参数的js不能进行调试也不能简单的使用fiddler进行线上抓取替换调试
 * 但是现在这种方式的加载方式也是有隐患的；那就是每一个js的加载都是异步的；这就意味着若是多个引入的js有上下依赖关系的话
 * 后面的文件加载的快的话可能导致错误.
 * src:js加载的路径
 * reload:是否重新加载js;默认为false,不重新加载
 */
function importScript(src,reload){
    var scripts=document.querySelectorAll("script");
    for(var i=0;i<scripts.length;i++){
        var script=scripts[i];
        if(script.src==src||script.getAttribute("src")==src){
            if(reload){
                script.parentNode.removeChild(script);
                break;
            }
            return;
        }
    }
    var script= document.createElement("script");
    script.src=src;
    document.head.appendChild(script);
}