/*
 * dump.java
 *
 * Created on August 19, 2006, 2:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.apache.commons.logging;

/**
 *
 * @author tayfun
 */
public class dump implements Log {
    
    /** Creates a new instance of dump */
    public dump() {
    }

    public boolean isDebugEnabled() {
        return false;
    }

    public boolean isErrorEnabled() {
        return false;
    }

    public boolean isFatalEnabled() {
        return false;
    }

    public boolean isInfoEnabled() {
        return false;
    }

    public boolean isTraceEnabled() {
        return false;
    }

    public boolean isWarnEnabled() {
        return false;
    }

    public void trace(Object message) {
    }

    public void trace(Object message, Throwable t) {
    }

    public void debug(Object message) {
    }

    public void debug(Object message, Throwable t) {
    }

    public void info(Object message) {
    }

    public void info(Object message, Throwable t) {
    }

    public void warn(Object message) {
    }

    public void warn(Object message, Throwable t) {
    }

    public void error(Object message) {
    }

    public void error(Object message, Throwable t) {
    }

    public void fatal(Object message) {
    }

    public void fatal(Object message, Throwable t) {
    }
    
}
