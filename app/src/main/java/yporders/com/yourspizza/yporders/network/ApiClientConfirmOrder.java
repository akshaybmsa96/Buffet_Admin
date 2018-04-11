package yporders.com.yourspizza.yporders.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yporders.com.yourspizza.yporders.pojo.orderitempojo.OrderItemPojo;

/**
 * Created by akshaybmsa96 on 28/12/17.
 */

public interface ApiClientConfirmOrder {

    @POST("confirmorder.php")
    @FormUrlEncoded
    Call<String> getItems(@Field("orderid") String orderid);

}
