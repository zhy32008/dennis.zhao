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
      BufferedReader reader = new BufferedReader(new InputStreamReader(json));
      StringBuilder sb = new StringBuilder();

      try {
         String line = reader.readLine();
         while (line != null) {
            sb.append(line);
            line = reader.readLine();
         }
      } catch (IOException e) {
         throw e;
      } finally {
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
