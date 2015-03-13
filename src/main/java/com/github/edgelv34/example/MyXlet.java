

package com.github.edgelv34.example;


import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;


/**
 *
 * @author Administrator
 */
public class MyXlet implements Xlet {


    public void initXlet(final XletContext ctx)
        throws XletStateChangeException {
        System.out.println("initXlet(" + ctx + ")");
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

