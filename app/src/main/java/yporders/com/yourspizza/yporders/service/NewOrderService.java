package yporders.com.yourspizza.yporders.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import yporders.com.yourspizza.yporders.R;
import yporders.com.yourspizza.yporders.activity.DashboardActivity;
import yporders.com.yourspizza.yporders.adapters.CustomOrdersAdapter;
import yporders.com.yourspizza.yporders.global.PreferencedData;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class NewOrderService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ypAdminNotify"+PreferencedData.getPrefDeliveryCentre(this));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String data=dataSnapshot.getValue(String.class);
               // Toast.makeText(NewOrderService.this,"new"+data,Toast.LENGTH_SHORT).show();

                if(dataSnapshot.getValue()!=null && (!data.equals(PreferencedData.getPrefNewOrder(getBaseContext()))) ) {

                    PreferencedData.setPrefNewOrder(getBaseContext(),data);
                    Intent intent = new Intent(NewOrderService.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(NewOrderService.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NewOrderService.this);
                    notificationBuilder.setContentTitle("YP Admin");
                    notificationBuilder.setContentText(" New Orders Alert ");
                    notificationBuilder.setAutoCancel(true);
                    notificationBuilder.setSmallIcon(R.drawable.icon_trans_logo);
                    notificationBuilder.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
                    notificationBuilder.setContentIntent(pendingIntent);
                    notificationBuilder.setPriority(NotificationManager.IMPORTANCE_HIGH);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notificationBuilder.build());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }


    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}
