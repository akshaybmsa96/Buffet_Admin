package yporders.com.yourspizza.yporders.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yporders.com.yourspizza.yporders.pojo.orderitempojo.OrderItemPojo;
import yporders.com.yourspizza.yporders.pojo.orderspojo.Orderpojo;

/**
 * Created by akshaybmsa96 on 28/12/17.
 */

public interface ApiClientGetAllOrders {

    @POST("getallorders.php")
    @FormUrlEncoded
    Call<Orderpojo> getItems(@Field("skip") int skip,@Field("dcentre") String dcentre);
}
