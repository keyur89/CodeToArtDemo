package com.codetoart.codetoartmovies.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */
public class Network {

    private Context context;
    private ConnectivityManager connectionManager;

    boolean isOutcome = false;

    public Network(Context context){
        this.context = context;
    }

    public boolean getConnectivityStatus(){
        if(context != null){
            connectionManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo[] networkInfos = connectionManager.getAllNetworkInfo();

            for(NetworkInfo tempNetworkInfo : networkInfos ){
                if(tempNetworkInfo.isConnected()){
                    isOutcome = true;
                    break;
                }
            }
        }
        return  isOutcome;
    }
}
