package com.manning.aip;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MyMovies extends ListActivity implements Callback,
         OnItemLongClickListener {

   private static final AbstractHttpClient httpClient;
   private static final HttpClient a;

   private static final HttpRequestRetryHandler retryHandler;

   static {
      //方案注册
      /**the registry is responsible for resolving a url scheme(such as http or https)
       * and port number(80) to a TCP socket created by an appropriate socket factory
       * 
       */
      SchemeRegistry schemeRegistry = new SchemeRegistry();
      schemeRegistry.register(new Scheme("http", PlainSocketFactory
               .getSocketFactory(), 80));

      HttpParams connManagerParams = new BasicHttpParams();
      ConnManagerParams.setMaxTotalConnections(connManagerParams, 5);
      ConnManagerParams.setMaxConnectionsPerRoute(connManagerParams,
               new ConnPerRouteBean(5));
      ConnManagerParams.setTimeout(connManagerParams, 15 * 1000);
      /**this connection manager does't handle a single connection ,but
       * a pool of them,where each connection can be taken from the pool
       * allocated to a thread */
      ThreadSafeClientConnManager cm =
               new ThreadSafeClientConnManager(connManagerParams,
                        schemeRegistry);

      //any HttpParams instance is a map of key/value pairs
      HttpParams clientParams = new BasicHttpParams();
      HttpProtocolParams.setUserAgent(clientParams, "MyMovies/1.0");
      //设置建立连接的超时
      HttpConnectionParams.setConnectionTimeout(clientParams, 15 * 1000);
      //设置等待数据的超时
      HttpConnectionParams.setSoTimeout(clientParams, 15 * 1000);
      httpClient = new DefaultHttpClient(cm, clientParams);
      a = AndroidHttpClient.newInstance("MyMovies/1.0");
      retryHandler = new DefaultHttpRequestRetryHandler(5, false) {

         public boolean retryRequest(IOException exception, int executionCount,
                  HttpContext context) {
            if (!super.retryRequest(exception, executionCount, context)) {
               Log.d("HTTP retry-handler", "Won't retry");
               return false;
            }
            try {
               Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            Log.d("HTTP retry-handler", "Retrying request...");
            return true;
         }
      };

      httpClient.setHttpRequestRetryHandler(retryHandler);
   }

   public static HttpClient getHttpClient() {
      return a;
   }

   private MovieAdapter adapter;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.main);

      ListView listView = getListView();
      listView.setOnItemLongClickListener(this);

      Button backToTop =
               (Button) getLayoutInflater().inflate(R.layout.list_footer, null);
      backToTop.setCompoundDrawablesWithIntrinsicBounds(getResources()
               .getDrawable(android.R.drawable.ic_menu_upload), null, null,
               null);
      listView.addFooterView(backToTop, null, true);

      this.adapter = new MovieAdapter(this);
      listView.setAdapter(this.adapter);
      listView.setItemsCanFocus(false);
      //监听网络改变
      registerReceiver(new ConnectionChangedBroadcastReceiver(),
               new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

      new UpdateNoticeTask(new Handler(this)).execute();
   }

   public void backToTop(View view) {
      getListView().setSelection(0);
   }

   protected void onListItemClick(ListView l, View v, int position, long id) {
      this.adapter.toggleMovie(position);
      this.adapter.notifyDataSetInvalidated();
   }

   public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
            long arg3) {
      Toast.makeText(this, "Getting details...", Toast.LENGTH_LONG).show();
      Movie movie = adapter.getItem(position);
      new GetMovieRatingTask(this).execute(movie.getId());
      return false;
   }

   public boolean handleMessage(Message msg) {
      String updateNotice = msg.getData().getString("text");
      AlertDialog.Builder dialog = new AlertDialog.Builder(this);
      dialog.setTitle("What's new");
      dialog.setMessage(updateNotice);
      dialog.setIcon(android.R.drawable.ic_dialog_info);
      dialog.setPositiveButton(getString(android.R.string.ok),
               new OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                  }
               });
      dialog.show();
      return false;
   }
}