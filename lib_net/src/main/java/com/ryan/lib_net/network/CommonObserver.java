package com.ryan.lib_net.network;

import android.util.Log;
import android.widget.Toast;

import com.ryan.core.base.mvvm.BaseViewModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class CommonObserver<T> implements Observer<T> {

    private boolean isShowDialog;
    private int SUCCESS_CODE = 0;

    private Disposable disposable;//没有继承BaseActivity的  需要手动解绑订阅 否则内存泄漏
    private BaseViewModel mViewModel;

    public CommonObserver(BaseViewModel mViewModel, boolean isShowDialog) {
        this.mViewModel = mViewModel;
        this.isShowDialog = isShowDialog;
//        if (this.isShowDialog && dialog == null) {
//            createLoading();
//        }
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    public abstract void success(T data);

    public abstract void error(Throwable e);

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        error(e);
//        dismiss();
        Log.e("onError----->", e.toString());
    }

    @Override
    public void onNext(T t) {
        // dismiss();
        success(t);
        if (t instanceof ResultModel) {
            ResultModel<T> bean = (ResultModel<T>) t;
            if (bean.getErrorCode() != SUCCESS_CODE) {
                Toast.makeText(mViewModel.getApplication(), bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                Log.e("请求失败----->", "错误码：" + bean.getErrorCode() + " ,错误信息：" + bean.getErrorMsg());
            }
        } else if (t instanceof ResultListModel) {
            ResultListModel<T> beans = (ResultListModel<T>) t;
            if (beans.getErrorCode() != SUCCESS_CODE) {
                Toast.makeText(mViewModel.getApplication(), beans.getErrorMsg(), Toast.LENGTH_SHORT).show();
                Log.e("请求失败----->", "错误码：" + beans.getErrorCode() + " ,错误信息：" + beans.getErrorMsg());
            }
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
//        if (isShowDialog && dialog != null) {
//            dialog.show();
//        }
        this.disposable = d;
        //调用addSubscribe()添加Disposable，请求与View周期同步
        mViewModel.addDisposable(d);
    }

    @Override
    public void onComplete() {
        //dismiss();
    }

//    /**
//     * 请求时的进度条
//     *
//     * @param cancelAble 是否能取消，true_点击外部和返回时取消loading，false_点外部不能取消loading，
//     */
//    public void createLoading(final boolean... cancelAble) {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//            return;
//        }
//        try {
//
//            if (cancelAble == null || cancelAble.length == 0) {
//                dialog = new LoadDialog(context, "数据加载中...", false);//默认不可关闭
//            } else {
//                dialog = new LoadDialog(context, "数据加载中...", cancelAble[0]);
//            }
//            dialog.show();
//
//        } catch (Exception e) {
//            // handle exception
//            // Log.e(TAG, e.toString());
//        }
//
//    }
//
//    public void dismiss() {
//        if (dialog != null) {
//            try {
//                dialog.dismiss();
//                dialog = null;
//            } catch (Exception e) {
//            }
//        }
//    }

}
