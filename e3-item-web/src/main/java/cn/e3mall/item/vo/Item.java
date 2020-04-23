package cn.e3mall.item.vo;

import cn.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class Item extends TbItem {

    public String[] getImages(){
        String image2 = this.getImage();
        if(StringUtils.isNotBlank(image2)){
            String[] images = image2.split(",");
            return images;
        }
        return new String[0];
    }

    public Item(){

    }

    public Item(TbItem tbItem){
        BeanUtils.copyProperties(tbItem,this);
    }
}
