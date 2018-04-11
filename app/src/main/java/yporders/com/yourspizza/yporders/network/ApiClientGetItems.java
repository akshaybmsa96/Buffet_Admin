package yporders.com.yourspizza.yporders.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yporders.com.yourspizza.yporders.pojo.orderitempojo.OrderItemPojo;

/**
 * Created by akshaybmsa96 on 27/12/17.
 */

public interface ApiClientGetItems {
    @POST("getfullorderdetail.php")
    @FormUrlEncoded
    Call<OrderItemPojo> getItems(@Field("orderid") String orderid);
}
