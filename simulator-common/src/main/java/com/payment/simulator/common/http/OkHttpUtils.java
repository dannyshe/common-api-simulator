//package com.payment.simulator.common.http;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.security.SecureRandom;
//import java.security.cert.X509Certificate;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.TimeUnit;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//import com.alibaba.fastjson.JSON;
//import com.payment.simulator.common.mock.MockParam;
//import com.payment.simulator.common.mock.MockTool;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okio.Buffer;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpHeaders;
//
//@Slf4j
//public class OkHttpUtils {
//    private static volatile OkHttpClient okHttpClient = null;
//    private static volatile Semaphore semaphore = null;
//    private Map<String, String> headerMap;
//    private Map<String, String> paramMap;
//    private String url;
//    private Request.Builder request;
//    private Object paramObject;
//
//    /**
//     * 初始化okHttpClient，并且允许https访问
//     */
//    private OkHttpUtils() {
//        if (okHttpClient == null) {
//            synchronized (OkHttpUtils.class) {
//                if (okHttpClient == null) {
//                    TrustManager[] trustManagers = buildTrustManagers();
//                    okHttpClient = new OkHttpClient.Builder()
//                            .connectTimeout(15, TimeUnit.SECONDS)
//                            .writeTimeout(30, TimeUnit.SECONDS)
//                            .readTimeout(30, TimeUnit.SECONDS)
//                            .sslSocketFactory(createSSLSocketFactory(trustManagers), (X509TrustManager) trustManagers[0])
//                            .addInterceptor(new LogInterceptor())
//                            .hostnameVerifier((hostName, session) -> true)
//                            .retryOnConnectionFailure(true)
//                            .build();
//                    addHeader("User-Agent", "Payment-1.0");
//                }
//            }
//        }
//    }
//
//    /**
//     * 用于异步请求时，控制访问线程数，返回结果
//     *
//     * @return
//     */
//    private static Semaphore getSemaphoreInstance() {
//        //只能1个线程同时访问
//        synchronized (OkHttpUtils.class) {
//            if (semaphore == null) {
//                semaphore = new Semaphore(0);
//            }
//        }
//        return semaphore;
//    }
//
//    public static OkHttpUtils builder() {
//        return new OkHttpUtils();
//    }
//
//    public OkHttpUtils url(String url) {
//        if (MockTool.whetherToSwitchMock()){
//            this.addHeader(MockParam.HEADER_TEST_MODE,MockTool.getRequestHeaderValue(MockParam.HEADER_TEST_MODE));
//            MockParam mockParam= MockTool.doGetMockParam(url);
//            this.url = mockParam.getApolloMockUrl();
//            this.addHeader("channel_id",mockParam.getApolloChannelId());
//        }else {
//            this.url = url;
//        }
//        return this;
//    }
//
//    public OkHttpUtils addParam(String key, String value) {
//        if (paramMap == null) {
//            paramMap = new LinkedHashMap<>(16);
//        }
//        paramMap.put(key, value);
//        return this;
//    }
//
//    public OkHttpUtils addParamObject(Object request) {
//        if (paramObject == null) {
//            paramObject = request;
//        }
//        return this;
//    }
//
//    public OkHttpUtils addHeader(String key, String value) {
//        if (headerMap == null) {
//            headerMap = new LinkedHashMap<>(16);
//        }
//        if (StringUtils.isNotEmpty(key)){
//            //mock且AUTHORIZATION不为空，跳过设置header头
//            if (key.equals(HttpHeaders.AUTHORIZATION) && MockTool.whetherToSwitchMock()){
//                return this;
//            }
//            headerMap.put(key, value);
//        }
//        return this;
//    }
//
//    /**
//     * 初始化get方法
//     *
//     * @return
//     */
//    public OkHttpUtils get() {
//        request = new Request.Builder().get();
//        StringBuilder urlBuilder = new StringBuilder(url);
//        if (paramMap != null) {
//            urlBuilder.append("?");
//            try {
//                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//                    urlBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8")).
//                            append("=").
//                            append(URLEncoder.encode(entry.getValue(), "utf-8")).
//                            append("&");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
//        }
//        request.url(urlBuilder.toString());
//        return this;
//    }
//
//    /**
//     * 初始化post方法
//     *
//     * @param isJsonPost true等于json的方式提交数据，类似postman里post方法的raw
//     *                   false等于普通的表单提交
//     * @return
//     */
//    public OkHttpUtils post(boolean isJsonPost) {
//        RequestBody requestBody;
//        if (isJsonPost) {
//            String json = "";
//            if (paramMap != null) {
//                json = JSON.toJSONString(paramMap);
//            } else if (paramObject != null) {
//                json = JSON.toJSONString(paramObject);
//            }
//            requestBody = RequestBody
//                    .create(MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE), json);
//        } else {
//            FormBody.Builder formBody = new FormBody.Builder();
//            if (paramMap != null) {
//                paramMap.forEach(formBody::add);
//            }
//            requestBody = formBody.build();
//        }
//        request = new Request.Builder().post(requestBody).url(url);
//        return this;
//    }
//
//    /**
//     * 同步请求
//     *
//     * @return
//     */
//    public Response sync() {
//        setHeader(request);
//        Response response = null;
//        try {
//            response = okHttpClient.newCall(request.build()).execute();
//            return response;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    /**
//     * 异步请求，有返回值
//     */
//    public String async() {
//        StringBuilder buffer = new StringBuilder("");
//        setHeader(request);
//        okHttpClient.newCall(request.build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                buffer.append("请求出错：").append(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                assert response.body() != null;
//                buffer.append(response.body().string());
//                getSemaphoreInstance().release();
//            }
//        });
//        try {
//            getSemaphoreInstance().acquire();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return buffer.toString();
//    }
//
//    /**
//     * 异步请求，带有接口回调
//     *
//     * @param callBack
//     */
//    public void async(ICallBack callBack) {
//        setHeader(request);
//        okHttpClient.newCall(request.build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                callBack.onFailure(call, e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                assert response.body() != null;
//                callBack.onSuccessful(call, response.body().string());
//            }
//        });
//    }
//
//    private void setHeader(Request.Builder request) {
//        if (headerMap != null) {
//            try {
//                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
//                    request.addHeader(entry.getKey(), entry.getValue());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 生成安全套接字工厂，用于https请求的证书跳过
//     *
//     * @return
//     */
//    private static SSLSocketFactory createSSLSocketFactory(TrustManager[] trustAllCerts) {
//        SSLSocketFactory ssfFactory = null;
//        try {
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new SecureRandom());
//            ssfFactory = sc.getSocketFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ssfFactory;
//    }
//
//    private static TrustManager[] buildTrustManagers() {
//        return new TrustManager[]{
//                new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[]{};
//                    }
//                }
//        };
//    }
//
//    /**
//     * 自定义接口回调
//     */
//    public interface ICallBack {
//
//        void onSuccessful(Call call, String data);
//
//        void onFailure(Call call, String errorMsg);
//
//    }
//
//    public class LogInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            long startTime = System.currentTimeMillis();
//            Response response = chain.proceed(chain.request());
//            long endTime = System.currentTimeMillis();
//            long duration = endTime - startTime;
//            MediaType mediaType = response.body().contentType();
//            String content = response.body().string();
//            log.info("url: {}", request.toString());
//            String method = request.method();
//            if ("POST".equals(method)) {
//                StringBuilder sb = new StringBuilder();
//                if (request.body() instanceof FormBody) {
//                    FormBody body = (FormBody) request.body();
//                    for (int i = 0; i < body.size(); i++) {
//                        sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",");
//                    }
//                    sb.delete(sb.length() - 1, sb.length());
//                    log.info("params: {}", sb.toString());
//                }
//                if (request.body() != null) {
//                    Buffer buffer = new Buffer();
//                    request.body().writeTo(buffer);
//                    log.info("requestBody:{}", buffer.readUtf8());
//                }
//            }
//            log.info("cost {}ms. response: {}", duration, content);
//            return response.newBuilder()
//                    .body(ResponseBody.create(mediaType, content))
//                    .build();
//        }
//    }
//}
