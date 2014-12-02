package com.serd.cashregister.rest.sync;

import android.util.Log;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Tomas on 2. 12. 2014.
 */
public class SerdRestTemplate extends RestTemplate {

    private static final int TIMEOUT = 3 * 1000;
    public SerdRestTemplate(ClientHttpRequestFactory requestFactory)
    {
        super(requestFactory);
        updateTimeout(TIMEOUT);
    }

    public SerdRestTemplate() {
        super();
        updateTimeout(TIMEOUT);
    }

    private void updateTimeout(int pTimeout)
    {
        if (getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            Log.d("HTTP", "HttpUrlConnection is used");
            ((SimpleClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(pTimeout);
            ((SimpleClientHttpRequestFactory) getRequestFactory()).setReadTimeout(pTimeout);
        } else if (getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
            Log.d("HTTP", "HttpClient is used");
            ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setReadTimeout(pTimeout);
            ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(pTimeout);
        }
    }
}