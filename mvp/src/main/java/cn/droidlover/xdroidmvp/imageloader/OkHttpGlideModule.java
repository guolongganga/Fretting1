package cn.droidlover.xdroidmvp.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * <p>Description: </p>
 * <p>Company: 中企明道</p>
 * <p>Create Time:2017/12/26 13:28</p>
 *
 * @author MiaoWenHai
 */
public class OkHttpGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient mHttpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mHttpClient));
    }
}