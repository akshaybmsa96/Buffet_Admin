package yporders.com.yourspizza.yporders.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yporders.com.yourspizza.yporders.pojo.loginpojo.LoginPojo;

/**
 * Created by akshaybmsa96 on 31/12/17.
 */

public interface ApiClientAdminLogin {
    @POST("adminlogin.php")
    @FormUrlEncoded
    Call<LoginPojo> getItems(@Field("adminid") String adminid);
}
