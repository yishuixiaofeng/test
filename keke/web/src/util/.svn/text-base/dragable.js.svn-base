/**
 * Created by LiYonglei on 2016/4/16.
 * 让添加了类dragable的元素可以拖拽;主要是用于bootstrap的模态框
 */
$('html').on({
    'mousedown.dragable':function(e){
        var handle=this;
        if($(this).attr('data-handle')){
            handle=$($(this).attr('data-handle'),this).get(0);
        }
        if($(e.target).closest(handle).length){
            $ (e.delegateTarget).data('dragable',{
                dragable:true,
                current:e.currentTarget,
                originalX:e.clientX,
                originalY: e.clientY,
                minLeft:-($(this).closest('.modal').innerWidth()-$(this).innerWidth())/2
            });
        }
    }
},'.dragable').on({
    'mousemove.dragable':function(e){
        var data=$(this).data('dragable');
        if(data&&data.dragable){
            var current=data.current;
            var currentX= e.clientX;
            var currentY= e.clientY;
            var toX=currentX-data.originalX;
            var toY=currentY-data.originalY;
            var minLeft=data.minLeft;
            $(current).css({left:function(idx,value){
                value=parseFloat(value);
                if(isNaN(value)){
                    value=$(current).position().left;
                }
                value=value+toX;
                if(value<minLeft){
                    value=minLeft;
                }
                return value;
            },top:function(idx,value){
                value=parseFloat(value);
                if(isNaN(value)){
                    value=$(current).position().top;
                }
                value=value+toY;
                if(value<0){
                    value=0;
                }
                return value;
            }});
            $.extend(data,{
                originalX: e.clientX,
                originalY: e.clientY
            });
        }
    },
    'mouseup.dragable':function(e){
        if($(this).data('dragable')){
            $(this).data('dragable').dragable=false;
        }
    }
})