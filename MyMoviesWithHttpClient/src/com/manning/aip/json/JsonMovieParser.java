package com.manning.aip.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import com.manning.aip.Movie;

public class JsonMovieParser {

   public static Movie parseMovie(InputStream json) throws Exception {
      //输入流. Expensive interaction with the underlying reader is minimized
      BufferedReader reader = new BufferedReader(new InputStreamReader(json));
      //输入流返回的字符串
      StringBuilder sb = new StringBuilder();

      try {
         //按行读取数据
         String line = reader.readLine();
         //循环读取数据
         while (line != null) {
            sb.append(line);
            line = reader.readLine();
         }
      } catch (IOException e) {
         throw e;
      } finally {
         //  关闭输入流
         reader.close();
      }
      JSONArray jsonReply = new JSONArray(sb.toString());

      Movie movie = new Movie();
      JSONObject jsonMovie = jsonReply.getJSONObject(0);
      movie.setTitle(jsonMovie.getString("name"));
      movie.setRating(jsonMovie.getString("rating"));

      return movie;
   }
}
