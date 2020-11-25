CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(100) NOT NULL COMMENT '用户姓名',
  `user_gender` char(1) DEFAULT NULL COMMENT '用户性别0=男，1=女',
  `user_password` varchar(255) NOT NULL COMMENT '用户密码',
  `user_email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `fixed_phone` varchar(15) DEFAULT NULL COMMENT '固定电话',
  `mobile_phone` varchar(11) NOT NULL COMMENT '手机',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `user_status` char(1) NOT NULL COMMENT '用户状态0=激活，1=锁定，2=停用',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';




CREATE TABLE `tb_user_address` (
  `user_address_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户收货地址id',
  `provice` varchar(50) DEFAULT NULL COMMENT '省',
  `city` varchar(50) DEFAULT NULL COMMENT '市',
  `area` varchar(50) DEFAULT NULL COMMENT '区',
  `street` varchar(100) DEFAULT NULL COMMENT '街',
  `address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `is_default` char(1) DEFAULT '0' COMMENT '是否默认地址0=否，1=是',
  PRIMARY KEY (`user_address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收获地址表';




CREATE TABLE `tb_goods` (
  `goods_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_sn` varchar(20) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(255) NOT NULL COMMENT '商品名称',
  `goods_original_price` decimal(10,2) NOT NULL COMMENT '商品原始价格',
  `goods_current_price` decimal(10,2) NOT NULL COMMENT '商品现在价格',
  `goods_unit` char(10) NOT NULL COMMENT '单位',
  `goods_imgs` varchar(1000) DEFAULT NULL COMMENT '商品图片地址json数组',
  `goods_tips` text COMMENT '促销信息',
  `is_sale` char(1) NOT NULL COMMENT '是否上架0=未上架，1=已上架',
  `is_new` char(1) NOT NULL COMMENT '是否新品0=否，1=是',
  `goods_cat_id` bigint(20) NOT NULL COMMENT '商品分类ID',
  `goods_desc` text COMMENT '商品描述',
  `sale_num` int(11) NOT NULL DEFAULT '0' COMMENT '总销量',
  `sale_time` timestamp NULL DEFAULT NULL COMMENT '上架时间',
  `visit_num` int(11) NOT NULL DEFAULT '0' COMMENT '访问数',
  `appraise_num` int(11) NOT NULL DEFAULT '0' COMMENT '评价数',
  `goods_status` char(1) NOT NULL COMMENT '商品状态0=删除，1=有效',
  `goods_type` char(1) NOT NULL DEFAULT '1' COMMENT '商品类型0=实物商品，1=虚拟商品',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人id',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '最近更新人id',
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息表';





CREATE TABLE `tb_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_sn` varchar(20) NOT NULL COMMENT '订单编号',
  `order_sum_price` decimal(10,2) NOT NULL COMMENT '订单总价',
  `order_count` int(11) NOT NULL COMMENT '商品总数量',
  `user_id` bigint(20) NOT NULL COMMENT '【购买商品的】用户ID,tb_user',
  `user_address_id` bigint(20) DEFAULT NULL COMMENT '收获地址id，tb_user_address',
  `order_status` char(2) NOT NULL COMMENT '订单状态：-1=订单关闭，0=买家付款失败，1=待付款，2=付款成功，3=已发货，4=买家签收',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';




CREATE TABLE `tb_order_detail` (
  `order_detail_id` bigint(20) DEFAULT NULL COMMENT '订单明细id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id,tb_goods',
  `goods_current_price` decimal(10,2) DEFAULT NULL COMMENT '商品当前价格',
  `order_num` int(11) DEFAULT NULL COMMENT '购买商品数量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';


