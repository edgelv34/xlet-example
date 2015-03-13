

package com.github.edgelv34.example;


import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletStateChangeException;
import org.junit.Test;


/**
 *
 * @author Administrator
 */
public class MyXletTest {


    @Test
    public void loadDestroy() throws XletStateChangeException {
        final Xlet mx = new MyXlet();   // LOADED
        mx.destroyXlet(true);             // DESTROYED
    }


    @Test
    public void loadInitDestroy() throws XletStateChangeException {
        final Xlet mx = new MyXlet();   // LOADED
        mx.initXlet(null);                // PAUSED
        mx.destroyXlet(true);             // DESTROYED
    }


    @Test
    public void loadInitStartDestroy() throws XletStateChangeException {
        final Xlet mx = new MyXlet();   // LOADED
        mx.initXlet(null);                // PAUSED
        mx.startXlet();                   // ACTIVE
        mx.destroyXlet(true);             // DESTROYED
    }


    @Test
    public void loadInitStartPauseDestroy() throws XletStateChangeException {
        final Xlet mx = new MyXlet();   // LOADED
        mx.initXlet(null);                // PAUSED
        mx.startXlet();                   // ACTIVE
        mx.pauseXlet();                   // PAUSED
        mx.destroyXlet(true);             // DESTROYED
    }


}

