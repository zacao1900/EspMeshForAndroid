package h5.espressif.esp32.module.main;

import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.core.app.ActivityCompat;
//import androidx.core.location.LocationManagerCompat;
=======
>>>>>>> a4fb86ca0b573a475d669c038cb6b2543c5b0199

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

import h5.espressif.esp32.R;
import h5.espressif.esp32.module.web.JSCallbacks;

public class EspWebActivity extends AppCompatActivity {
    static final int REQUEST_PERMISSION_DEFAULT = 0x01;
    static final int REQUEST_PERMISSION_CAMERA = 0x02;
    static final int REQUEST_QRCODE = IntentIntegrator.REQUEST_CODE;

    public static final int REQUEST_WIFI = 0x03;
    public static final int REQUEST_BLUETOOTH = 0x04;
    public static final int REQUEST_LOCATION = 0x05;

    private MainHelper mMainHelper;
    private MainWebHelper mWebHelper;
    private MainBleNotifyThread mBleNotifyThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.esp_web_activity);

        mMainHelper = new MainHelper(this);
        getLifecycle().addObserver(mMainHelper);

        mWebHelper = new MainWebHelper(this);
        getLifecycle().addObserver(mWebHelper);

        getLifecycle().addObserver(new MainBroadcastReceiver(this));

        MainDeviceNotifier deviceNotifier = new MainDeviceNotifier(this);
        deviceNotifier.setListenSniffer(true);
        deviceNotifier.setListenStatus(true);
        deviceNotifier.setListenTopology(true);
        getLifecycle().addObserver(deviceNotifier);

        mBleNotifyThread = new MainBleNotifyThread(this);
        getLifecycle().addObserver(mBleNotifyThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_DEFAULT:
            case REQUEST_PERMISSION_CAMERA:
                mMainHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
            case REQUEST_WIFI:
            case REQUEST_LOCATION:
            case REQUEST_BLUETOOTH:
                mMainHelper.onActivityResult(requestCode, resultCode, data);
                return;
            default: {
                mWebHelper.onActivityResult(requestCode, resultCode, data);
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        evaluateJavascript(JSCallbacks.onBackPressed());
    }

<<<<<<< HEAD
    private void mkdirs() {
        String otaBinPath = EspActionDeviceOTA.getBinDirPath();
        if (otaBinPath != null) {
            File otaBinDir = new File(otaBinPath);
            if (!otaBinDir.exists()) {
                boolean result = otaBinDir.mkdirs();
                mLog.d("mkdirs otaBinPath " + result);
            }
        }
    }

    private void initWebView() {
        mWebForm = findViewById(R.id.web_form);
        mWebView = new WebView(getApplicationContext());
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(width, height);
        mWebForm.addView(mWebView, mlp);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (Objects.equals(uri.getHost(), Customer.INSTANCE.getHomeUrl())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setTextZoom(100);

        if (AppUtil.isPad(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mWebView.loadUrl(getUrl(FILE_PAD));
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            String file = mSharedPref.getString(KEY_WEB_FILE, FILE_PHONE);
            mWebView.loadUrl(getUrl(file));
        }
        mMeshApiForJS = new AppApiForJS(this);
        mWebView.addJavascriptInterface(mMeshApiForJS, AppApiForJS.NAME);

        mAliApiForJS = new AliApiForJS(getApplicationContext(), this::evaluateJavascript);
        mWebView.addJavascriptInterface(mAliApiForJS, AliApiForJS.NAME);
    }

    @Subscribe
    public void onWifiChanged(WifiChangedEvent event) {
        notifyWifiChanged();
    }

    @Subscribe
    public void onBluetoothChanged(BluetoothChangedEvent event) {
        notifyBluetoothChanged();
    }

    public void registerPhoneStateChange() {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        notifyWifiChanged();
        notifyBluetoothChanged();
    }

    public void hideCoverImage() {
        runOnUiThread(() -> mCoverIV.setVisibility(View.GONE));
    }

    public boolean isOTAing() {
        return mMeshApiForJS != null && mMeshApiForJS.isOTAing();
    }

    public boolean isLocationEnable() {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return true;
    }

=======
>>>>>>> a4fb86ca0b573a475d669c038cb6b2543c5b0199
    public void evaluateJavascript(String script) {
        runOnUiThread(() -> mWebHelper.evaluateJavascript(script));
    }

    public void startBleScan(List<ScanFilter> filters, ScanSettings settings) {
        mBleNotifyThread.startBleScan(filters, settings);
    }

    public void stopBleScan() {
        mBleNotifyThread.stopBleScan();
    }

    public void loadFile(String file) {
        mWebHelper.loadFile(file);
    }

    public void newWebView(String url) {
        mWebHelper.newWebView(url);
    }

    public void requestCameraPermission() {
        mMainHelper.requestCameraPermission();
    }

    public boolean isLocationEnable() {
        return mMainHelper.isLocationEnable();
    }

    public boolean isOTAing() {
        return mWebHelper.isOTAing();
    }

    public void clearBle() {
        mBleNotifyThread.clearBle();
    }

    public void hideCoverImage() {
        mWebHelper.hideCoverImage();
    }

    public void registerPhoneStateChange() {
        mMainHelper.registerPhoneStateChange();
    }
}
