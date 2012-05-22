package com.manning.aip;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.manning.aip.json.JsonMovieParser;
import com.manning.aip.xml.SAXMovieParser;
import com.manning.aip.xml.XmlPullMovieParser;

public class GetMovieRatingTask extends AsyncTask<String, Void, Movie> {

   private static final String API_KEY = "624645327f33f7866355b7b728f9cd98";

   private static final String API_ENDPOINT = "http://api.themoviedb.org/2.1";

   private static final int PARSER_KIND_SAX = 0;
   private static final int PARSER_KIND_XMLPULL = 1;
   private static final int PARSER_KIND_JSON = 2;

   private int parserKind = PARSER_KIND_SAX;

   private Activity activity;

   public GetMovieRatingTask(Activity activity) {
      this.activity = activity;
   }

   @Override
   protected Movie doInBackground(String... params) {
      try {
         String imdbId = params[0];
         HttpClient httpClient = MyMovies.getHttpClient();
         String format = parserKind == PARSER_KIND_JSON ? "json" : "xml";
         String path =
                  "/Movie.imdbLookup/en/" + format + "/" + API_KEY + "/"
                           + imdbId;
         HttpGet request = new HttpGet(API_ENDPOINT + path);

         HttpResponse response = httpClient.execute(request);
         InputStream data = response.getEntity().getContent();

         switch (parserKind) {
            case PARSER_KIND_SAX:
               return SAXMovieParser.parseMovie(data);
            case PARSER_KIND_XMLPULL:
               return XmlPullMovieParser.parseMovie(data);
            case PARSER_KIND_JSON:
               return JsonMovieParser.parseMovie(data);
            default:
               throw new RuntimeException("unsupported parser");
         }
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   protected void onPostExecute(Movie movie) {
      if (movie == null) {
         Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show();
         return;
      }
      Dialog dialog = new Dialog(activity);
      dialog.setContentView(R.layout.movie_dialog);

      dialog.setTitle("IMDb rating for \"" + movie.getTitle() + "\"");

      TextView rating =
               (TextView) dialog.findViewById(R.id.movie_dialog_rating);
      rating.setText(movie.getRating());

      dialog.show();
   }
}
