

package com.github.edgelv34.example;


import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletStateChangeException;
import org.junit.Ignore;
import org.junit.Test;


/**
 *
 * @author Park, Jaeseo &lt;edgelv34 at gmail.com&gt;
 */
public class MyXletTest {


    @Test
    public void testloadDestroy() throws XletStateChangeException {
        final Xlet mx = new MyXlet();   // LOADED
        mx.destroyXlet(true);             // DESTROYED
    }


    @Test
    @Ignore
    public void testLoadInitDestroy() throws XletStateChangeException {

        final Xlet mx = new MyXlet();     // LOADED
        mx.initXlet(null);                // PAUSED
        mx.destroyXlet(true);             // DESTROYED
    }


    @Test
    @Ignore
    public void testLoadInitStartDestroy() throws XletStateChangeException {

        final Xlet mx = new MyXlet();     // LOADED
        mx.initXlet(null);                // PAUSED
        mx.startXlet();                   // ACTIVE
        mx.destroyXlet(true);             // DESTROYED
    }


    @Test
    @Ignore
    public void testLoadInitStartPauseDestroy()
        throws XletStateChangeException {

        final Xlet mx = new MyXlet();     // LOADED
        mx.initXlet(null);                // PAUSED
        mx.startXlet();                   // ACTIVE
        mx.pauseXlet();                   // PAUSED
        mx.destroyXlet(true);             // DESTROYED
    }


}

