/**
 * Created by LiYonglei on 2016/4/14.
 */
function importCSS(href){
    var styles=document.querySelectorAll("link");
    for(var i=0;i<styles.length;i++){
        var style=styles[i];
        if(style.getAttribute("href")==href||style.href==href){
            return;
        }
    }
    var link=document.createElement("link");
    link.rel="stylesheet";
    link.href=href;
    document.head.appendChild(link);
}