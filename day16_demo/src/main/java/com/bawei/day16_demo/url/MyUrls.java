package com.bawei.day16_demo.url;

public interface MyUrls {
    String BASEURL = "http://172.17.8.100/small/";

    //banner接口
    String BANNER = "commodity/v1/bannerShow";
    //首页接口
    String HOMEPAGE = "commodity/v1/commodityList";
    String LOGIN = "user/v1/login";
    String ORDERITEM = "order/verify/v1/findOrderListByStatus";
    String SHOPPING_ITEM = "commodity/v1/findCategory";
}
