package yporders.com.yourspizza.yporders.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import yporders.com.yourspizza.yporders.R;
import yporders.com.yourspizza.yporders.adapters.CustomOrdersAdapter;
import yporders.com.yourspizza.yporders.global.PreferencedData;
import yporders.com.yourspizza.yporders.listview.NonScrollListView;
import yporders.com.yourspizza.yporders.network.ApiClientBase;
import yporders.com.yourspizza.yporders.network.ApiClientGetUnconfirmedOrders;
import yporders.com.yourspizza.yporders.pojo.orderspojo.OrderDetailPojo;
import yporders.com.yourspizza.yporders.pojo.orderspojo.Orderpojo;
import yporders.com.yourspizza.yporders.service.NewOrderService;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomOrdersAdapter customOrderAdapter;
    private CompositeDisposable compositeDisposable;
    private ProgressDialog pDialog;
    private Toolbar tb;
    private StickyRecyclerHeadersDecoration headersDecor;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static Orderpojo orderPojo;
    int i=0;
    public static int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        j=0;


        tb=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle("Unconfirmed Orders");

        getCallpermission();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                            recreate();
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.

                    }
                }
        );


        if(!isNetworkAvailable())
        {
            Snackbar.make((LinearLayout)findViewById(R.id.linearLayout),"Network Unavailable",Snackbar.LENGTH_INDEFINITE)
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
        else {

            //startFetch();
            getOrders();


            if(!isServiceRunning(NewOrderService.class)) {
                Intent i=new Intent(this, NewOrderService.class);
                startService(i);
            }
        }

    }

    private void getCallpermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user'textViewBuyer response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        101);


            }
        }
    }

    private void getOrders() {


        if(i==0) {
            pDialog = new ProgressDialog(DashboardActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        compositeDisposable=new CompositeDisposable();

        ApiClientGetUnconfirmedOrders apiClientGetUnconfirmedOrders= ApiClientBase.getApiClient().create(ApiClientGetUnconfirmedOrders.class);

        compositeDisposable.addAll(apiClientGetUnconfirmedOrders.getInfo(PreferencedData.getPrefDeliveryCentre(this)).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));

    }

    private void handleResponse(Orderpojo orderpojo) {

        this.orderPojo=orderpojo;
        if(i==0) {
            pDialog.dismiss();
        }

        if(i==1) {
            recyclerView.removeItemDecoration(headersDecor);
        }

        customOrderAdapter=new CustomOrdersAdapter(DashboardActivity.this,DashboardActivity.this,orderpojo.getItems());
        headersDecor = new StickyRecyclerHeadersDecoration(customOrderAdapter);
        recyclerView.addItemDecoration(headersDecor);
        recyclerView.setAdapter(customOrderAdapter);

        if(i==0)
        startFetch();

        i=1;


    }

    private void startFetch() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ypAdminNotify"+PreferencedData.getPrefDeliveryCentre(this));


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
              getOrders();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void handleError(Throwable throwable) {

        if(i==0) {
            pDialog.dismiss();
        }

        Snackbar.make((LinearLayout)findViewById(R.id.linearLayout),"Something went wrong",Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //    Toast.makeText(DashboardActivity.this,"Snackbar",Toast.LENGTH_SHORT).show();
                        //    finish();
                        //    startActivity(getIntent());

                        recreate();


                    }
                }).show();

       // Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
        System.out.println(throwable.getMessage());
        System.out.println(throwable.getCause().toString());

    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRestart() {
        super.onRestart();

        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.menuAllOrders)
        {
            Intent intent=new Intent(DashboardActivity.this,AllOrdersActivity.class);
            startActivity(intent);
        }

        else if(id==R.id.logOut)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
            builder.setTitle("LOGOUT ?");
            builder.setMessage("Are You Sure?");
            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    stopService(new Intent(DashboardActivity.this,NewOrderService.class));
                    Intent i=new Intent(DashboardActivity.this,LoginActivity.class);
                    PreferencedData.clearPref(DashboardActivity.this);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static int getSize()
    {
        if(orderPojo!=null)
        return orderPojo.getItems().size();

        else return 0;
    }

}
