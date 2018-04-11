package yporders.com.yourspizza.yporders.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yporders.com.yourspizza.yporders.R;
import yporders.com.yourspizza.yporders.adapters.CustomPurchasedItemAdapter;
import yporders.com.yourspizza.yporders.listview.NonScrollListView;
import yporders.com.yourspizza.yporders.network.ApiClientBase;
import yporders.com.yourspizza.yporders.network.ApiClientCancelOrder;
import yporders.com.yourspizza.yporders.network.ApiClientConfirmOrder;
import yporders.com.yourspizza.yporders.network.ApiClientGetItems;
import yporders.com.yourspizza.yporders.pojo.orderitempojo.OrderItemPojo;
import yporders.com.yourspizza.yporders.pojo.orderspojo.OrderDetailPojo;

public class FullDetailActivity extends AppCompatActivity {

    private Toolbar tb;
    private ApiClientGetItems apiClientGetItems;
    private ApiClientConfirmOrder apiClientConfirmOrder;
    private ApiClientCancelOrder apiClientCancelOrder;
    private OrderItemPojo orderitempojo;
    private LinearLayout linearLayout,belowLayout;
    private NonScrollListView listView;
    private CustomPurchasedItemAdapter ca;
    private TextView textViewCustomerName,textViewDDate,textViewAlternateNumber,textViewAddress,textViewAmount,textViewStaus,textViewCookingInstruction;
    private OrderDetailPojo orderDetail;
    private Button buttonCancelOrder,buttonConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);

        tb=(Toolbar)findViewById(R.id.toolbar);
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

        textViewCustomerName=(TextView)findViewById(R.id.textViewCustomerName);
        textViewDDate=(TextView)findViewById(R.id.textViewDDate);
        textViewAlternateNumber=(TextView)findViewById(R.id.textViewAlternateNumber);
        textViewAddress=(TextView)findViewById(R.id.textViewAddress);
        textViewAmount=(TextView)findViewById(R.id.textViewAmount);
        textViewStaus=(TextView)findViewById(R.id.textViewStaus);
        textViewCookingInstruction=(TextView)findViewById(R.id.textViewCookingInstruction);

        buttonConfirmOrder=(Button)findViewById(R.id.buttonConfirmOrder);
        buttonCancelOrder=(Button)findViewById(R.id.buttonCancelOrder);

        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        belowLayout=(LinearLayout)findViewById(R.id.belowLayout);
        linearLayout.setVisibility(View.GONE);


        String order=getIntent().getStringExtra("orderDetail");
        JsonElement jsonElement=new JsonParser().parse(order);
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gson=gsonBuilder.create();
        orderDetail=(gson.fromJson(jsonElement,OrderDetailPojo.class));

        if(orderDetail.getIsCanceled().equals("0")&&orderDetail.getIsConfirmed().equals("0"))
        {
            belowLayout.setVisibility(View.VISIBLE);
            textViewStaus.setVisibility(View.GONE);
        }
        else
            {
                if(orderDetail.getIsCanceled().equals("1"))
                {
                    textViewStaus.setTextColor(getResources().getColor(R.color.Red));
                    textViewStaus.setText("Canceled");
                }

                else if(orderDetail.getIsConfirmed().equals("1"))
                {
                    textViewStaus.setTextColor(getResources().getColor(R.color.green));
                    textViewStaus.setText("Confirmed");
                }
                belowLayout.setVisibility(View.GONE);
        }


       // Toast.makeText(this,orderid,Toast.LENGTH_SHORT).show();
      //  System.out.println("orderid  =                                         " +orderid);

        if(isNetworkAvailable()) {
            getOrderItems(orderDetail.getOrder_id());
        }

        else
        {
            Snackbar.make((RelativeLayout)findViewById(R.id.activity_full_detail),"Network Unavailable",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            //    Toast.makeText(DashboardActivity.this,"Snackbar",Toast.LENGTH_SHORT).show();
                            //    finish();
                            //    startActivity(getIntent());

                            recreate();

                        }
                    }).show();
        }

        textViewCustomerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91"+String.valueOf(orderDetail.getUser_phn_number())));
                if (ActivityCompat.checkSelfPermission(FullDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(FullDetailActivity.this, "permission denied, check Setting", Toast.LENGTH_LONG).show();

                } else {
                    startActivity(intent);
                }

            }
        });

        textViewAlternateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91"+String.valueOf(orderDetail.getUser_alternate_phn_number())));
                if (ActivityCompat.checkSelfPermission(FullDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(FullDetailActivity.this, "permission denied, check Setting", Toast.LENGTH_LONG).show();

                } else {
                    startActivity(intent);
                }
            }
        });

        buttonCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FullDetailActivity.this);
                builder.setTitle("Cancel Order ?");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Cancel Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cancelOrder();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FullDetailActivity.this);
                builder.setTitle("Confirm Order ?");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Confirm Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        confirmOrder();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

    }

    private void confirmOrder()
    {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientConfirmOrder = ApiClientBase.getApiClient().create(ApiClientConfirmOrder.class);
        Call<String> call= apiClientConfirmOrder.getItems(orderDetail.getOrder_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String result=response.body();

                if(result.equals("1"))
                {
                    Toast.makeText(FullDetailActivity.this,"Order Confirmed",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(FullDetailActivity.this,DashboardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }

                else
                    {
                        Toast.makeText(FullDetailActivity.this,"Cannot Confirm",Toast.LENGTH_SHORT).show();
                }

                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pDialog.dismiss();


                Toast.makeText(FullDetailActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });

    }

    private void cancelOrder()
    {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientCancelOrder = ApiClientBase.getApiClient().create(ApiClientCancelOrder.class);
        Call<String> call= apiClientCancelOrder.getItems(orderDetail.getOrder_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String result=response.body();

                if(result.equals("1"))
                {
                    Toast.makeText(FullDetailActivity.this,"Order Canceled",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(FullDetailActivity.this,DashboardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(FullDetailActivity.this,"Cannot Canceled",Toast.LENGTH_SHORT).show();
                }

                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pDialog.dismiss();


                Toast.makeText(FullDetailActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });

    }

    private void getOrderItems(String orderid) {

      //  Toast.makeText(this,orderid,Toast.LENGTH_SHORT).show();


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientGetItems = ApiClientBase.getApiClient().create(ApiClientGetItems.class);
        Call<OrderItemPojo> call= apiClientGetItems.getItems(orderid);
        call.enqueue(new Callback<OrderItemPojo>() {
            @Override
            public void onResponse(Call<OrderItemPojo> call, Response<OrderItemPojo> response) {
                orderitempojo=response.body();
                linearLayout.setVisibility(View.VISIBLE);
                listView=(NonScrollListView)findViewById(R.id.listView);
                ca=new CustomPurchasedItemAdapter(FullDetailActivity.this,FullDetailActivity.this,orderitempojo.getItems());
                listView.setAdapter(ca);

           //     Toast.makeText(FullDetailActivity.this,orderitempojo.getItems().toString(),Toast.LENGTH_SHORT).show();
                pDialog.dismiss();

                initFields();

            }

            @Override
            public void onFailure(Call<OrderItemPojo> call, Throwable t) {
                pDialog.dismiss();

                Snackbar.make((RelativeLayout)findViewById(R.id.activity_full_detail),"Something went wrong",Snackbar.LENGTH_INDEFINITE)
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

    private void initFields() {

        SpannableString nm = new SpannableString(orderDetail.getUser_name());
        nm.setSpan(new UnderlineSpan(), 0, nm.length(), 0);

        textViewCustomerName.setText(nm);
        textViewDDate.setText(orderDetail.getOrder_date().substring(0,16));

        if(orderDetail.getUser_alternate_phn_number()==null || orderDetail.getUser_alternate_phn_number().length()==0)
        {
            textViewAlternateNumber.setVisibility(View.GONE);
        }
        else
        {
            SpannableString aN = new SpannableString("Alte No. :"+orderDetail.getUser_alternate_phn_number());
            aN.setSpan(new UnderlineSpan(), 0, aN.length(), 0);

            textViewAlternateNumber.setText(aN);
        }

        textViewAddress.setText(orderDetail.getUser_address()+"\n"+orderDetail.getDelivery_centre());
        textViewAmount.setText("₹ "+orderDetail.getTotal_price());

        if(orderDetail.getCooking_instructions().equals("") || orderDetail.getCooking_instructions()==null)
        {
            textViewCookingInstruction.setVisibility(View.GONE);
        }

        else {
            textViewCookingInstruction.setText(orderDetail.getCooking_instructions());
        }


    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
