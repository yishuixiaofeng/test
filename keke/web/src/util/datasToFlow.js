/**
 * Created by LiYonglei on 2016/4/13.
 * 在渲染类似于树的结构的时候填充的数据在后台向前台推送的时候可能有两种格式；
 * 1、[{id:1,pid:0,name:'模块1'},{id:2,pid:0,name:'模块2'},{id:11,pid:1,name:'模块11'}]
 * 2、[{id:1,name:'模块1',children:[{id:11,name:'模块11'}]},{id:2,name:'模块2'}]
 * 这个简单的方法就是将第一种格式的数据转化成第二种格式的数据
 */
/*params:传入的参数可能得值有
 * datas:传入的数据
 * rootId:根元素的id
 * pidKey:父元素的标识字段
 * */
function datasToFlow(params){
    var datas=params.datas||[];
    var rootId=params.rootId||0;
    var pidKey=params.pidKey||"pid";
    /*将要生成的新的格式的json数据*/
    var flowDatas=[];
    /*已经使用过的id*/
    var ownerIds={};
    /*
    * parentData:每次插入新json的元素的父元素
    * */
    function transferred(parentData){
        datas.forEach(function(data,idx){
            /*
            * 父元素没有传递的时候其实就是将flowDatas作为父元素
            * */
            if(!parentData){
                if(rootId==data[pidKey]){
                    flowDatas.push(data);
                    /*
                    * 将使用过的id放入ownerIds中；后面遍历的时候就将这些id对应的元素剔除掉
                    * */
                    ownerIds[data[pidKey]]=true;
                    transferred(data);
                }
                /*
                * 元素的pid与传入的父id相同,并且元素的id没有在ownerIds中
                * */
            }else if(data[pidKey]==parentData.id&&!ownerIds[data.id]){
                /*
                * 父元素若是没有children属性则先生成一下
                * */
                if(!parentData.children){
                    parentData.children=[];
                }
                parentData.children.push(data);
                ownerIds[data[pidKey]]=true;
                transferred(data);
            }
        })
    }
    transferred();
    return flowDatas;
}
