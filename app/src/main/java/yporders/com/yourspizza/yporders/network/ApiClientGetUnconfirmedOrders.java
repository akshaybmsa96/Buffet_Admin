package yporders.com.yourspizza.yporders.network;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import yporders.com.yourspizza.yporders.pojo.orderspojo.Orderpojo;

/**
 * Created by akshaybmsa96 on 27/12/17.
 */

public interface ApiClientGetUnconfirmedOrders {
    @POST("getunconfirmedorders.php")
    @FormUrlEncoded
    Observable<Orderpojo> getInfo(@Field("dcentre") String dcentre);
}
