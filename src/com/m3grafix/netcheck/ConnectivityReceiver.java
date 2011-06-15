package com.m3grafix.netcheck;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class ConnectivityReceiver extends BroadcastReceiver{
	static final public String NETWORK_STATUS_CHANGED="network_status_changed";
	private NetworkInfo netInfo;
	private boolean failOver;
	private boolean noConnection;
	private WifiManager wm;
	@Override
	public void onReceive(Context context, Intent intent){
		wm=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		ConnectivityManager connection=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netInfo=connection.getActiveNetworkInfo();
		failOver=intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);
		noConnection=intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		if(!noConnection ){
			WifiInfo wi=wm.getConnectionInfo();
			if(failOver && netInfo!=null && netInfo.isConnected()){

				Intent intentB=new Intent(NETWORK_STATUS_CHANGED);
				intentB.putExtra("active", true);
				intentB.putExtra("SSID", wi.getSSID());
				context.sendBroadcast(intentB);
			}else{
				Intent intentB=new Intent(NETWORK_STATUS_CHANGED);
				intentB.putExtra("active", true);
				intentB.putExtra("SSID", wi.getSSID());
				context.sendBroadcast(intentB);
			}
			
		}else if(noConnection ){
			Intent intentB=new Intent(NETWORK_STATUS_CHANGED);
			intentB.putExtra("active", false);
			context.sendBroadcast(intentB);
		}else{
			Log.d("Network Manager","Network Offline...No Intent Fired");
		}
	}
}


