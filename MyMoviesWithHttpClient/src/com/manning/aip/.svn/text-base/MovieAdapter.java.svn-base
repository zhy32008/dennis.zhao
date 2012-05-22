package com.manning.aip;

import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

public class MovieAdapter extends ArrayAdapter<Movie> {

   private HashMap<Integer, Boolean> movieCollection =
            new HashMap<Integer, Boolean>();

   private String[] movieIconUrls;

   public MovieAdapter(Context context) {
      super(context, R.layout.movie_item, android.R.id.text1, Movie
               .inflateFromXml(context));

      movieIconUrls =
               context.getResources().getStringArray(R.array.movie_thumbs);
   }

   public void toggleMovie(int position) {
      if (!isInCollection(position)) {
         movieCollection.put(position, true);
      } else {
         movieCollection.put(position, false);
      }
   }

   public boolean isInCollection(int position) {
      return movieCollection.get(position) == Boolean.TRUE;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      View listItem = super.getView(position, convertView, parent);

      CheckedTextView checkMark =
               (CheckedTextView) listItem.findViewById(android.R.id.text1);
      checkMark.setChecked(isInCollection(position));

      ImageView imageView = (ImageView) listItem.findViewById(R.id.movie_icon);
      imageView.setImageDrawable(null);
      imageView.setTag(position);
      String imageUrl = this.movieIconUrls[position];
      new DownloadTask(position, imageView).execute(imageUrl);

      return listItem;
   }
}
