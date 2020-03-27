package iot.espressif.esp32.model.device.ble;

import android.bluetooth.BluetoothDevice;

import java.util.Arrays;
import java.util.List;

import libs.espressif.ble.BleAdvData;
import libs.espressif.ble.EspBleUtils;

public class MeshBleDevice implements IMeshBleDevice {
    private static final int BLE_ADV_TYPE_MANUFACTURER = 0xff;

    private static final byte[] OUI_MDF_BYTES = OUI_MDF.getBytes();
    private static final byte[] OUI_MGW_BYTES = OUI_MGW.getBytes();
    private static final byte[] OUI_ALI_BYTES = OUI_ALI.getBytes();

    private int mManufacturerId = 0;

    private BluetoothDevice mDevice = null;
    private int mRssi = 1;
    private byte[] mScanRecord = null;

    private String mStaBssid = null;
    private int mMeshVersion = -1;
    private boolean mOnlyBeacon = false;
    private int mTid = -1;

    private String mOUI;

    private String CID;//新加的适配北京平台的字段
    private String MID;
    private String PID;
    private String ability;
    private String BleProVersion;

    public MeshBleDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
        this(device, rssi, scanRecord, 0);
    }

    public MeshBleDevice(BluetoothDevice device, int rssi, byte[] scanRecord, int manufacturerId) {
        mDevice = device;
        mRssi = rssi;
        mScanRecord = scanRecord;
        mManufacturerId = manufacturerId;
        parseMesh();
    }

    @Override
    public void setDevice(BluetoothDevice device) {
        mDevice = device;
    }

    @Override
    public BluetoothDevice getDevice() {
        return mDevice;
    }

    @Override
    public void setRssi(int rssi) {
        mRssi = rssi;
    }

    @Override
    public int getRssi() {
        return mRssi;
    }

    @Override
    public void setScanRecord(byte[] scanRecord) {
        mScanRecord = scanRecord;
        parseMesh();
    }

    @Override
    public byte[] getScanRecord() {
        return mScanRecord;
    }

    @Override
    public String getStaBssid() {
        return mStaBssid;
    }

    @Override
    public int getMeshVersion() {
        return mMeshVersion;
    }

    @Override
    public boolean isOnlyBeacon() {
        return mOnlyBeacon;
    }

    @Override
    public int getTid() {
        return mTid;
    }

    @Override
    public void setManufacturerId(int manufacturerId) {
        mManufacturerId = manufacturerId;
        parseMesh();
    }

    @Override
    public int getManufacturerId() {
        return mManufacturerId;
    }

    @Override
    public String getOUI() {
        return mOUI;
    }

    private void initVars() {
        mMeshVersion = -1;
        mOnlyBeacon = false;
        mStaBssid = null;
        mTid = -1;
    }

    private void parseMesh() {
        try {
//            _parseMeshVersion();
            parseMeshVersion();
        } catch (Exception e) {
            e.printStackTrace();
            initVars();
        }
    }

    /**
     * 新加的
     */
    private void parseMeshVersion(){
        List<BleAdvData> dataList = EspBleUtils.resolveScanRecord(mScanRecord);
        for (BleAdvData advData : dataList) {
            byte[] manuData = advData.getData();
            if(manuData.length<12){
                continue;
            }
            CID= byte2Hex(manuData[1])+byte2Hex(manuData[0]);
            MID=byte2Hex(manuData[2]);
            PID=byte2Hex(manuData[3]);
            mStaBssid= byte2Hex(manuData[4])+byte2Hex(manuData[5])+byte2Hex(manuData[6])+byte2Hex(manuData[7])+byte2Hex(manuData[8])+byte2Hex(manuData[9]);
            ability=byte2Hex(manuData[10]);
            BleProVersion=byte2Hex(manuData[11]);
        }
    }
    private String byte2Hex(byte data){
        String str=Integer.toHexString(data<0?data&0xff:data);
       return str.length()<2?"0"+str:str;
    }

    private void _parseMeshVersion() {
        List<BleAdvData> dataList = EspBleUtils.resolveScanRecord(mScanRecord);
        for (BleAdvData advData : dataList) {
            // Check manufacturer adv type(0xff)
            if (advData.getType() != BLE_ADV_TYPE_MANUFACTURER) {
                continue;
            }

            byte[] manuData = advData.getData();
            if (manuData.length < 5) {
                continue;
            }
            // Check manufacturer id
            int advManuId = (manuData[0] & 0xff) | ((manuData[1] & 0xff) << 8);
            if (mManufacturerId != 0 && advManuId != mManufacturerId) {
                continue;
            }
            // Check OUI
            byte[] oui = {manuData[2], manuData[3], manuData[4]};
            if (Arrays.equals(oui, OUI_MDF_BYTES) || Arrays.equals(oui, OUI_ALI_BYTES)) {
                if (manuData.length != 14) {
                    continue;
                }
                mOUI = new String(oui);
                mMeshVersion = manuData[5] & 0b11;
                mOnlyBeacon = (manuData[5] & 0b10000) != 0;
                mStaBssid = String.format("%02x%02x%02x%02x%02x%02x",
                        manuData[6], manuData[7], manuData[8], manuData[9], manuData[10], manuData[11]);
                mTid = (manuData[12] & 0xff) | ((manuData[13] & 0xff) << 8);
                return;
            } else if (Arrays.equals(oui, OUI_MGW_BYTES)) {
                if (manuData.length != 12) {
                    continue;
                }
                mOUI = new String(oui);
                mMeshVersion = manuData[5] & 0b11;
                mStaBssid = String.format("%02x%02x%02x%02x%02x%02x",
                        manuData[6], manuData[7], manuData[8], manuData[9], manuData[10], manuData[11]);
                return;
            }
        }

        initVars();
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getBleProVersion() {
        return BleProVersion;
    }

    public void setBleProVersion(String bleProVersion) {
        BleProVersion = bleProVersion;
    }
}
