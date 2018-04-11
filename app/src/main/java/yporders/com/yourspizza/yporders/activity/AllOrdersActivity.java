package yporders.com.yourspizza.yporders.activity;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yporders.com.yourspizza.yporders.R;
import yporders.com.yourspizza.yporders.adapters.CustomAllOrdersAdapter;
import yporders.com.yourspizza.yporders.adapters.CustomPurchasedItemAdapter;
import yporders.com.yourspizza.yporders.global.PreferencedData;
import yporders.com.yourspizza.yporders.listview.NonScrollListView;
import yporders.com.yourspizza.yporders.network.ApiClientBase;
import yporders.com.yourspizza.yporders.network.ApiClientGetAllOrders;
import yporders.com.yourspizza.yporders.network.ApiClientGetItems;
import yporders.com.yourspizza.yporders.pojo.orderitempojo.OrderItemPojo;
import yporders.com.yourspizza.yporders.pojo.orderspojo.Orderpojo;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class AllOrdersActivity extends AppCompatActivity {

    private Toolbar tb;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private int skip;
    private String flag="0";
    private ApiClientGetAllOrders apiClientGetAllOrders;
    private Orderpojo orderpojo;
    private CustomAllOrdersAdapter customAllOrdersAdapter;
    private StickyRecyclerHeadersDecoration headersDecor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle("Order Details");

        tb.setNavigationIcon(R.mipmap.ic_back);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fetch();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                flag="0";
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();


                if (flag.compareTo("0")==0) {

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                    //    Toast.makeText(AllOrdersActivity.this, "Loading More Items", Toast.LENGTH_SHORT).show();
                        flag="1";
                        skip = skip + 10;

                        fetch();


                        return;

                    }
                }
                //     Toast.makeText(AllOrdersActivity.this, flag, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        if(skip==0) {
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        // show it
        apiClientGetAllOrders = ApiClientBase.getApiClient().create(ApiClientGetAllOrders.class);
        Call<Orderpojo> call= apiClientGetAllOrders.getItems(skip, PreferencedData.getPrefDeliveryCentre(this));
        call.enqueue(new Callback<Orderpojo>() {
            @Override
            public void onResponse(Call<Orderpojo> call, Response<Orderpojo> response) {
                orderpojo = response.body();

                if(skip==0) {
                    pDialog.dismiss();
                    customAllOrdersAdapter=new CustomAllOrdersAdapter(AllOrdersActivity.this,AllOrdersActivity.this,orderpojo.getItems());
                    headersDecor = new StickyRecyclerHeadersDecoration(customAllOrdersAdapter);
                    recyclerView.addItemDecoration(headersDecor);
                    recyclerView.setAdapter(customAllOrdersAdapter);

                }
                else
                {
                    customAllOrdersAdapter.notifyDataSetChanged();
                }


             //   Toast.makeText(AllOrdersActivity.this,orderpojo.getItems().toString(),Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Orderpojo> call, Throwable t) {
                pDialog.dismiss();

                Snackbar.make((RecyclerView)findViewById(R.id.recyclerView),"Something went wrong",Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //    Toast.makeText(DashboardActivity.this,"Snackbar",Toast.LENGTH_SHORT).show();
                                //    finish();
                                //    startActivity(getIntent());

                                recreate();


                            }
                        }).show();

                //  Toast.makeText(DashboardActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });


    }
}
