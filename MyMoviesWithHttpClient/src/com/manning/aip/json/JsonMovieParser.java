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
      //������. Expensive interaction with the underlying reader is minimized
      BufferedReader reader = new BufferedReader(new InputStreamReader(json));
      //���������ص��ַ���
      StringBuilder sb = new StringBuilder();

      try {
         //���ж�ȡ����
         String line = reader.readLine();
         //ѭ����ȡ����
         while (line != null) {
            sb.append(line);
            line = reader.readLine();
         }
      } catch (IOException e) {
         throw e;
      } finally {
         //  �ر�������
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
