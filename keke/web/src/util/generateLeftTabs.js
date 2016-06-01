/**
 * Created by LiYonglei on 2016/4/22.
 * 生成左侧的树
 * params:
 * res 后台返回的数据
 * $leftTabs左侧导航的容器元素
 */
function generateLeftTabs(res,$leftTabs){
    if(!res.success){
        console.error(res.errorCode,res.errorMsg);
    }
    res.data.forEach(function(item){
        if(item.f_parent_id===0){
            item.isParent=true;
        }else{
            item.isParent=false;
        }
    })
    var datas=datasToFlow({datas:res.data,pidKey:"f_parent_id"});
    $leftTabs.empty();
    insertDom($leftTabs,datas);
    $(".metismenu",$leftTabs).metisMenu();
}
function insertDom(parent,datas){
    var ul=undefined;
    if(!parent.hasClass("sidebar-nav")){
        ul=$("<ul/>",{
            "aria-expanded":"false",
            "class":"collapse",
            "style":"height: 0px;"
        });
    }else{
        ul=$("<ul/>",{
            "class":"metismenu"
        })
    }
    ul.appendTo(parent);
    datas.forEach(function(data,idx){
        var li=$("<li/>").appendTo(ul);
        var a=$("<a/>",{
            "aria-expanded":"false"
        }).append(
            $("<span/>",{
                "class":"sidebar-nav-item",
                "text":data.name
            })
        ).appendTo(li);
        if(!data.isParent){
            a.attr({"data-url":data.web_key})
        }
        if(data.isParent&&data.children&&data.children.length){
            $("<span/>",{
                "class":"fa arrow"
            }).appendTo(a);
            insertDom(li,data.children)
        }
    })
}
