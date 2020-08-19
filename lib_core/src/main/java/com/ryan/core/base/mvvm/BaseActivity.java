package com.ryan.core.base.mvvm;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ryan.core.BR;
import com.ryan.core.base.RootActivity;
import com.ryan.core.R;
import com.ryan.core.utils.QMUIStatusBarHelper;
import com.ryan.core.utils.StatusBarUtil;
import com.ryan.core.widget.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @Author: Ryan
 * @Date: 2020/7/16 14:20
 * @Use： MvvM模式Activity基类
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RootActivity {
    public V mBinding;
    public VM mViewModel;//如果某个页面很简单不需要单独的ViewModel去展示。VM直接传BaseViewModel即可，mViewModel对象将不会被创建
    private CompositeDisposable mCompositeDisposable;
    protected LoadingDialog dialog;
    protected TextView mTvCenter, mTvLeft, mTvRight, mTvMore;
    protected View mViewTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.themeYellow));
        initViewDataBinding();
        //页面间传值
        if (savedInstanceState != null) {
            initBundle(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initBundle(getIntent().getExtras());
        }
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 绑定mViewModel
     */
    private void initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        if (getLayoutId() == 0) {
            return;
        }

        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewModel = initMVVMViewModel();

        if (mViewModel == null) {
            createViewModel();
        }

        if (mViewModel != null) {
            mBinding.setVariable(initVariableId(), mViewModel);

            registerLiveDataCallBack();
            //页面事件监听的方法 用于ViewModel层转到View层的事件注册
            initViewObservable();
        }


    }

    private void initBundle(Bundle bundle) {
        if (mViewModel != null) {
            mViewModel.onBundle(bundle);
        }
    }

    /**
     * 初始化布局的id
     *
     * @return 布局的id
     */
    protected abstract int getLayoutId();

    /**
     * 不使用类名传来的ViewModel，使用临时自定义的ViewModel
     *
     * @return 重写次方法返回
     */
    public VM initMVVMViewModel() {
        return null;
    }

    /**
     * 布局文件里的ViewModel默命名为viewModel（命名为其它请重写方法返回自定义的命名）
     */
    public int initVariableId() {
        //因为 libcore 是无法引用 主app 里的BR（BR.viewModel）.所以我这里创建activity_binding.xml
        // 里命名了一个占位的viewModel以便通过编译期，实际运行时会被替换主app里的BR
        return BR.viewModel;
    }


    public abstract void initViewObservable();

    /**
     * 初始化view
     */
    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止空指针异常
        if (mViewModel != null) {
//            mViewModel.removeRxBus();
//            mViewModel.onDestroy();
            mViewModel = null;
        }
        if (mBinding != null) {
            mBinding.unbind();
        }
        unsubscribe();

    }


    /**
     * 添加activity里的订阅者 对订阅者统一管理,避免内存泄漏
     *
     * @param disposable
     */
    public void addRxDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解绑
     */
    public void unsubscribe() {
        if (this.mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            this.mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    /**
     * 获取泛型ViewModel的class对象
     * <p>
     * 如果 直接传进来的就是BaseViewModel，则不创建
     */
    private <T> Class<T> getViewModel(Object obj) {
        Class<?> currentClass = obj.getClass();
        Class<T> tClass = getGenericClass(currentClass, AndroidViewModel.class);
//        if (tClass == null || tClass == AndroidViewModel.class || tClass == NoViewModel.class) {
        if (tClass == null || tClass == BaseViewModel.class) { //如果 直接传进来的就是BaseViewModel，则不创建
            return null;
        }
        return tClass;
    }

    private <T> Class<T> getGenericClass(Class<?> klass, Class<?> filterClass) {
        Type type = klass.getGenericSuperclass();
        if (type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types) {
            Class<T> tClass = (Class<T>) t;
            if (filterClass.isAssignableFrom(tClass)) {
                return tClass;
            }
        }
        return null;
    }

    /**
     * 创建ViewModel
     */
    private void createViewModel() {
        Class<VM> viewModelClass = getViewModel(this);
        if (viewModelClass != null) {
            this.mViewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }


    /**
     * 请求时的进度条
     *
     * @param cancelAble 是否能取消，true_点击外部和返回时取消loading，false_点外部不能取消loading，
     */
    public void showDialog(String msg, final boolean... cancelAble) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            msg = "Loading...";
        }
        try {
            if (cancelAble == null || cancelAble.length == 0) {
                dialog = new LoadingDialog(mContext, msg, false);//默认不可关闭
            } else {
                dialog = new LoadingDialog(mContext, msg, cancelAble[0]);
            }
            dialog.show();
        } catch (Exception e) {
            // Log.e(TAG, e.toString());
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            try {
                dialog.dismiss();
                dialog = null;
            } catch (Exception ignored) {
            }
        }
    }


    /**
     * 注册(初始化)ViewModel与View的UI回调事件
     */
    protected void registerLiveDataCallBack() {
        //加载对话框显示
        mViewModel.getUILiveData().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showDialog(s);
            }
        });
        //加载对话框消失
        mViewModel.getUILiveData().getDismissDialogEvent().observe(this, aVoid -> dismissDialog());
        //跳入新页面
        mViewModel.getUILiveData().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterType.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterType.BUNDLE);
                startActivity(clz, bundle);
            }
        });

        //跳入新页面(共享元素动画)
        mViewModel.getUILiveData().getStartActivityAnimationEvent().observe(this, stringObjectMap -> {
            Class<?> clz = (Class<?>) stringObjectMap.get(BaseViewModel.ParameterType.CLASS);
            Bundle bundle = (Bundle) stringObjectMap.get(BaseViewModel.ParameterType.BUNDLE);
            View v = (View) stringObjectMap.get(BaseViewModel.ParameterType.VIEW);
            String name = (String) stringObjectMap.get(BaseViewModel.ParameterType.VIEW_NAME);
            startActivityAnimation(clz, v, name, bundle);
        });

        //finish界面
        mViewModel.getUILiveData().getFinishEvent().observe(this, aVoid -> finish());
        //关闭上一层
        mViewModel.getUILiveData().getOnBackPressedEvent().observe(this, aVoid -> onBackPressed());

        //跳转一个共用显示fragment页面
        mViewModel.getUILiveData().getStartContainerActivityEvent().observe(this, params -> {
            String canonicalName = (String) params.get(BaseViewModel.ParameterType.FARGMENT_NAME);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterType.BUNDLE);
            startContainerActivity(canonicalName, bundle);
        });
    }

}
