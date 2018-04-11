package yporders.com.yourspizza.yporders.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akshaybmsa96 on 29/10/17.
 */

public class ApiClientBase {

   // public static String url="http://139.59.5.188:8080/";

   //  public static String url="http://10.0.2.2:80/yp/";

    public static String url=" http://ypoutlet.esy.es/yp/api/admin/";
    public static Retrofit retrofit = null;


    public static Retrofit getApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url).
                    addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
