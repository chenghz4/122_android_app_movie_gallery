package edu.uci.ics.fabflixmobile;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;

public class NetworkManager {
    private static Context context;
    private static NetworkManager instance = null;

    RequestQueue queue;
    private NetworkManager() {
        NukeSSLCerts.nuke();  // disable ssl cert self-sign check
    }

    public static NetworkManager sharedManager(Context ctx) {
        if (instance == null) {
            context = ctx;
            instance = new NetworkManager();
            instance.queue = Volley.newRequestQueue(context.getApplicationContext());

            // Create a new cookie store, which handles sessions information with the server.
            // This cookie store will be shared across all the network requests.
            CookieHandler.setDefault(new CookieManager());
        }

        return instance;
    }
}
