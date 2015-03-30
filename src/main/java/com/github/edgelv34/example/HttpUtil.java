

package com.github.edgelv34.example;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kxml2.kdom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * .
 * @author Park, Jaeseo &lt;edgelv34 at gmail.com&gt;
 */
public final class HttpUtil {


    /**
     * Parses given url and returns an instance of {@link JSONObject}.
     *
     * @param spec http url to parse
     * @param charset the charset to use
     *
     * @return an instance of {@link JSONObject} or {@code null} if http
     * response code is not {@code 200}
     *
     * @throws MalformedURLException if the url is malformed
     * @throws UnsupportedEncodingException if {@code charset} is not supported
     * @throws IOException if an I/O error occurs.
     * @throws ParseException if failed to parse
     */
    public static JSONObject parseJsonSimple(final String spec,
                                             final String charset)
        throws MalformedURLException, UnsupportedEncodingException, IOException,
               ParseException {

        System.out.println("parseJsonSimple(" + spec + ", " + charset + ")");

        if (spec == null) {
            throw new NullPointerException("null spec");
        }

        if (charset == null) {
            throw new NullPointerException("null charset");
        }

        final URL url = new URL(spec);
        System.out.println("url : " + url);
        final HttpURLConnection connection
            = (HttpURLConnection) url.openConnection();
        connection.connect();
        try {
            System.out.println("connected");
            final int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
            if (responseCode == 200) {
                final InputStreamReader reader = new InputStreamReader(
                    connection.getInputStream(), charset);
                try {
                    final JSONParser parser = new JSONParser();
                    return (JSONObject) parser.parse(reader);
                } finally {
                    reader.close();
                }
            } else {
                System.out.println(
                    "unexpected response code: " + responseCode);
                return null;
            }
        } finally {
            connection.disconnect();
            System.out.println("disconnected");
        }
    }


    /**
     * use parse XmlPull before InputstreamReader close.
     */
    public static interface XmlPullCallback {


        void visit(XmlPullParser parser)
            throws XmlPullParserException, IOException;


    }


    /**
     *
     * @param spec http url to prase
     * @param charset the charset to use
     * @param callback use parse XmlPull before InputstreamReader close
     *
     * @return if parse Successed true, else false
     *
     * @throws MalformedURLException if the url is malformed
     * @throws IOException if an I/O error occurs.
     * @throws XmlPullParserException if fail to pares XmlPull
     */
    public static boolean parseXmlPull(final String spec,
                                       final String charset,
                                       final XmlPullCallback callback)
        throws MalformedURLException, IOException, XmlPullParserException {

        if (spec == null) {
            throw new NullPointerException("null spsec");
        }

        if (charset == null) {
            throw new NullPointerException("null charset");
        }

        if (callback == null) {
            throw new NullPointerException("null XmlPullCallback");
        }

        final URL url = new URL(spec);
        final HttpURLConnection connection
            = (HttpURLConnection) url.openConnection();
        connection.connect();
        try {
            System.out.println("connected");
            final int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
            if (responseCode == 200) {
                final InputStreamReader reader = new InputStreamReader(
                    connection.getInputStream(), charset);
                try {
                    final XmlPullParserFactory factory
                        = XmlPullParserFactory.newInstance();
                    final XmlPullParser parser = factory.newPullParser();
                    parser.setInput(reader);
                    callback.visit(parser);
                    return true;
                } finally {
                    reader.close();
                }
            } else {
                System.out.println(
                    "unexpected response code: " + responseCode);
                return false;
            }
        } finally {
            connection.disconnect();
            System.out.println("disconnected");
        }
    }


    /**
     * Parses given url and returns an instance of {@link Document}.
     *
     * @param spec http url to parse
     * @param charset the charset to use
     *
     * @return an instance of {@link Document} or {@code null} if http response
     * code is not {@code 200}.
     *
     * @throws MalformedURLException if the url is malformed
     * @throws IOException if an I/O error occurs
     * @throws XmlPullParserException if fail to parse XmlPull
     */
    public static Document parseKxmlDocument(final String spec,
                                             final String charset)
        throws MalformedURLException, IOException, XmlPullParserException {

        if (spec == null) {
            throw new NullPointerException("null spsec");
        }

        if (charset == null) {
            throw new NullPointerException("null charset");
        }

        final URL url = new URL(spec);
        final HttpURLConnection connection
            = (HttpURLConnection) url.openConnection();
        connection.connect();
        try {
            System.out.println("connected");
            final int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
            if (responseCode == 200) {
                System.out.println(
                    "Content-Type: "
                    + connection.getHeaderField("Content-Type"));
                final InputStreamReader reader = new InputStreamReader(
                    connection.getInputStream(), charset);
                try {
                    final XmlPullParserFactory factory
                        = XmlPullParserFactory.newInstance();
                    final XmlPullParser parser = factory.newPullParser();
                    parser.setInput(reader);
                    final Document document = new Document();
                    document.parse(parser);
                    return document;
                } finally {
                    reader.close();
                }
            } else {
                System.out.println(
                    "unexpected response code: " + responseCode);
                return null;
            }
        } finally {
            connection.disconnect();
            System.out.println("disconnected");
        }
    }


    private HttpUtil() {

        super();
    }


}

