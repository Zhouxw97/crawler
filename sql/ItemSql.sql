CREATE TABLE `item` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `spu` bigint(15) DEFAULT NULL COMMENT '商品集合id',
  `sku` bigint(15) DEFAULT NULL COMMENT '商品最小品类单元id',
  `title` varchar(100) DEFAULT NULL COMMENT '商品标题',
  `price` bigint(10) DEFAULT NULL COMMENT '商品价格',
  `store` varchar(255) DEFAULT NULL COMMENT '店铺',
  `promotions` varchar(255) DEFAULT NULL COMMENT '促销活动',
  `pic` varchar(200) DEFAULT NULL COMMENT '商品图片',
  `url` varchar(200) DEFAULT NULL COMMENT '商品详情地址',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=845 DEFAULT CHARSET=utf8;


CREATE TABLE `jd_item` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `spu` varchar(20) DEFAULT NULL COMMENT '商品集合id',
  `sku` varchar(20) DEFAULT NULL COMMENT '商品最小品类单元id',
  `title` varchar(100) DEFAULT NULL COMMENT '商品标题',
  `current_price` bigint(10) DEFAULT NULL COMMENT '商品当前价格',
  `guide_price` bigint(10) DEFAULT NULL COMMENT '商品指导价格',
  `store` varchar(255) DEFAULT NULL COMMENT '店铺',
  `promotions` varchar(255) DEFAULT NULL COMMENT '促销活动',
  `coupon` varchar(255) DEFAULT NULL COMMENT '优惠劵',
  `pic` varchar(200) DEFAULT NULL COMMENT '商品图片',
  `url` varchar(200) DEFAULT NULL COMMENT '商品详情地址',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1385412079699988483 DEFAULT CHARSET=utf8 COMMENT='京东商品品类信息';