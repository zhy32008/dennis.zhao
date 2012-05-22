package com.manning.aip.xml;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.manning.aip.Movie;

public class XmlPullMovieParser {

   private XmlPullParser xpp;

   public static Movie parseMovie(InputStream xml) throws Exception {
      return new XmlPullMovieParser().parse(xml);
   }

   public Movie parse(InputStream xml) throws Exception {
      Movie movie = new Movie();

      xpp = XmlPullParserFactory.newInstance().newPullParser();
      xpp.setInput(xml, "UTF-8");

      skipToTag("name");
      movie.setTitle(xpp.nextText());

      skipToTag("rating");
      movie.setRating(xpp.nextText());

      return movie;
   }

   private void skipToTag(String tagName) throws Exception {
      int event = xpp.getEventType();
      while (event != XmlPullParser.END_DOCUMENT
               && !tagName.equals(xpp.getName())) {
         event = xpp.next();
      }
   }
}
