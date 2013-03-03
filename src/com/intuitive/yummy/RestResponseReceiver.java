package com.intuitive.yummy;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class RestResponseReceiver extends ResultReceiver {
	private Receiver receiver;

    public RestResponseReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
    	this.receiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
        	receiver.onReceiveResult(resultCode, resultData);
        }
    }
}
