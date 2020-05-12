package cn.e3mall.order.pojo;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbOrder;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * 订单pojo扩展，用来接收表单数据
 */
public class OrderInfo extends TbOrder implements Serializable {

    /**
     * 订单所包含的商品
     */
    private List<TbOrderItem> orderItems;

    /**
     * 订单配送信息
     */
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
