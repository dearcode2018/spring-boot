package com.hua.controller;

import lombok.Data;

import java.awt.print.PrinterGraphics;
import java.io.Serializable;

/**
 * @Description:  
 * @author qianye.zheng
 * Abc
 */
@Data
public class Abc implements Serializable {
    private static final long serialVersionUID = 4425118033089877450L;

    //private static final
    private  int a;

    private int a_1;

     /*
      * @description 
      * @author qianye.zheng
      * @param 
      * @return 
      */
    public Abc(final int a) {
        System.out.println();
        this.a = a;
    }

    public static void main(String[] args) {
        String str = null;
        System.out.println(str);
    }
}
