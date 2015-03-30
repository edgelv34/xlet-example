

package com.github.edgelv34.example;


import com.github.edgelv34.example.HttpUtil.XmlPullCallback;
import java.awt.Container;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import javax.media.Player;
import javax.tv.graphics.TVContainer;
import javax.tv.media.AWTVideoSize;
import javax.tv.media.AWTVideoSizeControl;
import javax.tv.service.selection.ServiceContentHandler;
import javax.tv.service.selection.ServiceContext;
import javax.tv.service.selection.ServiceContextException;
import javax.tv.service.selection.ServiceContextFactory;
import javax.tv.service.selection.ServiceMediaHandler;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


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
    private XletContext xletContext;


    /**
     * {@inheritDoc}.
     *
     * @param ctx the XletContext recevied Loaded State
     *
     * @throws XletStateChangeException {@inheritDoc}
     */
    public void initXlet(final XletContext ctx)
        throws XletStateChangeException {

        System.out.println("initXlet(" + ctx + ")");

        xletContext = ctx;

        {
            final InputStream resource = getClass().getResourceAsStream(
                "/application.properties");
            if (resource == null) {
                System.err.println("application.properties not found");
            } else {
                try {
                    try {
                        applicationProperties.load(resource);
                    } finally {
                        resource.close();
                    }
                } catch (final IOException ioe) {
                    ioe.printStackTrace();
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
     * {@inheritDoc}.
     *
     * @throws XletStateChangeException {@inheritDoc}
     */
    public void startXlet() throws XletStateChangeException {

        System.out.println("startXlet()");

        try {
            final JSONObject object = HttpUtil.parseJsonSimple(
                "http://api.androidhive.info/contacts/", "UTF-8");
            final JSONArray contacts = (JSONArray) object.get("contacts");
            System.out.println("contacts.size: " + contacts.size());
            for (int i = 0; i < contacts.size(); i++) {
                final JSONObject contact = (JSONObject) contacts.get(i);
                System.out.println(
                    "contact[" + i + "].id: " + contact.get("id"));
                System.out.println(
                    "contact[" + i + "].name: " + contact.get("name"));
                System.out.println(
                    "contact[" + i + "].email: " + contact.get("email"));
                System.out.println(
                    "contact[" + i + "].address: " + contact.get("address"));
                System.out.println(
                    "contact[" + i + "].gender: " + contact.get("gender"));
                System.out.println(
                    "contact[" + i + "].phone: " + contact.get("phone"));
                final JSONObject phone = (JSONObject) contact.get("phone");
                System.out.println("contact[" + i + "].phone.mobile: "
                                   + phone.get("mobile"));
                System.out.println("contact[" + i + "].phone.home: "
                                   + phone.get("home"));
                System.out.println("contact[" + i + "].phone.office: "
                                   + phone.get("office"));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

//        try {
//            final XmlPullParser parser = HttpUtil.parseXmlPull(
//                "http://www.simplyhired.com/c/add-jobs/example-feed.xml",
//                "US-ASCII");
//            parser.require(XmlPullParser.START_DOCUMENT, null, null);
//            parser.nextTag();
//            parser.require(XmlPullParser.START_TAG, null, "jobs");
//            for (int i = 1; parser.nextTag() == XmlPullParser.START_TAG; i++) {
//                parser.require(XmlPullParser.START_TAG, null, "job");
//                parser.nextTag();
//                parser.require(XmlPullParser.START_TAG, null, "title");
//                final String title = parser.nextText();
//                parser.require(XmlPullParser.END_TAG, null, "title");
//                System.out.println("jobs/job[" + i + "]/title: " + title);
//                parser.nextTag(); // <job-board-name>
//                parser.nextText();
//                parser.require(XmlPullParser.END_TAG, null, "job-board-name");
//                parser.nextTag();
//                parser.require(XmlPullParser.START_TAG, null, "job-board-url");
//                final String jobBoardUrl = parser.nextText();
//                parser.require(XmlPullParser.END_TAG, null, "job-board-url");
//                System.out.println("jobBoardUrl: " + jobBoardUrl);
//                while (!(parser.next() == XmlPullParser.START_TAG
//                         && parser.getName().equals("description"))) {
//                }
//                parser.require(XmlPullParser.START_TAG, null, "description");
//                parser.nextTag();
//                parser.require(XmlPullParser.START_TAG, null, "summary");
//                final String summary = parser.nextText();
//                parser.require(XmlPullParser.END_TAG, null, "summary");
//                System.out.println(
//                    "/jobs/job[" + i + "]/description/summary: " + summary);
//                while (!(parser.next() == XmlPullParser.END_TAG
//                         && parser.getName().equals("job"))) {
//                }
//                parser.require(XmlPullParser.END_TAG, null, "job");
//            }
//        } catch (final Exception e) {
//            e.printStackTrace(System.err);
//        }

        try {
            final boolean responseOk = HttpUtil.parseXmlPull(
                "http://www.simplyhired.com/c/add-jobs/example-feed.xml",
                "US-ASCII",
                new XmlPullCallback() {


                    public void visit(final XmlPullParser parser)
                    throws XmlPullParserException, IOException {

                        parser.require(XmlPullParser.START_DOCUMENT, null, null);
                        parser.nextTag();
                        parser.require(XmlPullParser.START_TAG, null, "jobs");
                        for (int i = 1; parser.nextTag() == XmlPullParser.START_TAG; i++) {
                            parser.require(XmlPullParser.START_TAG, null, "job");
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG, null, "title");
                            final String title = parser.nextText();
                            parser.require(XmlPullParser.END_TAG, null, "title");
                            System.out.println("jobs/job[" + i + "]/title: " + title);
                            parser.nextTag(); // <job-board-name>
                            parser.nextText();
                            parser.require(XmlPullParser.END_TAG, null, "job-board-name");
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG, null, "job-board-url");
                            final String jobBoardUrl = parser.nextText();
                            parser.require(XmlPullParser.END_TAG, null, "job-board-url");
                            System.out.println("jobBoardUrl: " + jobBoardUrl);
                            while (!(parser.next() == XmlPullParser.START_TAG
                                     && parser.getName().equals("description"))) {
                            }
                            parser.require(XmlPullParser.START_TAG, null, "description");
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG, null, "summary");
                            final String summary = parser.nextText();
                            parser.require(XmlPullParser.END_TAG, null, "summary");
                            System.out.println(
                                "/jobs/job[" + i + "]/description/summary: " + summary);
                            while (!(parser.next() == XmlPullParser.END_TAG
                                     && parser.getName().equals("job"))) {
                            }
                            parser.require(XmlPullParser.END_TAG, null, "job");
                        }
                    }


                });
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }


        try {
            final Document document = HttpUtil.parseKxmlDocument(
                "http://www.simplyhired.com/c/add-jobs/example-feed.xml",
                "US-ASCII");
            final Element jobs = document.getRootElement();
            final int childCount = jobs.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final Object child = jobs.getChild(i);
                if (!(child instanceof Element)) {
                    continue;
                }
                final Element job = (Element) child;
                final Element title = job.getElement(null, "title");
                System.out.println(
                    "/jobs/job[" + (i + 1) + "]/title: " + title.getText(0));
            }
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }


        final Container rootContainer
            = TVContainer.getRootContainer(xletContext);
        System.out.println("rootContainer: " + rootContainer);
        System.out.println("rootContainer Maximum Size: "
                           + rootContainer.getMaximumSize());
        System.out.println("rootContainer Maximum Size: "
                           + rootContainer.getMinimumSize());
        System.out.println("rootContainer Maximum Size: "
                           + rootContainer.getPreferredSize());

        try {
            final Class clazz = Class.forName("org.havi.ui.HSceneFactory");
            final Method getInstance
                = clazz.getMethod("getInstance", new Class[0]);
            final Object instance = getInstance.invoke(null, new Object[0]);
            final Method getDefaultHScene
                = clazz.getMethod("getDefaultHScene", new Class[0]);
            final Container defaultHScene
                = (Container) getDefaultHScene.invoke(instance, new Object[0]);
            System.out.println("defaultHScene: " + defaultHScene);
            System.out.println("defaultHScene Maximum Size: "
                               + defaultHScene.getMaximumSize());
            System.out.println("defaultHScene Maximum Size: "
                               + defaultHScene.getMinimumSize());
            System.out.println("defaultHScene Maximum Size: "
                               + defaultHScene.getPreferredSize());
        } catch (final Exception e) {
            e.printStackTrace();
        }

        Player player = null;
        {
            final ServiceContextFactory f = ServiceContextFactory.getInstance();
            try {
                final ServiceContext t = f.getServiceContext(xletContext);
                final ServiceContentHandler[] c = t.getServiceContentHandlers();
                for (int i = 0; i < c.length; i++) {
                    System.out.println(
                        "serviceContentHandler[" + i + "]: " + c[i]);
                    if (c[i] instanceof ServiceMediaHandler) {
                        final Player p = (ServiceMediaHandler) c[i];
                        System.out.println("\t-> is a player: " + p);
                        if (player == null) {
                            player = p;
                        }
                    }
                }
            } catch (final ServiceContextException sce) {
                sce.printStackTrace();
            }
        }
        System.out.println("(first) player: " + player);

        if (player != null) {
            // resize to 400x300
            final AWTVideoSizeControl control
                = (AWTVideoSizeControl) player.getControl(
                    "javax.tv.media.AWTVideoSizeControl");
            if (control == null) {
                // not supported?
            } else {
                System.out.println("control: " + control);
                final Rectangle source
                    = new Rectangle(control.getSourceVideoSize());
                System.out.println("source: " + source);
                final Rectangle target = new Rectangle(400, 300);
                System.out.println("target: " + target);
                final AWTVideoSize desired = new AWTVideoSize(source, target);
                System.out.println("disired: " + desired);
                final AWTVideoSize checked = control.checkSize(desired);
                System.out.println("checked: " + checked);
                final boolean resized = control.setSize(checked);
                System.out.println("resized: " + resized);
                System.out.println("control.size: " + control.getSize());
            }
        }
    }


    /**
     * {@inheritDoc}.
     */
    public void pauseXlet() {

        System.out.println("pauseXlet()");
    }


    /**
     * {@inheritDoc}.
     *
     * @param unconditional {@inheritDoc}
     *
     * @throws XletStateChangeException {@inheritDoc}
     */
    public void destroyXlet(final boolean unconditional) // unconditional=무조건
        throws XletStateChangeException {

        System.out.println("destroyXlet(" + unconditional + ")");

        if (unconditional) {
            // 무조건 죽어라
        } else {
            // 상황 봐서 죽어라
            // 죽기싫으면 XletStateChaneException 을 날려라
        }
    }


}

