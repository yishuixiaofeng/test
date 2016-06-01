/**
 * Created by LiYonglei on 2016/5/3.
 * 对bootstrap翻页的js封装
 */
$("html").on("click.pageTurning",".pageTurningPager li",function(event){
    if($(this).hasClass("disabled")){
        return false;
    }
    var operateState=$(this).closest("li").hasClass("previous")?"previous":"next";
    var currentPageIdx;
    var pageTurningContainer=$(this).closest(".pageTurningContainer");
    var pageTurningPager=$(this).closest(".pageTurningPager");
    var formerPageIdx=pageTurningContainer.find(".pageTurningPanel").index(pageTurningContainer.find(".pageTurningActive"));
    pageTurningPager.find("li").removeClass("disabled");
    if(operateState==="previous"){
        currentPageIdx=formerPageIdx-1;
    }else if(operateState==="next"){
        currentPageIdx=formerPageIdx+1;
    }
    pageTurningContainer.find(".pageTurningPanel").eq(currentPageIdx).addClass("pageTurningActive").removeClass("pageTurningHidden").siblings(".pageTurningPanel").removeClass("pageTurningActive").addClass("pageTurningHidden");
    if(currentPageIdx==0||currentPageIdx==pageTurningContainer.find(".pageTurningPanel").size()-1){
        $(this).addClass("disabled");
    }
})
/*
* container盛放翻页插件的容器
* */
function initPageTurning(container){
    $(container).find(".previous").addClass("disabled").next(".next").removeClass("disabled");
    $(container).find(".pageTurningPanel:first-child").addClass("pageTurningActive").removeClass("pageTurningHidden").siblings(".pageTurningPanel").removeClass("pageTurningActive").addClass("pageTurningHidden");
}
