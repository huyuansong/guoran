package com.hisoft.pam.im.common.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static Long getUUIDInOrderId(){
        Long orderId=Long.valueOf(UUID.randomUUID().toString().hashCode()) ;
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
        return orderId;
    }

    public static void main(String[] args){
        for (int i = 0; i<100; i++)
        System.out.println(UUIDUtils.getUUIDInOrderId());
    }
}