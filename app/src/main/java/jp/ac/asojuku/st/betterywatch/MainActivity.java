package jp.ac.asojuku.st.betterywatch;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int scale = intent.getIntExtra("scale", 0);
                int level = intent.getIntExtra("level", 0);
                int status = intent.getIntExtra("level", 0);
                String statusString = "";
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        statusString = "unknow";
                        break;
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        statusString = "charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        statusString = "discharging";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        statusString = "not charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        statusString = "full";
                        break;
                }
                final Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minute = calendar.get(Calendar.MINUTE);
                final int second = calendar.get(Calendar.SECOND);

                final String watch = "Battery Watch";
                final String message = hour + ":" + minute + ":" + second + "　 " + statusString + " " + level + "/" + scale;

                //Log.v("Battery Watch", "" + hour + ":" + minute + "" + second + "" + statusString + "" + level + "/" + scale);

                Activity mainActivity = (Activity) context;
                TextView title = (TextView) mainActivity.findViewById(R.id.TVtext);
                title.setText(watch);

                TextView tvmsg = (TextView) mainActivity.findViewById(R.id.textView);
                tvmsg.setText(message);


                final Button button = (Button) findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v){
                        String coment = watch + "Time:" + message.toString();
                        Toast.makeText(MainActivity.this, coment, Toast.LENGTH_LONG).show();
                    }
                });

            }

        };

    }
}


       /* @Override
        public void onBindViewHolder(final UranaiViewHolder holder,final int listPosition){
            holder.textViewNumber.setText(UranaiDataSet.get(listPosition).getUranai());
            holder.base.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(v.getContext(),"コメント"+
                            UranaiDataSet.get(listPosition).getComent(),Toast.LENGTH_SHORT).show();
                }

            });*/