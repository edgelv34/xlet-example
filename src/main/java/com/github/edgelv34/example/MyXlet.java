

package com.github.edgelv34.example;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;


/**
 *
 * @author Administrator
 */
public class MyXlet implements Xlet {


    // The properties holding entries from '/application.properties'
    final Properties applicationProperties = new Properties();


    public void initXlet(final XletContext ctx)
        throws XletStateChangeException {

        System.out.println("initXlet(" + ctx + ")");

        {
            final InputStream resource = getClass()
                .getResourceAsStream("/application.properties");
            if (resource == null) {
                throw new XletStateChangeException(
                    "resource not found: application.properties");
            }
            try {
                try {
                    applicationProperties.load(resource);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } finally {
                try {
                    resource.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        // prints entries of properties using while loop.
        final Enumeration whileloopEnumeration = applicationProperties.propertyNames();
        while (whileloopEnumeration.hasMoreElements()) {
            final Object key = whileloopEnumeration.nextElement();
            final Object val = applicationProperties.get(key);
            System.out.println("property: " + key + " = " + val);
        }

        // prints entries of properties using for loop.
        for (final Enumeration forloopEnumeration = applicationProperties.propertyNames();
             forloopEnumeration.hasMoreElements();) {
            final Object key = forloopEnumeration.nextElement();
            final Object val = applicationProperties.get(key);
            System.out.println("property: " + key + " = " + val);

        }
    }


    public void startXlet() throws XletStateChangeException {

        System.out.println("startXlet()");
    }


    public void pauseXlet() {

        System.out.println("pauseXlet()");
    }


    public void destroyXlet(final boolean unconditional) // unconditional=무조건
        throws XletStateChangeException {

        System.out.println("destroyXlet(" + unconditional + ")");

        if (unconditional) { // 무조건 죽어라
        } else {             // 죽어도 되면 죽어라
        }
    }


}

