/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smartshell.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.smartshell.common.power;

 
/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
public class BluetoothConnectService {

    private static final String TAG = "BluetoothConnectService";
    private static final boolean D = true;

    private static final String NAME_SECURE = "BluetoothChatSecure";


    private static final UUID MY_UUID_SECURE =
        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Member fields
    private final BluetoothAdapter mAdapter;
   // private AcceptThread mSecureAcceptThread;
  //  private AcceptThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    public static final int STATE_DISCONN=4;  //

    public static final String ACTION_BROADCAST_BT_DATA = "action.broadcast.smartshell.data";
    

    public static final String EXTRA_DATA = "smartshell_data";
    
    private Context mContext;
    

    protected power mPower;
    
    private int startick,endtick;
    /**
     * Constructor. Prepares a new BluetoothChat session.
     * @param context  The UI Activity Context
     * @param handler  A Handler to send messages back to the UI Activity
     */
    public BluetoothConnectService(Context context) {
    	mContext = context;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        
        mPower = new power(mContext);
        
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Set the current state of the chat connection
     * @param state  An integer defining the current connection state
     */
    public synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
    }

    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
    /*
    public synchronized void start() {
        if (D) Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        setState(STATE_LISTEN);

        // Start the thread to listen on a BluetoothServerSocket
        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread(true);
            mSecureAcceptThread.start();
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread(false);
            mInsecureAcceptThread.start();
        }
    }
*/
    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * @param device  The BluetoothDevice to connect
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    public synchronized void connect(BluetoothDevice device, boolean secure) {
        if (D) Log.d(TAG, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        
        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device, secure);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device, final String socketType) {
        if (D) Log.d(TAG, "connected, Socket Type:" + socketType);

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Cancel the accept thread because we only want to connect to one device
        /*
        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }*/

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity

        setState(STATE_CONNECTED);
        
        startick= mPower.getick();
        if (D) Log.d(TAG, "App.istartick=" + startick);
        //���ӳɹ�����ע��notification
        showNotification();
        
    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
        if (D) Log.d(TAG, "stop");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
/*
        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }

        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }
        */
        setState(STATE_NONE);
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
    	Log.e(TAG, "connectionFailed");
        // Start the service over to restart listening mode
//        BluetoothConnectService.this.start();
    	hideNotification();
    	setState(STATE_DISCONN);
    	
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    public void connectionLost() {
    	Log.e(TAG, "connectionLost");
    	hideNotification();
        // Start the service over to restart listening mode
//        BluetoothConnectService.this.start();
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    /*
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;
        private String mSocketType;

        public AcceptThread(boolean secure) {
            BluetoothServerSocket tmp = null;
            mSocketType = secure ? "Secure":"Insecure";

            // Create a new listening server socket
            try {
                if (secure) {
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE,
                        MY_UUID_SECURE);
                } 
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            if (D) Log.d(TAG, "Socket Type: " + mSocketType +
                    "BEGIN mAcceptThread" + this);
            setName("AcceptThread" + mSocketType);

            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "Socket Type: " + mSocketType + "accept() failed", e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothConnectService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
                            connected(socket, socket.getRemoteDevice(),
                                    mSocketType);
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
                                socket.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread, socket Type: " + mSocketType);

        }

        public void cancel() {
            if (D) Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
            }
        }
    }
*/

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            
            
            
            if (secure) {
			    Log.i(TAG, "BEGIN createRfcommSocketToServiceRecord");
			//    tmp = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
			    
			    
	
			    Method m;
			    try {
			        m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
			        try {
			            tmp = (BluetoothSocket) m.invoke(device, 1);
			        } catch (IllegalArgumentException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        } catch (IllegalAccessException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        } catch (InvocationTargetException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        }
			    } catch (SecurityException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (NoSuchMethodException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }   
			    
            
                 
			}
            
            
            
            /*
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(
                            MY_UUID_SECURE);
                } 
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
            }
            */
            
            mmSocket = tmp;
                      	
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            if (mAdapter.isDiscovering()) {
            mAdapter.cancelDiscovery();
            }

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
               // Log.e(TAG, "connectionFailed  before " );
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                   // Log.e(TAG, "unable to close() " + mSocketType +
                   //         " socket during connection failure", e2);
                }
                Log.e(TAG, "connectionFailed" + mSocketType +
                        " socket during connection failure", e);
                connectionFailed();
             //   Log.e(TAG, "connectionFailed  after " );
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothConnectService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
                Log.e(TAG, "close() of connect " + mSocketType + " socket ok");
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            byte[] items = new byte[1024];
            int lenth;
        	String result; 
        	
            // Keep listening to the InputStream while connected
            while (true) {
                try {
                result="";
                    // Read from the InputStream
                    lenth = mmInStream.read(buffer);
 
                    if (lenth > 0) { 
                    	endtick=mPower.getick();
                       if(endtick-startick>300)
                  {
    					//for(int i=0;i<lenth;i++)
    					//	Log.e(TAG, "data is %d"+buffer[i]);

                   	   if(false)
                    	   {
                   		   
           				byte[] datas = new byte[]{
        						(byte)4D,(byte)0x45,(byte)0x43,(byte)0x41,(byte)0x52,(byte)0x44,(byte)0x3A,(byte)0x4E,(byte)0x3A,(byte)0xE9,
        						(byte)0x83,(byte)0xAD,(byte)0xE5,(byte)0xBF,(byte)0xB5,(byte)0xE5,(byte)0x90,(byte)0x9B,(byte)0x3B,(byte)0x4F,
        						(byte)0x52,(byte)0x47,(byte)0x3A,(byte)0xE4,(byte)0xB8,(byte)0xAD,(byte)0xE5,(byte)0x9B,(byte)0xBD,(byte)0xE7,
        						(byte)0x94,(byte)0xB5,(byte)0xE4,(byte)0xBF,(byte)0xA1,(byte)0xE6,(byte)0xBB,(byte)0xA8,(byte)0xE5,(byte)0xB7,
        						(byte)0x9E,(byte)0xE5,(byte)0x88,(byte)0x86,(byte)0xE5,(byte)0x85,(byte)0xAC,(byte)0xE5,(byte)0x8F,(byte)0xB8,
        						(byte)0xE6,(byte)0x94,(byte)0xBF,(byte)0xE4,(byte)0xBC,(byte)0x81,(byte)0xE5,(byte)0xAE,(byte)0xA2,(byte)0xE6,
        						(byte)0x88,(byte)0xB7,(byte)0xE4,(byte)0xBA,(byte)0x8B,(byte)0xE4,(byte)0xB8,(byte)0x9A,(byte)0xE9,(byte)0x83,
        						(byte)0xA8,(byte)0x3B,(byte)0x54,(byte)0x49,(byte)0x4C,(byte)0x3A,(byte)0xE6,(byte)0x80,(byte)0xBB,(byte)0xE7,
        						(byte)0xBB,(byte)0x8F,(byte)0xE7,(byte)0x90,(byte)0x86,(byte)0x3B,(byte)0x54,(byte)0x45,(byte)0x4C,(byte)0x3A,
        						(byte)0x31,(byte)0x38,(byte)0x39,(byte)0x30,(byte)0x35,(byte)0x34,(byte)0x33,(byte)0x36,(byte)0x36,(byte)0x36,
        						(byte)0x36,(byte)0x3B,(byte)0x55,(byte)0x52,(byte)0x4C,(byte)0x3A,(byte)0x77,(byte)0x77,(byte)0x77,(byte)0x2E,
        						(byte)0x62,(byte)0x7A,(byte)0x74,(byte)0x65,(byte)0x6C,(byte)0x65,(byte)0x2E,(byte)0x63,(byte)0x6F,(byte)0x6D,
        						(byte)0x3B,(byte)0x45,(byte)0x4D,(byte)0x41,(byte)0x49,(byte)0x4C,(byte)0x3A,(byte)0x31,(byte)0x38,(byte)0x39,
        						(byte)0x30,(byte)0x35,(byte)0x34,(byte)0x33,(byte)0x36,(byte)0x36,(byte)0x36,(byte)0x36,(byte)0x40,(byte)0x31,
        						(byte)0x38,(byte)0x39,(byte)0x2E,(byte)0x63,(byte)0x6E,(byte)0x3B,(byte)0x41,(byte)0x44,(byte)0x52,(byte)0x3A,
        						(byte)0xE5,(byte)0xB1,(byte)0xB1,(byte)0xE4,(byte)0xB8,(byte)0x9C,(byte)0xE6,(byte)0xBB,(byte)0xA8,(byte)0xE5,
        						(byte)0xB7,(byte)0x9E,(byte)0xE9,(byte)0xBB,(byte)0x84,(byte)0xE6,(byte)0xB2,(byte)0xB3,(byte)0xE4,(byte)0xBA,
        						(byte)0x94,(byte)0xE8,(byte)0xB7,(byte)0xAF,(byte)0xE6,(byte)0xB8,(byte)0xA4,(byte)0xE6,(byte)0xB5,(byte)0xB7,
        						(byte)0xE5,(byte)0x8D,(byte)0x81,(byte)0xE4,(byte)0xB9,(byte)0x9D,(byte)0xE8,(byte)0xB7,(byte)0xAF,(byte)0xE7,
        						(byte)0x94,(byte)0xB5,(byte)0xE4,(byte)0xBF,(byte)0xA1,(byte)0xE5,(byte)0xA4,(byte)0xA7,(byte)0xE6,(byte)0xA5,
        						(byte)0xBC,(byte)0x3B,(byte)0x3B,(byte)0x0D,(byte)0x0D,
        						};				
           			
						result=mPower.tbardatachieve(buffer, lenth);
                    		   //moto��ά�����
   		                 //   Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
   		                 //   readData.putExtra("2barcode",datas);
   		                  //  mContext.sendBroadcast(readData);
						if(result!=null)
						{
	                    Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
	                    readData.putExtra(EXTRA_DATA, result);
	                    mContext.sendBroadcast(readData);
	                    Log.i(TAG, "BARCODE is " + result);
   		                    Log.i(TAG, "BARCODE is " + result);    
						}
                     		    
                    	   }
                    	   else
                    	   {
                    	   //���´�����������ʱһ�·���N������
                    	int itemsnum=0;
    					for(int ii=0;ii<lenth;ii++)
    						if(buffer[ii]==0x0a)
    							itemsnum++;
    					

                    	//�������
						result=mPower.bardatachieve(buffer, lenth);
						if(result!=null)
						{
							result=result.replace("\r","");
							result=result.replace("\n",";!@#;");
		                     String[] splitresult=result.split(";!@#;"); 
		                     
		                     if(splitresult.length>1) 
				                    Log.i(TAG, "wwww BARCODE is more");                    	 
		                     
		                     for(int tmp=0;tmp<splitresult.length;tmp++)
		                     {
		                    Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
		                    if(tmp==0)
		                    readData.putExtra(EXTRA_DATA, splitresult[tmp]);
		                    else
			                readData.putExtra(EXTRA_DATA, "barcode:"+splitresult[tmp]);		
		                    
		                    mContext.sendBroadcast(readData);
		                    Log.i(TAG, "BARCODE is " + splitresult[tmp]);
		                     }
						} 
						
						//���RFID
						result=mPower.rfiddatachieve(buffer, lenth);
						if(result!=null)
						{  
		                     
		                    Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
		                    readData.putExtra(EXTRA_DATA, result);
		                    mContext.sendBroadcast(readData);
		                    Log.i(TAG, "BARCODE is " + result);
						}
						
						
						//���MOTO ��ά��
						result=mPower.tbardatachieve(buffer, lenth);
					if(result!=null)
					{
                 Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
                 readData.putExtra(EXTRA_DATA, result);
                 mContext.sendBroadcast(readData);
                 Log.i(TAG, "TBARCODE is " + result);
	                    Log.i(TAG, "TBARCODE is " + result);    
					}						
                    	   }
						
						/*
    					}
    					else   //�ж���������Ϣ
    					{
		                    Log.i(TAG, "wwwwwBARCODE is more than one " );
    						int curpos=0,curitem=0,iii,itemlen;
    						for(curitem=0;curitem<=itemsnum;curitem++)
    						{   
    							iii=0;
    							itemlen=0;
    							while(buffer[curpos]!=0x0a&&curpos<lenth)
    							{
    							    if(buffer[curpos]!=0x0d)
    							    {
    								items[iii]=buffer[curpos];
    							    itemlen++;
    							    }
    							    curpos++;
    							}
 
    	                    	//�������
    							result=mPower.bardatachieve(items, itemlen);
    							if(result!=null)
    							{
    			                     
    			                    Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
    			                    readData.putExtra(EXTRA_DATA, result);
    			                    mContext.sendBroadcast(readData);
    			                    Log.i(TAG, "BARCODE is " + result);
    							} 
    							//���RFID
    							result=mPower.rfiddatachieve(items, itemlen);
    							if(result!=null)
    							{
    			                    
    			                    Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
    			                    readData.putExtra(EXTRA_DATA, result);
    			                    mContext.sendBroadcast(readData);
    			                    Log.i(TAG, "BARCODE is " + result);
    							}
                                   }   //end for
    						
    					}
    					*/
					} 
                    
                    
                    //�յ���ݷ��͸�����Ӧ�ó��������������ݺͷ��㲥���ƿ��Ըģ�
                    //�����յ�ָ�����Ⱥ󷢣����ڷ��㲥ֱ�����ַ������������������������������ʵ�������������
                    
                    //����ĳɴ�����ɣ����������ٲ�������
                  //  byte[] data = new byte[lenth];
                  //  System.arraycopy(buffer, 0, data, 0, lenth);

                    
                    
       //             Intent readData = new Intent(ACTION_BROADCAST_BT_DATA);
       //             readData.putExtra(EXTRA_DATA, data);
       //             mContext.sendBroadcast(readData);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "disconnected!!", e);
                    connectionLost();
                    setState(STATE_DISCONN);
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
  //֪ͨ����
  	private NotificationManager mNotificationManager;
  	
  	private static final int ID_LOGO  = 0X1;
  	
    private void showNotification() {
		Intent showLogoIntent = new Intent(mContext, mContext.getClass());
		PendingIntent mLogoNotifyIntent = PendingIntent.getActivity(mContext, ID_LOGO, showLogoIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Notification showLogoNotification = new Notification(CommonUtils.getResId(mContext, "drawable.smartshell"), "", 0);
		showLogoNotification.setLatestEventInfo(mContext, "smartshell", "",
				mLogoNotifyIntent);
		showLogoNotification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
		mNotificationManager.notify(ID_LOGO, showLogoNotification);
	}
	
	private void hideNotification() {
		mNotificationManager.cancel(ID_LOGO);
	}
}
