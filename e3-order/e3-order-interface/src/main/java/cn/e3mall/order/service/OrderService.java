package cn.e3mall.order.service;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * 订单服务
 */
public interface OrderService {
    /**
     * 创建订单
     * 1、在订单确认页面点击“提交订单”按钮生成订单。
     * 2、请求的url：/order/create
     * 3、参数：提交的是表单的数据。保存的数据：订单、订单明细、配送地址。
     * a)	向tb_order中插入记录。
     * i.	订单号需要手动生成。
     * 要求订单号不能重复。
     * 订单号可读性号。
     * 可以使用redis的incr命令生成订单号。订单号需要一个初始值。
     * ii.	Payment：表单数据
     * iii.	payment_type：表单数据
     * iv.	user_id：用户信息
     * v.	buyer_nick：用户名
     * vi.	其他字段null
     * b)	向tb_order_item订单明细表插入数据。
     * i.	Id：使用incr生成
     * ii.	order_id：生成的订单号
     * iii.	其他的都是表单中的数据。
     * c)	tb_order_shipping，订单配送信息
     * i.	order_id：生成的订单号
     * ii.	其他字段都是表单中的数据。
     * d)	使用pojo接收表单的数据。
     * 可以扩展TbOrder，在子类中添加两个属性一个是商品明细列表，一个是配送信息。
     * 把pojo放到e3-order-interface工程中
     * @param orderInfo
     * @return
     */
    E3Result createOrder(OrderInfo orderInfo);
}
