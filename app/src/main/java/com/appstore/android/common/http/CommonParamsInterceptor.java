package com.appstore.android.common.http;

import android.content.Context;
import android.content.Entity;

import com.appstore.android.common.Constants;
import com.appstore.android.common.util.DeviceUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
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

            Set<String> paramNames = httpUrl.queryParameterNames();
            HashMap<String, Object> rootMap = new HashMap<>();

            for (String key : paramNames) {
                // p={}&pag=&
                if (Constants.PARAM.equals(key)) {
                    String oldParamJson = httpUrl.queryParameter(Constants.PARAM);
                    if (oldParamJson != null) {
                        HashMap<String, Object> p = gson.fromJson(oldParamJson, HashMap.class);  //original datas
                        if (p != null) {
                            for (Map.Entry<String, Object> entry : p.entrySet()) {
                                rootMap.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                } else {
                    rootMap.put(key, httpUrl.queryParameter(key));
                }

            }

            rootMap.put("publicParams", commonParamsMap);  //重新组装

            String newParamJson = gson.toJson(rootMap);

            String url = httpUrl.toString();
            int index = url.indexOf("?");
            if (index > 0) {
                url = url.substring(0, index);
            }
            url = url + "?" + Constants.PARAM + "=" + newParamJson;

            request = request.newBuilder().url(url).build();

        } else if ("POST".equals(method)) {


        }

        return chain.proceed(request);
    }

}
