/**
 * Created by LiYonglei on 2016/4/21.
 * formValidation原生的判断是否表单通过验证的方式isValid()不准确，必须要先通过 .validation()方法之后才能正确判断，
 * 但是若是调用validation();那么就会对所有验证的元素进行验证；让所有的被验证元素后面加上验证结果标识等
 * params:e 是on('status.field.fv',function(e,data){})中的第一个参数e
 */
function formValidationIsValid(e){
    var form=$(e.currentTarget);
    var isValid=true;
    var validation=form.data('formValidation');

    var fields=Object.keys(validation.options.fields);
    fields.forEach(function(field){
        if(validation.isValidField(field)===false){
            isValid=false;
        }
    })
    return isValid;
}