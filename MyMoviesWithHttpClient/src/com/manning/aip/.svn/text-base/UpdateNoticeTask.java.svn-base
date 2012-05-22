package com.manning.aip;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class UpdateNoticeTask extends AsyncTask<Void, Void, String> {

   private String UPDATE_URL =
            "http://android-in-practice.googlecode.com/files/update_notice.txt";

   private Handler handler;

   public UpdateNoticeTask(Handler handler) {
      this.handler = handler;
   }

   @Override
   protected String doInBackground(Void... params) {
      try {
         HttpGet request = new HttpGet(UPDATE_URL);
         request.setHeader("Accept", "text/plain");
         HttpResponse response = MyMovies.getHttpClient().execute(request);
         int statusCode = response.getStatusLine().getStatusCode();
         if (statusCode != HttpStatus.SC_OK) {
            return "Error: Failed getting update notes";
         }
         return EntityUtils.toString(response.getEntity());
      } catch (Exception e) {
         return "Error: " + e.getMessage();
      }
   }

   @Override
   protected void onPostExecute(String updateNotice) {
      Message message = new Message();
      Bundle data = new Bundle();
      data.putString("text", updateNotice);
      message.setData(data);
      handler.sendMessage(message);
   }
}
