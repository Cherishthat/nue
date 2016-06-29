package com.capipad.barcodebroadcast;

public class barcode {
	

	static public native int BarCodeInit(int type);

	static public native int BarCodeDeInit();
	

	static public native String BarCodeRead();

	static public native int BarCodeCheck();
	
	static {
		try {
			System.loadLibrary("BarCodeSys");
		} catch (Exception e) {
		}
	}
}