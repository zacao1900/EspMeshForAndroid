package h5.espressif.esp32.module;

import android.app.Application;

import androidx.multidex.MultiDex;

import aliyun.espressif.mesh.AliInitialize;
import iot.espressif.esp32.app.EspApplication;

public class MeshApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //MultiDex.install(this);

//        AliInitialize.initAliyun(this);
    }
}
