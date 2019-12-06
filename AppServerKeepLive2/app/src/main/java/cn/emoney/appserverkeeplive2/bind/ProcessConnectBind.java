package cn.emoney.appserverkeeplive2.bind;

import android.os.RemoteException;

import cn.emoney.appserverkeeplive2.IProcessConnectListener;

public class ProcessConnectBind extends IProcessConnectListener.Stub {
    @Override
    public void connectSuccessed(boolean successed) throws RemoteException {

    }

    @Override
    public void connectFailed(boolean failed) throws RemoteException {

    }
}
