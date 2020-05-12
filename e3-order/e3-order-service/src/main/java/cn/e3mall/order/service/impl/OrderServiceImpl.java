package cn.e3mall.order.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${order.id.gen.key}")
    private String orderIdGenKey;
    @Value("${order.id.start}")
    private String orderIdStartKey;
    @Autowired
    private TbOrderMapper orderMapper;
    @Value("${order.detail.id.gen.key}")
    private String orderDetailIdGenKey;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        //接收表单的数据
        //生成订单ID
        if(!jedisClient.exists(orderIdGenKey)){
            //设置初始值
            jedisClient.set(orderIdGenKey,orderIdStartKey);
        }
        String orderId = jedisClient.incr(orderIdGenKey).toString();
        orderInfo.setOrderId(orderId);
        orderInfo.setPostFee("0");
        // 1 未付款 2 已付款 3 未发货 4 已发货 5交易成功 6 交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderMapper.insert(orderInfo);
        //向订单明细表中插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem item : orderItems) {
            Long orderItemId = jedisClient.incr(orderDetailIdGenKey);
            item.setId(orderItemId.toString());
            item.setOrderId(orderId);
            orderItemMapper.insert(item);
        }
        //向订单物流表中插入数据
        TbOrderShipping shipping = orderInfo.getOrderShipping();
        shipping.setCreated(new Date());
        shipping.setOrderId(orderId);
        shipping.setUpdated(new Date());
        orderShippingMapper.insert(shipping);
        return E3Result.ok(orderId);
    }
}
