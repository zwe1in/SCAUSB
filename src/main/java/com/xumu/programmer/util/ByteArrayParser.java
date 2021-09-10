package com.xumu.programmer.util;

import java.math.BigDecimal;

public class ByteArrayParser {
    
    private static final int scale = 3; //保留位数
    private static final int roundingMode = 4; //最大不约进数
    
    
    /**
     * 高字节在前的字节数据转整型
     * @param bytes
     * @return
     */
    public static int heightByteBeforeArrayToInt(byte[] bytes) {
        int value = 0;
        for(int i = 0; i < bytes.length; i ++) { 
        int shift= (bytes.length - i - 1) * 8; 
        value +=(bytes[i] & 0xFF) << shift;
        }
        return value;
    }
    
    public static byte[] deleteAtIndexWithLength(byte[] bs, int index, int length) {
        int newByteArrayLength = bs.length - length;
        byte[] ret = new byte[newByteArrayLength];
         
        if(index == bs.length - 1) {
            System.arraycopy(bs, 0, ret, 0, length);
        } else if(index < bs.length - 1) {
            for(int i = index; i < bs.length - length; i++) {
                bs[i] = bs[i + length];
            }
            
            System.arraycopy(bs, 0, ret, 0, newByteArrayLength);
        }
        return ret;
    }
    
    public static float byteArrayToFloat(byte[] bArray) {
        float ft =  Float.intBitsToFloat(heightByteBeforeArrayToInt(bArray));
        BigDecimal bd = BigDecimal.valueOf(ft);
        bd = bd.setScale(scale, roundingMode);
        return ft = bd.floatValue();
    }
    
    public static float iEEEIntegerToFloat(int sum) {
        float ft =  Float.intBitsToFloat(sum);
        BigDecimal bd = BigDecimal.valueOf(ft);
        bd = bd.setScale(scale, roundingMode);
        return ft = bd.floatValue();
    }
    
    public static byte[] intToIEEEByteArray(float f) {
        int num = floatToIEEEInt(f);
        return intToLowBeforeByteArray(num);
    }
    
    //浮点型数转转IEEE754字节数组所表示数
    public static int floatToIEEEInt(float ft) {
        return Float.floatToIntBits(ft);
    }
    
    //低位在前
    public static byte[] intToLowBeforeByteArray(int num) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (num & 0xFF);
        targets[1] = (byte) (num >> 8 & 0xFF);
        targets[2] = (byte) (num >> 16 & 0xFF);
        targets[3] = (byte) (num >> 24 & 0xFF);
        return targets;
    }
    
    //高位在前
    public static byte[] intToHeightBeforeByteArray(int num) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (num & 0xFF);
        targets[2] = (byte) (num >> 8 & 0xFF);
        targets[1] = (byte) (num >> 16 & 0xFF);
        targets[0] = (byte) (num >> 24 & 0xFF);
        return targets;
    }
    /**
     * 低字节在前的字节数据转整型
     * @param bytes
     * @return
     */
    public static int lowByteBeforeArrayToInt(byte[] bytes) {
        int value = 0;
        for(int i = 0; i < bytes.length; i ++) {
        int shift= i * 8; 
        value +=(bytes[i] & 0xFF) << shift;
        } 
        return value;
    }
}
