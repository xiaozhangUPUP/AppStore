package com.appstore.android.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;

import com.appstore.android.R;
import com.appstore.android.bean.AppDownloadInfo;
import com.appstore.android.bean.AppInfo;
import com.appstore.android.bean.BaseBean;
import com.appstore.android.bean.PageBean;
import com.appstore.android.common.rx.RxHttpResponseCompat;
import com.appstore.android.common.rx.RxSchedulers;
import com.appstore.android.common.util.AppUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaSchedulersHook;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.function.DownloadEventFactory;

/**
 * Created by zhangqi on 2019/4/26
 */
public class DownloadButtonController {

    private String downloadDir;

    private RxDownload rxDownload;

    private Api api;

    public DownloadButtonController(RxDownload rxDownload) {
        this.rxDownload = rxDownload;
        if (rxDownload != null) {
            api = rxDownload.getRetrofit().create(Api.class);
        }
    }

    public void handClick(final DownloadProgressButton btn, final AppInfo appInfo) {

        bindClick(btn, appInfo);

        isAppInstalled(btn.getContext(), appInfo).flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {

            @Override
            public ObservableSource<DownloadEvent> apply(DownloadEvent downloadEvent) throws Exception {
                if (downloadEvent.getFlag() == DownloadFlag.UN_INSTALL) {
                    return isAppFileExist(appInfo);
                }
                return Observable.just(downloadEvent);
            }
        })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {

                    @Override
                    public ObservableSource<DownloadEvent> apply(DownloadEvent downloadEvent) throws Exception {
                        if (downloadEvent.getFlag() == DownloadFlag.FILE_EXIST) {
                            return getAppDownloadInfo(appInfo).flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                @Override
                                public ObservableSource<DownloadEvent> apply(AppDownloadInfo appDownloadInfo) throws Exception {
                                    appInfo.setAppDownloadInfo(appDownloadInfo);
                                    return receiveDownloadStatus(appDownloadInfo);
                                }
                            });
                        }
                        return Observable.just(downloadEvent);
                    }
                })
                .compose(RxSchedulers.<DownloadEvent>io_main())

                .subscribe(new DownloadConsumer(btn));


    }

    private void bindClick(final DownloadProgressButton btn, final AppInfo appInfo) {
        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                int flag = (int) btn.getTag(R.id.tag_apk_flag);
                switch (flag) {
                    case DownloadFlag.INSTALLED:
                        runApp(btn.getContext(), appInfo);
                        break;
                    case DownloadFlag.STARTED:
                        pausedDownload(appInfo);
                        break;
                    case DownloadFlag.PAUSED:
                    case DownloadFlag.NORMAL:
                        startDownload(btn, appInfo);
                        break;
                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(), appInfo);
                        break;
                }
            }
        });

    }

    private void installApp(Context context, AppInfo appInfo) {
        String path = downloadDir + File.separator + appInfo.getReleaseKeyHash();
        AppUtils.installApk(context, path);
    }

    private void startDownload(final DownloadProgressButton btn, final AppInfo appInfo) {
        AppDownloadInfo appDownloadInfo = appInfo.getAppDownloadInfo();
        if (appDownloadInfo == null) {
            getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
                @Override
                public void accept(AppDownloadInfo appDownloadInfo) throws Exception {
                    appInfo.setAppDownloadInfo(appDownloadInfo);
                    download(btn, appDownloadInfo);
                }
            });
        } else {
            download(btn, appDownloadInfo);
        }
    }

    private void download(DownloadProgressButton btn, AppDownloadInfo appInfo) {
        rxDownload.serviceDownload(appInfo.getDownloadUrl()).subscribe();
        rxDownload.receiveDownloadStatus(appInfo.getDownloadUrl()).subscribe(new DownloadConsumer(btn));
    }

    private void pausedDownload(AppInfo appInfo) {
        AppDownloadInfo appDownloadInfo = appInfo.getAppDownloadInfo();
        rxDownload.pauseServiceDownload(appDownloadInfo.getDownloadUrl());
    }

    private void runApp(Context context, AppInfo appInfo) {
        AppUtils.runApp(context, appInfo.getPackageName());
    }


    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo) {
        DownloadEvent event = new DownloadEvent();
        event.setFlag(AppUtils.isInstalled(context, appInfo.getPackageName()) ?
                DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);

        return Observable.just(event);
    }

    public Observable<DownloadEvent> isAppFileExist(AppInfo appInfo) {
        String path = downloadDir + File.separator + appInfo.getReleaseKeyHash();
        File file = new File(path);
        DownloadEvent event = new DownloadEvent();
        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);
        return Observable.just(event);
    }

    public Observable<DownloadEvent> receiveDownloadStatus(AppDownloadInfo appInfo) {
        return rxDownload.receiveDownloadStatus(appInfo.getDownloadUrl());
    }

    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo) {
        return api.getApps(appInfo.getId()).compose(RxHttpResponseCompat.<AppDownloadInfo>compatResult());
    }

    class DownloadConsumer implements Consumer<DownloadEvent> {

        private DownloadProgressButton btn;

        public DownloadConsumer(DownloadProgressButton btn) {
            this.btn = btn;
        }

        @Override
        public void accept(DownloadEvent downloadEvent) throws Exception {
            int flag = downloadEvent.getFlag();
            btn.setTag(R.id.tag_apk_flag, flag);
            switch (flag) {
                case DownloadFlag.INSTALLED:
                    btn.setText("运行");
                    break;
                case DownloadFlag.STARTED:
                    btn.setPreProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    break;
                case DownloadFlag.PAUSED:
                    btn.paused();
                    break;
                case DownloadFlag.NORMAL:
                    btn.download();
                    break;
            }
        }
    }

    interface Api {
        @GET("download/{id}")
        public Observable<BaseBean<AppDownloadInfo>> getApps(@Query("id") int id);
    }
}
