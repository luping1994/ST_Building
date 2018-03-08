package net.suntrans.building.api;

import net.suntrans.building.domin.CameraHi;
import net.suntrans.building.domin.LoginResult;
import net.suntrans.building.vedio.camhi.MyCamera;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Looney on 2018/2/6.
 * Des:
 */

public interface Api {

    /**
     * 登录api
     *
     * @param grant_type    默认填password
     * @param client_id     默认填6
     * @param client_secret 默认填test
     * @param username      账号
     * @param password      密码
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/login")
    Flowable<LoginResult> login(@Field("grant_type") String grant_type,
                                @Field("client_id") String client_id,
                                @Field("client_secret") String client_secret,
                                @Field("username") String username,
                                @Field("password") String password);

    @POST("ScreenShow/camhi")
    Flowable<List<CameraHi>> getCamHiList();

}
