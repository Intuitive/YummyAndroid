package com.intuitive.yummy.webservices;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class RestResponseReceiver extends ResultReceiver {
	private Receiver mReceiver;

    public RestResponseReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
    	this.mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
        	mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
