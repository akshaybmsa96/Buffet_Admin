package yporders.com.yourspizza.yporders.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by akshaybmsa96 on 28/12/17.
 */

public interface ApiClientCancelOrder {

    @POST("cancelorder.php")
    @FormUrlEncoded
    Call<String> getItems(@Field("orderid") String orderid);
}