package iot.espressif.esp32.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Random;

import libs.espressif.utils.RandomUtil;

public class EspApplication {
    private static EspApplication instance;
    private static Application applicationInstance;
    private final Object mCacheLock = new Object();
    private HashMap<String, Object> mCacheMap;
    private boolean mSupportBLE;
    private LocalBroadcastManager mBroadcastManager;

    public static EspApplication getEspApplication() {
        if (instance == null) {
            instance = new EspApplication();
        }
        return instance;
    }

    private void init() {
        if (instance == null) {
            instance = new EspApplication();
        }
        mBroadcastManager = LocalBroadcastManager.getInstance(applicationInstance);
        mCacheMap = new HashMap<>();

        mSupportBLE = applicationInstance.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);

//        MeshObjectBox.getInstance().init(applicationInstance);
    }

    private void release() {
//        MeshObjectBox.getInstance().close();

        mCacheMap.clear();
        mCacheMap = null;
    }

    public void putCache(String key, Object value) {
        synchronized (mCacheLock) {
            mCacheMap.put(key, value);
        }
    }

    /**
     * Save an object in app
     *
     * @param value the object will be saved
     * @return the key to used in #takeCache
     */
    public String putCache(Object value) {
        synchronized (mCacheLock) {
            String key;
            do {
                int keyLength = new Random().nextInt(20) + 20;
                key = RandomUtil.randomString(keyLength);
            } while (mCacheMap.containsKey(key));

            mCacheMap.put(key, value);
            return key;
        }
    }

    /**
     * Take the object and remove from app
     *
     * @param key the key generated when put
     * @return the target object
     */
    public Object takeCache(String key) {
        synchronized (mCacheLock) {
            Object result = mCacheMap.get(key);
            if (result != null) {
                mCacheMap.remove(key);
            }

            return result;
        }
    }

    public Object getCache(String key) {
        synchronized (mCacheLock) {
            return mCacheMap.get(key);
        }
    }

    /**
     * Check the Android device support Ble
     */
    public boolean hasBleFeature() {
        return mSupportBLE;
    }

    /**
     * Get the application default phone storage dir path
     */
    public String getEspRootSDPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/Espressif/Esp32";
    }

    public void sendLocalBroadcast(Intent intent) {
        mBroadcastManager.sendBroadcast(intent);
    }

    public void registerLocalReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        mBroadcastManager.registerReceiver(receiver, filter);
    }

    public void unregisterLocalReceiver(BroadcastReceiver receiver) {
        mBroadcastManager.unregisterReceiver(receiver);
    }
    public void setApplication(Application application) {
        applicationInstance = application;
        this.init();
    }
    public Context getApplicationContext() {
        return applicationInstance;
    }

}
