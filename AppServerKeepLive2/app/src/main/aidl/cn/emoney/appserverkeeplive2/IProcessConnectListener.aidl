// IProcessConnectListener.aidl
package cn.emoney.appserverkeeplive2;

// Declare any non-default types here with import statements

interface IProcessConnectListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void connectSuccessed(boolean successed);
     void connectFailed(boolean failed);
}
