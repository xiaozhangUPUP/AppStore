package com.appstore.android.common.util;

import android.Manifest;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;


public class PermissionUtil {





    public static Observable<Boolean> requestPermisson(Activity activity, String permission){


        RxPermissions rxPermissions =  RxPermissions.getInstance(activity);


        return rxPermissions.request(permission);
    }





}
