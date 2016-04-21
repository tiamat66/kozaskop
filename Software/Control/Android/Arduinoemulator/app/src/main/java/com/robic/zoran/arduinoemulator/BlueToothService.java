package com.robic.zoran.arduinoemulator;

/**
 * Created by zoran on 14.3.2016.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BlueToothService {

    private static final String TAG = "bluetooth1";

    // Delimiters
    private static final String SM = "<";
    private static final String EM = ">";

    //Messages
    //IN
    private static final String MOVE = "MV";
    private static final String ST = "ST?";
    private static final String BTRY = "BTRY?";

    //OUT
    private static final String RDY = "RDY";
    private static final String NOT_RDY = "NOT_RDY";
    private static final String BTRY_RES = "BTRY";


    // SDP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String NAME = "MOONSTALKER";
    // Status  for Handler
    private static final int RECIEVE_MESSAGE = 1;
    private static final int CONNECTION_CANCELED_MESSAGE = 2;
    private BluetoothAdapter btAdapter = null;
    private MainActivity mainActivity;
    private AcceptThread acceptThread;
    private BtReadWrite btReadWrite;
    Handler h;
    String rcvdMsg;
    String outMessage;
    boolean connectionCanceled = true;
    BluetoothSocket socket = null;

    public BlueToothService(final MainActivity myMainActivity) {

        mainActivity = myMainActivity;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        acceptThread = new AcceptThread();

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;
                        rcvdMsg = new String(readBuf, 0, msg.arg1);
                        Log.d(TAG, "Message received from Client:");
                        Log.d(TAG, rcvdMsg);
                        myMainActivity.print(rcvdMsg);
                        processMsg(rcvdMsg);
                        break;
                    case CONNECTION_CANCELED_MESSAGE:
                        connectionCanceled = true;
                        Log.d(TAG, "...Connection Canceled...");
                        socket = null;
                        break;
                }
            };
        };

        checkBTState();
    }

    private void checkBTState() {

        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (btAdapter == null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mainActivity.startActivityForResult(enableBtIntent, 1);
            }

        }
    }

    private void errorExit(String title, String message) {

        Log.d(TAG, title + " - " + message);
        mainActivity.finish();
    }

    public void getPairedDevices() {

        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                Log.d(TAG, device.getName() + "\n" + device.getAddress());
            }
        }
    }

    // This acts as Server
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = btAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException ignored) { }
            mmServerSocket = tmp;
        }

        public void run() {
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    if(!connectionCanceled) continue;
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    btReadWrite = new BtReadWrite(socket);
                    btReadWrite.start();
                    Log.d(TAG, "...Connection was accepted...");
                    connectionCanceled = false;
                    rdy();
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException ignored) { }
        }
    }

    private class BtReadWrite extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public BtReadWrite(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException ignored) { }

            mmInStream = tmpIn;
            if(mmInStream != null) {
                Log.d(TAG, "...In Stream OK...");
            }
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                if(connectionCanceled) break;
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    Log.d(TAG, "...Readed" + bytes + " bytes...");
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    h.obtainMessage(CONNECTION_CANCELED_MESSAGE).sendToTarget();
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                h.obtainMessage(CONNECTION_CANCELED_MESSAGE).sendToTarget();
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }

    public void write(String msg) {

        if(btReadWrite != null) {
            btReadWrite.write(msg);
            Log.d(TAG, "Message sent to Client: " + msg);
        }
    }

    public void startBtServer() {

        acceptThread.start();
        Log.d(TAG, "...Bluetooth Server Started...");
    }

    private void processMsg(String msg) {

        //MV
        if(chkMsg(msg, MOVE)) {

            Log.d(TAG, "Process MV message from Client)");
            rdy();
            return;
        }

        //ST
        if(chkMsg(msg, ST)) {

            Log.d(TAG, "Process STATUS message from Client)");
            notRdy();
            return;
        }

        if(chkMsg(msg, BTRY)) {

            Log.d(TAG, "Process STATUS message from Client)");
            btryRes();
            return;
        }

        Log.d(TAG, "Unknown message received from Arduino");
    }

    private boolean chkMsg(String recMsg, String expMsg)
    {

        recMsg = recMsg.substring(1, 1+expMsg.length());
        return(recMsg.equals(expMsg));
    }

    private void rdy() {

        // send <RDY>
        outMessage = SM +
                RDY +
                EM;
        Log.d(TAG, outMessage);
        write(outMessage);
    }

    private void notRdy() {

        // send <NOT_RDY>
        outMessage = SM +
                NOT_RDY +
                EM;
        Log.d(TAG, outMessage);
        write(outMessage);
    }

    private void btryRes() {

        // send <BTRY>
        outMessage = SM +
                BTRY_RES + " 11.4V" +
                EM;
        Log.d(TAG, outMessage);
        write(outMessage);
    }
}