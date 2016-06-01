/**
 * Created by LiYonglei on 2016/4/19.
 * 打开模态框的按钮点击的时候根据其[data-operate]的值来决定是被打开的模态框的标题是添加还是修改;
 * 以及初始化模态框中的表单的验证状态和表单值
 */
(function(){
    $("html").on("click.modalOperate-add","[data-operate=add]",function(){
        var modal=$(this.dataset.target);
        modal.find(".modalHeaderText").removeClass("updateText");
        resetForm(modal,false);
        initInnerPageTurning(modal);
    }).on("click.modalOperate-update","[data-operate=update]",function(){
        var modal=$(this.dataset.target);
        $(modal).find(".modalHeaderText").addClass("updateText");
        resetForm(modal,true);
        initInnerPageTurning(modal);
    })
    function resetForm(modal,isUpdate){
        var form=$("form",modal);
        if(!form.length){
            return;
        }
        if(!isUpdate){
            form.clearForm();
        }
        /*
         * 使用延时的原因是formValidation里面的改变status的事件都是异步的
         * */
        setTimeout(function(){
            form.data('formValidation').resetForm();
        },100);
        modal.find("button,.btn").prop({ "disabled": false }).find(".spinning").addClass("hide");
    }
    /*
    * 初始化容器内部的翻页插件
    * */
    function initInnerPageTurning(modal){
        /*
        * 定义在pageTurning.js文件中
        * */
        initPageTurning(modal);
    }
}())

