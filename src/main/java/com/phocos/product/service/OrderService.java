package com.phocos.product.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class OrderService {

    public String ecpayCheckout(int totalPrice) {
        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        AllInOne all = new AllInOne("");

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate("2017/01/01 08:05:23");
        obj.setTotalAmount(String.valueOf(totalPrice));
        obj.setTradeDesc("test Description");
        obj.setItemName("TestItem");
        // 交易结果返回网址，只接受以https开头的网站，可以使用ngrok
        obj.setReturnURL("http://localhost:8080/phocos/products/shoppingcar");
        obj.setNeedExtraPaidInfo("N");
        // 商店转跳网址 (Optional)
        obj.setClientBackURL("http://localhost:8080/phocos/products/camerashop2");
        String form = all.aioCheckOut(obj, null);

        return form;
    }
    
    public String ecpayCheckout2() {
        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        AllInOne all = new AllInOne("");

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setStoreID("Phocos");
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate("2017/01/01 08:05:23");
        obj.setTotalAmount("50");
        obj.setTradeDesc("test Description");
        obj.setItemName("TestItem");
        // 交易结果返回网址，只接受以https开头的网站，可以使用ngrok
        obj.setReturnURL("http://localhost:8080/phocos/products/shoppingcar");
        obj.setNeedExtraPaidInfo("N");
        // 商店转跳网址 (Optional)
        obj.setClientBackURL("http://localhost:8080/phocos/products/camerashop2");
        String form = all.aioCheckOut(obj, null);

        return form;
    }
}