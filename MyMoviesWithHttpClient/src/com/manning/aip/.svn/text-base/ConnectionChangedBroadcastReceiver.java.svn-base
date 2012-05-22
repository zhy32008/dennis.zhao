package com.manning.aip;

import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.params.HttpParams;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.util.Log;

public class ConnectionChangedBroadcastReceiver extends BroadcastReceiver {

   public void onReceive(Context context, Intent intent) {
      String info = intent.getStringExtra(ConnectivityManager.EXTRA_EXTRA_INFO);
      NetworkInfo nwInfo =
               intent
                        .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
      Log.d("Connectivity change", info + ": " + nwInfo.getReason());

      HttpParams httpParams = MyMovies.getHttpClient().getParams();
      if (nwInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
         String proxyHost = Proxy.getHost(context);
         if (proxyHost == null) {
            proxyHost = Proxy.getDefaultHost();
         }
         int proxyPort = Proxy.getPort(context);
         if (proxyPort == -1) {
            proxyPort = Proxy.getDefaultPort();
         }
         if (proxyHost != null && proxyPort > -1) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
         } else {
            httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
         }
      } else {
         httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
      }
   }

}
