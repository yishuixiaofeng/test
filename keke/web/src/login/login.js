/**
 * Created by LiYonglei on 2016/4/26.
 */
$('#loginDialog').modal({
    keyboard:false,
    backdrop:false,
    show:true
});
$('#loginDialog .modal-dialog').css({'margin-top':function(){
    return -$(this).height()/3;
},'top':function(){
    return $(this).parents().height()/3;
}});
$(window).resize(function(){
    $('#loginDialog .modal-dialog').css({'margin-top':function(){
        return -$(this).height()/3;
    },'top':function(){
        return $(this).parents().height()/3;
    }});
});
$('#loginForm').formValidation({
    framework: 'bootstrap',
    locale: 'zh_CN',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        username: {
            validators: {
                notEmpty: {
                    message: '用户名不能为空'
                }
            }
        },
        pwd: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                }
            }
        }
    }
}).on('status.field.fv', function (e, data) {
    var btn = $(e.currentTarget).closest(".modal").find(".submitBtn");
    btn.prop({ "disabled": !formValidationIsValid(e) });
});
$("#loginBtn").on('click',submitForm);
$(":input","#loginForm").on("keyup",function(event){
    if(event.keyCode=="13"){
        submitForm.call(this);
    }
})
function submitForm() {
    var _this = this;
    var form = $(_this).closest(".modal").find("form");
    var formValidation = $(form).data('formValidation');
    formValidation.validate();
    if(!formValidation.isValid()){
        return false;
    }
    var data = form.serializeForm();
    beforeSend(_this);
    $.post(appContextPath+"/ui/user/logon.do",data).done(function(res){
        if(res.success){
            location.href= appContextPath+ "/welcome.do";
        }else{
            BootstrapDialog.show({
                type: BootstrapDialog.TYPE_WARNING,
                title: '失败',
                draggable: true,
                message: "登录失败,失败原因是"+res.errorMsg,
                buttons: [{
                    label: '确定',
                    action: function (dialog) {
                        dialog.close();
                    }
                }]
            });
        }
        afterReceive(_this,true);
    });

}
