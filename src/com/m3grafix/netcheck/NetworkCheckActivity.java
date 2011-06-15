package com.m3grafix.netcheck;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NetworkCheckActivity extends Activity {
    private NetworkStatusReceiver netstatus;
    private TextView netStatus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        netStatus=(TextView) findViewById(R.id.netStatus);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	IntentFilter filter;
    	filter=new IntentFilter(ConnectivityReceiver.NETWORK_STATUS_CHANGED);
    	netstatus=new NetworkStatusReceiver();
    	registerReceiver(netstatus, filter);
    }
    public class NetworkStatusReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("NetTest","Receiver Fired");
			boolean active=intent.getBooleanExtra("active", false);
			String SSID=intent.getStringExtra("SSID");
			if(active && SSID!=null && SSID.equals("m3_net")){
				netStatus.setText("No Place Like Home.\nYou are on "+SSID);
			}else if(active && SSID!=null){
				netStatus.setText("Be careful! You are on "+SSID);
			}else{
				netStatus.setText("no connection");
			}
		}
	}
}