package com.manning.aip;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.manning.aip.R;

public class Movie {

   private String id, title, rating;

   public static List<Movie> inflateFromXml(Context context) {
      String[] ids = context.getResources().getStringArray(R.array.movie_ids);
      String[] titles = context.getResources().getStringArray(R.array.movies);
      ArrayList<Movie> movies = new ArrayList<Movie>(ids.length);

      for (int i = 0; i < ids.length; i++) {
         Movie movie = new Movie();
         movie.setId(ids[i]);
         movie.setTitle(titles[i]);
         movies.add(movie);
      }

      return movies;
   }

   @Override
   public String toString() {
      return title;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getRating() {
      return rating;
   }

   public void setRating(String rating) {
      this.rating = rating;
   }
}
