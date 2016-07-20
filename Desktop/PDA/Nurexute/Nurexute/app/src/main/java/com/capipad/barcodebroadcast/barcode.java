package com.capipad.barcodebroadcast;

public class barcode {
	
	/**
	 * 功能描述：扫描头上电
	 * 参数：1初始化一维 2初始化二维
	 * 返回：成功返回1**/
	static public native int BarCodeInit(int type);
	
	/**
	 * 功能描述：扫描头关闭
	 * 参数：无
	 * 返回：成功返回1**/
	static public native int BarCodeDeInit();
	
	/**
	 * 功能描述：使扫描头初始工作状态，获取条码信息
	 * 参数：无
	 * 返回：成功返回扫描数据，失败没有数据返回**/
	static public native String BarCodeRead();
	/*
	 * 功能描述：检查扫描头类型
	 * 参数：无
	 * 返回：成功返回1，
	 */
	static public native int BarCodeCheck();
	
	static {
		try {
			System.loadLibrary("BarCodeSys");
		} catch (Exception e) {
		}
	}
}