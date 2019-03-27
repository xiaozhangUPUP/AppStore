package com.appstore.android.common.http;

import android.content.Context;
import android.content.Entity;
import android.util.Log;

import com.appstore.android.bean.User;
import com.appstore.android.common.Constants;
import com.appstore.android.common.util.ACache;
import com.appstore.android.common.util.DensityUtil;
import com.appstore.android.common.util.DeviceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by zhangqi on 2019/3/6
 */
public class CommonParamsInterceptor implements Interceptor {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Gson gson;
    private Context context;

    public CommonParamsInterceptor(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            String method = request.method();
            HashMap<String, Object> commonParamsMap = new HashMap<>();
            commonParamsMap.put(Constants.IMEI, DeviceUtils.getIMEI(context));
            commonParamsMap.put(Constants.MODEL, DeviceUtils.getModel());
            commonParamsMap.put(Constants.LANGUAGE, DeviceUtils.getLanguage());
            commonParamsMap.put(Constants.os, DeviceUtils.getBuildVersionIncremental());
            commonParamsMap.put(Constants.RESOLUTION, DensityUtil.getScreenW(context) + "*" + DensityUtil.getScreenH(context));
            commonParamsMap.put(Constants.SDK, DeviceUtils.getBuildVersionSDK() + "");
            commonParamsMap.put(Constants.DENSITY_SCALE_FACTOR, context.getResources().getDisplayMetrics().density + "");

            String token = ACache.get(context).getAsString(Constants.TOKEN);
            commonParamsMap.put(Constants.TOKEN, token == null ? "" : token);

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
                RequestBody body = request.body();


                HashMap<String, Object> rootMap = new HashMap<>();
                if (body instanceof FormBody) { // form 表单

                    for (int i = 0; i < ((FormBody) body).size(); i++) {

                        rootMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
                    }

                } else {
                    Buffer buffer = new Buffer();

                    body.writeTo(buffer);

                    String oldJsonParams = buffer.readUtf8();

                    rootMap = gson.fromJson(oldJsonParams, HashMap.class); // 原始参数
                    rootMap.put("publicParams", commonParamsMap); // 重新组装
                    String newJsonParams = gson.toJson(rootMap); // {"page":0,"publicParams":{"imei":'xxxxx',"sdk":14,.....}}

                    request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build();


                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chain.proceed(request);
    }

}
