package com.xumu.programmer.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Arrays;

import com.xumu.programmer.util.ByteArrayParser;

public class DeoEquipment {
    
    private int id;
    private Timestamp time;
    private float n01;
    private float n0;
    private float n1;
    private float n2;
    private float n3;
    private float n4;
    private float n5;
    private float n6;
    private int n20;
    private int n21;
    private int n22;
    private int n23;
    private int n24;
    private int n25;
    private int n26;
    private int n27;
    private int n28;
    private int n29;
    private int n30;
    private int n31;
    private int n32;
    private int n33;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time2) {
        this.time = time2;
    }
    public float getN01() {
        return n01;
    }
    public void setN01(int n01) {
        this.n01 = n01;
    }
    public float getN0() {
        return n0;
    }
    public void setN0(int n0) {
        this.n0 = n0;
    }
    public float getN1() {
        return n1;
    }
    public void setN1(int n1) {
        this.n1 = n1;
    }
    public float getN2() {
        return n2;
    }
    public void setN2(int n2) {
        this.n2 = n2;
    }
    public float getN3() {
        return n3;
    }
    public void setN3(int n3) {
        this.n3 = n3;
    }
    public float getN4() {
        return n4;
    }
    public void setN4(int n4) {
        this.n4 = n4;
    }
    public float getN5() {
        return n5;
    }
    public void setN5(int n5) {
        this.n5 = n5;
    }
    public float getN6() {
        return n6;
    }
    public void setN6(int n6) {
        this.n6 = n6;
    }
    public int getN20() {
        return n20;
    }
    public void setN20(int n20) {
        this.n20 = n20;
    }
    public int getN21() {
        return n21;
    }
    public void setN21(int n21) {
        this.n21 = n21;
    }
    public int getN22() {
        return n22;
    }
    public void setN22(int n22) {
        this.n22 = n22;
    }
    public int getN23() {
        return n23;
    }
    public void setN23(int n23) {
        this.n23 = n23;
    }
    public int getN24() {
        return n24;
    }
    public void setN24(int n24) {
        this.n24 = n24;
    }
    public int getN25() {
        return n25;
    }
    public void setN25(int n25) {
        this.n25 = n25;
    }
    public int getN26() {
        return n26;
    }
    public void setN26(int n26) {
        this.n26 = n26;
    }
    public int getN28() {
        return n28;
    }
    public void setN28(int n28) {
        this.n28 = n28;
    }
    public int getN29() {
        return n29;
    }
    public void setN29(int n29) {
        this.n29 = n29;
    }
    public int getN30() {
        return n30;
    }
    public void setN30(int n30) {
        this.n30 = n30;
    }
    public int getN31() {
        return n31;
    }
    public void setN31(int n31) {
        this.n31 = n31;
    }
    public int getN32() {
        return n32;
    }
    public void setN32(int n32) {
        this.n32 = n32;
    }
    public int getN33() {
        return n33;
    }
    public void setN33(int n33) {
        this.n33 = n33;
    }
    
    public int getN27() {
		return n27;
	}
	public void setN27(int n27) {
		this.n27 = n27;
	}
	public byte[] getCommand(DeoEquipment this, String type) {
        byte[] command = new byte[96];
        System.out.println("√¸¡Óƒ⁄»›:" + Arrays.toString(command));
        return command;
    }
    
    public void update(String type, int status) {
        Class thisClass = this.getClass();
        try {
			Field field = thisClass.getDeclaredField(type);
            field.setAccessible(true);
            field.setInt(this, status);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    }
    
}
