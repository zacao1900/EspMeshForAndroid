package iot.espressif.esp32.action.device;

import android.bluetooth.BluetoothDevice;

import iot.espressif.esp32.model.device.ble.MeshBlufiClient;

public interface IEspActionDeviceBatchBluFi {
    int CONNECTION_MAX = 5;

    interface MeshBlufiClientListener {
        void onClientCreated(MeshBlufiClient client);

        void onConnectResult(BluetoothDevice device, boolean connected);

        void addConnectIngDeviceAddress(String address);
    }

    void setMeshBlufiClientListener(MeshBlufiClientListener listener);

    void notifyNext();

    void execute();

    void close();

}
