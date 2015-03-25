

package com.github.edgelv34.example;


import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import javax.tv.graphics.TVContainer;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * This Class is Xlet Example
 *
 * @author Administrator
 */
public class MyXlet implements Xlet {


    // The properties holding entries from '/application.properties'
    final Properties applicationProperties = new Properties();


    /**
     * initXlet으로 제공되는 XletContext를 담아둠.
     */
    private XletContext context;


    private static JSONObject jsonUrl(final String spec) throws MalformedURLException {

        final URL url = new URL(spec);
        System.out.println("url : " + url);
        final HttpURLConnection connection
            = (HttpURLConnection) url.openConnection();
        //connection.setRequestMethod("GET");   // Default is "GET".
        //connection.setDoInput(true);   // Default is true.
        //connection.setDoOutput(false); // Default is false.
        connection.connect();
        try {
            System.out.println("connected");
            final int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
            if (responseCode == 200) {
                final InputStreamReader reader = new InputStreamReader(
                    connection.getInputStream(), "UTF-8");
                try {
                    final JSONParser parser = new JSONParser();
                    final JSONObject object
                        = (JSONObject) parser.parse(reader);
                    final JSONArray contacts
                        = (JSONArray) object.get("contacts");
                    System.out.println("contacts.size: " + contacts.size());
                    for (int i = 0; i < contacts.size(); i++) {
                        final JSONObject contact
                            = (JSONObject) contacts.get(i);
                        System.out.println("contact[" + i + "].id: "
                                           + contact.get("id"));
                        System.out.println("contact[" + i + "].name: "
                                           + contact.get("name"));
                        System.out.println("contact[" + i + "].email: "
                                           + contact.get("email"));
                        System.out.println("contact[" + i + "].address: "
                                           + contact.get("address"));
                        System.out.println("contact[" + i + "].gender: "
                                           + contact.get("gender"));
                        System.out.println("contact[" + i + "].phone: "
                                           + contact.get("phone"));
                        final JSONObject phone
                            = (JSONObject) contact.get("phone");
                        System.out.println(
                            "contact[" + i + "].phone.mobile: "
                            + phone.get("mobile"));
                        System.out.println(
                            "contact[" + i + "].phone.home: "
                            + phone.get("home"));
                        System.out.println(
                            "contact[" + i + "].phone.office: "
                            + phone.get("office"));
                    }
                } finally {
                    reader.close();
                }
            } else {
                System.out.println(
                    "unexpected response code: " + responseCode);
            }
        } finally {
            connection.disconnect();
            System.out.println("disconnected");
        }

    }


