package br.ufc.great.iot.activitysystem;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DataReader mDataReader;

    private boolean detectionRunning = false;

    private Button btDetection;
    private TextView tvState;
    private TextView tvSensorLabel;
    private TextView tvSensorID;
    private TextView tvZoneID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataReader = new DataReader(this);

        btDetection = (Button) findViewById(R.id.bt_detection);
        tvState = (TextView) findViewById(R.id.lv_detection_state);
        tvSensorLabel = (TextView) findViewById(R.id.lv_sensor_label);
        tvSensorID = (TextView) findViewById(R.id.lb_sensor_id);
        tvZoneID = (TextView) findViewById(R.id.lb_zone_id);

        btDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detectionRunning){
                    mDataReader.stopDetection();
                    tvState.setText("Detection is Not Running");
                    tvState.setTextColor(Color.RED);
                    btDetection.setText("START DETECTION");

                    detectionRunning = false;
                }
                else{
                    mDataReader.startDetection();
                    tvState.setText("Detection is Running");
                    tvState.setTextColor(Color.GREEN);
                    btDetection.setText("STOP DETECTION");

                    detectionRunning = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDataReader != null) {
            mDataReader.stopDetection();
        }
    }

    public void updateUI(final String sensorLabel, final String sensorID, final String zoneID){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSensorLabel.setText(sensorLabel);
                tvSensorID.setText(sensorID);
                tvZoneID.setText(zoneID);
            }
        });
    }
}
