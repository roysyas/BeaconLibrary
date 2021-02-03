package io.quadrant.beaconlib;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

public class BluetoothUtil {

    private final Context context;
    private final BluetoothAdapter bluetoothAdapter;
    public static final int BLUETOOTH_REQUEST = 1004;

    public BluetoothUtil(Context context){
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void checkBluetooth(onBluetoothListener onBluetoothListener){
        if(!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity)context).startActivityForResult(intent, BLUETOOTH_REQUEST);
        }else {
            if(onBluetoothListener!=null){
                onBluetoothListener.bluetoothStatus(true);
            }
        }
    }

    public interface onBluetoothListener {
        void bluetoothStatus(boolean isBluetoothEnable);
    }
}
