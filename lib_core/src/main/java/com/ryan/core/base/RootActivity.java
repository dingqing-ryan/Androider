package com.ryan.core.base;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.ryan.core.helper.Listener;
import com.ryan.core.utils.permission.PermissionsUtils;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import static com.ryan.core.utils.ButtonUtils.isFastDoubleClick;



/**
 * @Description: 最終的根acitvity
 * @Author: Ryan
 * @CreateDate: 2020/8/6 12:01
 */
public class RootActivity extends RxAppCompatActivity {

    /**
     * 当statusbar颜色需要为白色的时候，需要设置，默认不是白色
     * true:白色 false:其他颜色
     */
    protected boolean isStatusBarWhite;

    protected RxAppCompatActivity mContext;
    private final int ACCESS_FINE_LOCATION_CODE = 2;//申请获得定位权限
    private final int ACTION_LOCATION_SOURCE_SETTINGS_CODE = 3; //打开GPS设置界面
    private int orientation;
    protected FrameLayout mLayoutContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //固定屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        orientation = mConfiguration.orientation; //获取屏幕方向
        mContext = this;
    }


    /**
     * 查看是否打开GPS  跳转设置GPS
     */
    public void isOpenGPS() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 转到手机设置界面，用户设置GPS
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, ACTION_LOCATION_SOURCE_SETTINGS_CODE); // 设置完成后返回到原来的界面
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_CODE:
                //定位权限   加设置GPS
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请权限可获得位置权限成功了
                    isOpenGPS();
                } else {
                    Toast.makeText(getBaseContext(),"申请失败,可能导致定位不准确",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



    //************************************** Activity跳转(兼容4.4) **************************************//

    /**
     * Activity跳转
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivity(Class<?> clz) {
        if (isFastDoubleClick()) {
            return;
        }
        startActivity(new Intent(this, clz));
    }


    /**
     * Activity携带数据的跳转
     *
     * @param clz    要跳转的Activity的类名
     * @param bundle bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转(带动画)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz) {
        if (isFastDoubleClick()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(this, clz), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(clz);
        }
    }

    /**
     * Activity跳转(共享元素动画)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz, View view, String shareView) {
        startActivityAnimation(clz, view, shareView, null);
    }

    /**
     * Activity跳转(共享元素动画,带Bundle数据)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz, View view, String shareView, Bundle bundle) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, shareView).toBundle());
        } else {
            startActivity(intent);
        }
    }

    /**
     * Activity跳转(带动画,带Bundle数据)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz, Bundle bundle) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }


    /**
     * 通过Class打开编辑界面
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 有动画的Finish掉界面
     */
    public void AnimationFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }


    /**
     * 跳转公共的一个ContainerActivity 用来显示Fragment
     *
     * @param canonicalName 通过 Fragment.class.getCanonicalName()获取
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 通过 Fragment.class.getCanonicalName()获取
     * @param bundle
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }


    //************************************** Activity跳转 **************************************//


    /**
     * 8.0需要校验安装未知源权限
     */
    public void canInstallAPK(Listener listener) {
        boolean hasInstallPerssion = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            hasInstallPerssion = getPackageManager().canRequestPackageInstalls();
        }
        if (hasInstallPerssion) {
            //去下载安装应用
            listener.onResult();
        } else {
            //跳转至“安装未知应用”权限界面，引导用户开启权限，可以在onActivityResult中接收权限的开启结果
            this.listener = listener;
            Uri packageURI = Uri.parse("package:" + RootActivity.this.getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            RootActivity.this.startActivityForResult(intent, 0x33);
//            showDialogBysure("应用安装", "更新app需要您开启安装权限", new Listener() {
//                @Override
//                public void onResult() {
//
//                }
//            }).setCancelable(true);
        }
    }

    Listener listener;

    //接收“安装未知应用”权限的开启结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x33) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && resultCode == RESULT_OK) {
                listener.onResult();
                listener = null;
            }
        }

    }


    /**
     * 为textview 设值，避免空值情况
     *
     * @param tv
     * @param str
     */
    public void setTextValues(TextView tv, String str) {
        if (tv != null && !TextUtils.isEmpty(str)) {
            tv.setText(str);
        }
    }

    public void setTextValues(TextView tv, @StringRes int id) {
        String str = getString(id);
        if (tv != null && !TextUtils.isEmpty(str)) {
            tv.setText(str);
        }
    }

    /**
     * 获取
     *
     * @param colorId
     * @return
     */
    public int getColors(int colorId) {
        return ContextCompat.getColor(this, colorId);
    }

    public View getView(@LayoutRes int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

}
