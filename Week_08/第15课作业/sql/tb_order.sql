CREATE TABLE `tb_order` (
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_sn` varchar(20) NOT NULL COMMENT '订单编号',
  `order_sum_price` decimal(10,2) NOT NULL COMMENT '订单总价',
  `order_count` int(11) NOT NULL COMMENT '商品总数量',
  `user_id` bigint(20) NOT NULL COMMENT '【购买商品的】用户ID,tb_user',
  `user_address_id` bigint(20) DEFAULT NULL COMMENT '收获地址id，tb_user_address',
  `order_status` char(2) NOT NULL COMMENT '订单状态：-1=订单关闭，0=买家付款失败，1=待付款，2=付款成功，3=已发货，4=买家签收',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';