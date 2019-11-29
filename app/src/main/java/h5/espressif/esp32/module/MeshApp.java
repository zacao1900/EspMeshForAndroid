package h5.espressif.esp32.module;

import android.app.Application;
<<<<<<< HEAD
=======
import android.util.Log;
>>>>>>> a4fb86ca0b573a475d669c038cb6b2543c5b0199

import androidx.multidex.MultiDex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import iot.espressif.esp32.app.EspApplication;

<<<<<<< HEAD
public class MeshApp extends Application {
=======
public class MeshApp extends EspApplication {
    private static final String TAG = "MeshApp";
>>>>>>> a4fb86ca0b573a475d669c038cb6b2543c5b0199

    @Override
    public void onCreate() {
        super.onCreate();
        //MultiDex.install(this);

        try {
            Class<?> aliInitCls = Class.forName("aliyun.espressif.mesh.AliInitialize");
            Method initMethod = aliInitCls.getMethod("initAliyun", Application.class);
            initMethod.invoke(aliInitCls, this);
            Log.d(TAG, "Init Aliyun suc");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.w(TAG, "Init Aliyun failed");
        }
    }
}
