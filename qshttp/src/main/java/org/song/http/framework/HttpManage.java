package org.song.http.framework;

import android.app.Application;

import org.song.http.framework.java.JavaHttpClient;
import org.song.http.framework.ok.OkHttpClient;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by song on 2016/9/18.
 * http框架的一些配置、常量
 */
public class HttpManage {

    public static final boolean DEBUG = true;

    public static Application application;
    public static HttpEnum.XX_Http xx_http = HttpEnum.XX_Http.OK_HTTP;//

    /**
     * 使用前进行初始化
     * 才能支持缓存 cookie ssl证书 网络状态判断
     */
    public static void init(Application application) {
        HttpManage.application = application;
    }


    /**
     * 配置自签名ssl
     *
     * @param sslSocketFactory Utils里有提供方法使用
     * @param sslHost          设置需要自签名的主机地址,不设置则只能访问sslSocketFactory里的https网站
     */
    public static void setSSL(SSLSocketFactory sslSocketFactory, String... sslHost) {
        HttpManage.sslSocketFactory = sslSocketFactory;
        HttpManage.sslHost = sslHost;
    }

    static SSLSocketFactory sslSocketFactory;
    static String[] sslHost;

    public static void setInterceptor(Interceptor interceptor) {
        getHttpClient().interceptor(interceptor);
    }

    static AbsHttpClient getHttpClient() {
        switch (xx_http) {
            case OK_HTTP:
                return OkHttpClient.getInstance();
            case JAVA_HTTP:
            default:
                return JavaHttpClient.getInstance();
        }
    }

    public static void cleanCache() {
        Utils.cleanCache();
    }

    public static int DEFAULT_THREAD_POOL_SIZE = 10;
    //缓存大小
    public static int DEFAULT_CACHE_SIZE = 110 * 1024 * 1024;

    //超时
    public static int TIMEOUT_CONNECTION = 12000;
    public static int TIMEOUT_SOCKET_READ = 15000;
    public static int TIMEOUT_SOCKET_WRITE = 15000;
    //进度回调的频率 毫秒
    public static int PROGRESS_SPACE = 500;

    //请求回调类型
    final static public int HTTP_SUCCESS = 0X200;// 访问成功
    final static public int HTTP_FAILURE = 0X201;// 访问出错
    final static public int HTTP_PROGRESS = 0X202;//进度

    public static final String CONTENT_TYPE_DATA = "multipart/form-data";
    public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    public static final String CONTENT_TYPE_URL = "application/x-www-form-urlencoded; charset=utf-8";
    public static final String HEAD_KEY_CT = "Content-Type";
    public static final String HEAD_KEY_CR = "Content-Range";
    public static final String HEAD_KEY_UA = "User-Agent";


}
