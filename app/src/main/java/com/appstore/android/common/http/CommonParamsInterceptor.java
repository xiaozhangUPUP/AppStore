package com.appstore.android.common.http;

import android.content.Context;

import com.appstore.android.common.Constants;
import com.appstore.android.common.util.DeviceUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhangqi on 2019/3/6
 */
public class CommonParamsInterceptor implements Interceptor {

    private Gson gson;
    private Context context;

    public CommonParamsInterceptor(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        HashMap<String, Object> commonParamsMap = new HashMap<>();
        commonParamsMap.put(Constants.IMEI, DeviceUtils.getIMEI(context));

        if ("GET".equals(method)) {
            HttpUrl httpUrl = request.url();
            String oldParamJson = httpUrl.queryParameter(Constants.PARAM);
            HashMap<String, Object> rootMap = gson.fromJson(oldParamJson, HashMap.class);  //original datas
            rootMap.put("publicParams", commonParamsMap);  //重新组装

            String newParamJson = gson.toJson(rootMap);

            String url = httpUrl.toString();
            url = url.substring(0, (url.indexOf("?") + 1)) + Constants.PARAM + "=" + newParamJson;

            request = request.newBuilder().url(url).build();

        } else if ("POST".equals(method)) {

        }
        return chain.proceed(request);
    }

}