    /**
     * JSON.simple 을 이용하여 json파일을 parse 한다.
     */
    private static void parseJsonSimple() {

        // open and parse http://api.androidhive.info/contacts/
        try {
            final URL url = new URL("http://api.androidhive.info/contacts/");
            System.out.println("Request url : " + url);
            final HttpURLConnection connection
                = (HttpURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");   // Default is "GET".
            //connection.setDoInput(true);   // Default is true.
            //connection.setDoOutput(false); // Default is false.
            connection.connect();
            try {
                System.out.println("connected");
                final int responseCode = connection.getResponseCode();
                System.out.println("response code : " + responseCode);
                if (responseCode == 200) {
                    final InputStreamReader reader = new InputStreamReader(
                        connection.getInputStream(), "UTF-8");
                    try {
                        final JSONParser parser = new JSONParser();
                        final JSONObject object
                            = (JSONObject) parser.parse(reader);
                        final JSONArray contacts
                            = (JSONArray) object.get("contacts");
                        System.out.println("contacts.size: " + contacts.size());
                        for (int i = 0; i < contacts.size(); i++) {
                            final JSONObject contact
                                = (JSONObject) contacts.get(i);
                            System.out.println("contact[" + i + "].id: "
                                               + contact.get("id"));
                            System.out.println("contact[" + i + "].name: "
                                               + contact.get("name"));
                            System.out.println("contact[" + i + "].email: "
                                               + contact.get("email"));
                            System.out.println("contact[" + i + "].address: "
                                               + contact.get("address"));
                            System.out.println("contact[" + i + "].gender: "
                                               + contact.get("gender"));
                            System.out.println("contact[" + i + "].phone: "
                                               + contact.get("phone"));
                            final JSONObject phone
                                = (JSONObject) contact.get("phone");
                            System.out.println(
                                "contact[" + i + "].phone.mobile: "
                                + phone.get("mobile"));
                            System.out.println(
                                "contact[" + i + "].phone.home: "
                                + phone.get("home"));
                            System.out.println(
                                "contact[" + i + "].phone.office: "
                                + phone.get("office"));
                        }
                    } finally {
                        reader.close();
                    }
                } else {
                    System.out.println(
                        "unexpected response code: " + responseCode);
                }
            } finally {
                connection.disconnect();
                System.out.println("disconnected");
            }
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }
    }


    /**
     * Xml 데이터를 Pull 방식으로 Parsing
     */
    private static void parseXmlPull() {
        // open and parse http://www.simplyhired.com/c/add-jobs/example-feed.xml
        // using xmlpull
        try {
            final URL url = new URL(
                "http://www.simplyhired.com/c/add-jobs/example-feed.xml");
            System.out.println("Request url : " + url);
            final HttpURLConnection connection
                = (HttpURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");   // Default is "GET".
            //connection.setDoInput(true);   // Default is true.
            //connection.setDoOutput(false); // Default is false.
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
                        connection.getInputStream(), "US-ASCII");
                    try {
                        final XmlPullParserFactory factory
                            = XmlPullParserFactory.newInstance();
                        final XmlPullParser parser = factory.newPullParser();
                        parser.setInput(reader);
                        parser.require(XmlPullParser.START_DOCUMENT, null,
                                       null);
                        parser.nextTag();
                        parser.require(XmlPullParser.START_TAG,
                                       null, "jobs");
                        for (int i = 1; parser.nextTag()
                                        == XmlPullParser.START_TAG; i++) {
                            parser.require(XmlPullParser.START_TAG,
                                           null, "job");
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG,
                                           null, "title");
                            final String title = parser.nextText();
                            parser.require(XmlPullParser.END_TAG,
                                           null, "title");
                            System.out.println("jobs/job["
                                               + i
                                               + "]/title: "
                                               + title);
                            parser.nextTag(); // <job-board-name>
                            parser.nextText();
                            parser.require(XmlPullParser.END_TAG,
                                           null, "job-board-name");
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG,
                                           null, "job-board-url");
                            final String jobBoardUrl = parser.nextText();
                            parser.require(XmlPullParser.END_TAG,
                                           null, "job-board-url");
                            System.out.println("jobBoardUrl: " + jobBoardUrl);
                            while (!(parser.next() == XmlPullParser.START_TAG
                                     && parser.getName()
                                     .equals("description"))) {
                            }
                            parser.require(XmlPullParser.START_TAG,
                                           null, "description");
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG,
                                           null, "summary");
                            final String summary = parser.nextText();
                            parser.require(XmlPullParser.END_TAG,
                                           null, "summary");
                            System.out.println("/jobs/job["
                                               + i
                                               + "]/description/summary: "
                                               + summary);
                            while (!(parser.next() == XmlPullParser.END_TAG
                                     && parser.getName().equals("job"))) {
                            }
                            parser.require(XmlPullParser.END_TAG, null, "job");
                        }
                        parser.require(XmlPullParser.END_TAG, null, "jobs");
                    } finally {
                        reader.close();
                    }
                } else {
                    System.out.println(
                        "unexpected response code: " + responseCode);
                }
            } finally {
                connection.disconnect();
                System.out.println("disconnected");
            }
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }
    }


    /**
     * Xml 데이터를 Document 방식으로 Parsing
     */
    private static void parseXmlKdom() {
        // open and parse http://www.simplyhired.com/c/add-jobs/example-feed.xml
        // using kdom
        try {
            final URL url = new URL(
                "http://www.simplyhired.com/c/add-jobs/example-feed.xml");
            System.out.println("Request url : " + url);
            final HttpURLConnection connection
                = (HttpURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");   // Default is "GET".
            //connection.setDoInput(true);   // Default is true.
            //connection.setDoOutput(false); // Default is false.
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
                        connection.getInputStream(), "US-ASCII");
                    try {
                        final XmlPullParserFactory factory
                            = XmlPullParserFactory.newInstance();
                        final XmlPullParser parser = factory.newPullParser();
                        parser.setInput(reader);
                        final Document document = new Document();
                        document.parse(parser);
                        final Element jobs = document.getRootElement();
                        final int childCount = jobs.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            final Object child = jobs.getChild(i);
                            if (!(child instanceof Element)) {
                                continue;
                            }
                            final Element job = (Element) child;
                            final Element title
                                = job.getElement(null, "title");
                            System.out.println("/jobs/job["
                                               + (i + 1)
                                               + "]/title: "
                                               + title.getText(0));
                        }
                    } finally {
                        reader.close();
                    }
                } else {
                    System.out.println(
                        "unexpected response code: " + responseCode);
                }
            } finally {
                connection.disconnect();
                System.out.println("disconnected");
            }
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }
    }


    /**
     * Loaded 상태를 지나 초기화를 진행하기위한 메소드
     *
     * @param ctx
     *
     * @throws XletStateChangeException
     */
    public void initXlet(final XletContext ctx)
        throws XletStateChangeException {

        System.out.println("initXlet(" + ctx + ")");

        context = ctx;

        {
            final InputStream resource = getClass().getResourceAsStream(
                "/application.properties");
            if (resource == null) {
                throw new XletStateChangeException(
                    "resource not found: application.properties");
            }
            try {
                try {
                    applicationProperties.load(resource);
                } catch (final IOException ioe) {
                    ioe.printStackTrace();
                }
            } finally {
                try {
                    resource.close();
                } catch (final IOException ioe) {
                    ioe.printStackTrace(System.err);
                }
            }
        }

        // prints entries of properties using while loop.
        final Enumeration whileloopEnumeration
            = applicationProperties.propertyNames();
        while (whileloopEnumeration.hasMoreElements()) {
            final Object key = whileloopEnumeration.nextElement();
            final Object val = applicationProperties.get(key);
            System.out.println("property: " + key + " = " + val);
        }

        // prints entries of properties using for loop.
        for (final Enumeration forloopEnumeration
            = applicationProperties.propertyNames();
             forloopEnumeration.hasMoreElements();) {
            final Object key = forloopEnumeration.nextElement();
            final Object val = applicationProperties.get(key);
            System.out.println("property: " + key + " = " + val);

        }
    }


    /**
     * Acived 상태로 진행되기 위한 메소드
     *
     * @throws XletStateChangeException
     */
    public void startXlet() throws XletStateChangeException {

        System.out.println("startXlet()");

        parseJsonSimple();
        parseXmlPull();
        parseXmlKdom();

        final Container rootContainer = TVContainer.getRootContainer(context);
        System.out.println("rootContainer: " + rootContainer);

        try {
            final Class clazz = Class.forName("org.havi.ui.HSceneFactory");
            final Method getInstance = clazz.getMethod("getInstance", new Class[0]);
            final Object instance = getInstance.invoke(null, new Object[0]);
            final Method getDefaultHScene = clazz.getMethod("getDefaultHScene", new Class[0]);
            final Object defaultHScene = getDefaultHScene.invoke(instance, new Object[0]);
            System.out.println("defaultHScene: " + defaultHScene);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Paused 상태로 만들기 위한 메소드
     */
    public void pauseXlet() {

        System.out.println("pauseXlet()");
    }


    /**
     * Destoryed 상태로 만들기 위한 메소드
     *
     * @param unconditional
     *
     * @throws XletStateChangeException
     */
    public void destroyXlet(final boolean unconditional) // unconditional=무조건
        throws XletStateChangeException {

        System.out.println("destroyXlet(" + unconditional + ")");

        if (unconditional) { // 무조건 죽어라
        } else {             // 죽어도 되면 죽어라
            // 죽기싫으면 XletStateChaneException 을 날려라
        }
    }


}

