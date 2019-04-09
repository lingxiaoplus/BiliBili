package com.camera.lingxiao.common.utills;

import com.camera.lingxiao.common.http.HttpResultFunction;
import com.camera.lingxiao.common.observer.HttpRxObserver;
import com.trello.rxlifecycle2.LifecycleProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class RxJavaHelp {
    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 无管理生命周期,容易导致内存溢出
     */
    public static Observable getObservable(Observable<Object> apiObservable, final HttpRxObserver callback) {
        // showLog(request);
        Observable observable = apiObservable
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (callback != null){
                            callback.onCanceled();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<FragmentEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxFragment,RxDialogFragment
     *
     */
    public static Observable getObservable(Observable<Object> apiObservable, LifecycleProvider lifecycle, final HttpRxObserver callback) {
        //  showLog(request);
        Observable observable;
        if (lifecycle != null) {
            //手动管理移除监听生命周期.eg:FragmentEvent.STOP
            observable = apiObservable
                    .compose(lifecycle.bindToLifecycle())//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .doOnDispose(new Action() {
                        @Override
                        public void run() throws Exception {
                            if (callback != null)
                                callback.onCanceled();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable, callback);
        }

        return observable;
    }



    public static void work(ObservableOnSubscribe ob, HttpRxObserver callback){
        Observable observable = Observable.create(ob);
        getObservable(observable, callback).subscribe(callback);
    }

    public static void workWithLifecycle(LifecycleProvider lifecycle, ObservableOnSubscribe ob,HttpRxObserver callback){
        Observable observable = Observable.create(ob);
        getObservable(observable,lifecycle,callback).subscribe(callback);
    }
}
