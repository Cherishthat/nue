package com.capipad.barcodebroadcast;

public class barcode {
	
	/**
	 * ����������ɨ��ͷ�ϵ�
	 * ������1��ʼ��һά 2��ʼ����ά
	 * ���أ��ɹ�����1**/
	static public native int BarCodeInit(int type);
	
	/**
	 * ����������ɨ��ͷ�ر�
	 * ��������
	 * ���أ��ɹ�����1**/
	static public native int BarCodeDeInit();
	
	/**
	 * ����������ʹɨ��ͷ��ʼ����״̬����ȡ������Ϣ
	 * ��������
	 * ���أ��ɹ�����ɨ�����ݣ�ʧ��û�����ݷ���**/
	static public native String BarCodeRead();
	/*
	 * �������������ɨ��ͷ����
	 * ��������
	 * ���أ��ɹ�����1��
	 */
	static public native int BarCodeCheck();
	
	static {
		try {
			System.loadLibrary("BarCodeSys");
		} catch (Exception e) {
		}
	}
}