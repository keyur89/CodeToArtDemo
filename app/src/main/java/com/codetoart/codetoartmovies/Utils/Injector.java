package com.codetoart.codetoartmovies.Utils;


import com.codetoart.codetoartmovies.BuildConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */

public class Injector {

    private static EventBus eventBus;

    public static EventBus provideEventBus ()
    {
        if ( eventBus == null )
        {
            eventBus = EventBus.builder()
                    .logNoSubscriberMessages( BuildConfig.DEBUG )
                    .sendNoSubscriberEvent( BuildConfig.DEBUG )
                    .build();
        }
        return eventBus;
    }
}
