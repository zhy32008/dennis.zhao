package com.manning.aip.xml;

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Xml;
import android.util.Xml.Encoding;

import com.manning.aip.Movie;

public class SAXMovieParser extends DefaultHandler {

   private Movie movie;

   private StringBuilder elementText;

   public static Movie parseMovie(InputStream xml) throws Exception {
      SAXMovieParser parser = new SAXMovieParser();
      Xml.parse(xml, Encoding.UTF_8, parser);
      return parser.getMovie();
   }

   public Movie getMovie() {
      return movie;
   }

   @Override
   public void startDocument() throws SAXException {
      elementText = new StringBuilder();
   }

   @Override
   public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
      if ("movie".equals(localName)) {
         movie = new Movie();
      }
   }

   @Override
   public void endElement(String uri, String localName, String qName)
      throws SAXException {

      if ("name".equals(localName)) {
         movie.setTitle(elementText.toString().trim());
      } else if ("rating".equals(localName)) {
         movie.setRating(elementText.toString().trim());
      }
      elementText.setLength(0);
   }

   @Override
   public void characters(char[] ch, int start, int length) throws SAXException {
      elementText.append(ch, start, length);
   }
}
