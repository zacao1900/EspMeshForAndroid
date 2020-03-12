package iot.espressif.esp32.model.device.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;

import libs.espressif.ble.ScanListener;

public abstract class MeshScanListener implements ScanListener {
    abstract public void onMeshDeviceScanned(MeshBleDevice meshBleDevice);

    private int mManufacturerID = 0;

    public void setManufacturerIDFilter(int manufacturerID) {
        mManufacturerID = manufacturerID;
    }

    @Override
    public void onLeScan(ScanResult scanResult) {
        if (scanResult.getScanRecord() == null) {
            return;
        }
        BluetoothDevice device = scanResult.getDevice();
        int rssi = scanResult.getRssi();
        byte[] scanRecord = scanResult.getScanRecord().getBytes();
        MeshBleDevice meshBleDevice = new MeshBleDevice(device, rssi, scanRecord, mManufacturerID);
//        if (meshBleDevice.getMeshVersion() >= 0) {
        //再app端必需加 ScanFilter 无需判断meshversion 和tid
            onMeshDeviceScanned(meshBleDevice);
//        }
    }
}
