package com.serd.cashregister.rest.generics;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteTemplate<T> extends AsyncTask<Void, Void, T> {

    public class MyResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            boolean hasError = false;
            int rawStatusCode = response.getRawStatusCode();
            if (rawStatusCode != 200) {
                hasError = true;
            }
            return hasError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            //String body = IOUtils.toString(response.getBody());

        }
    }

    public interface Listener<T> {
        public void onDeleteResponse(T object);
    }

    final Class<T> typeParameterClass;
    private List<Listener> mListeners = new ArrayList<Listener>();
    private String mUrl;
    T mData;
    String mId;

    public DeleteTemplate(Class<T> typeParameterClass, String url, T pData, String pId)
    {
        this.typeParameterClass = typeParameterClass;
        this.mUrl = url;
        this.mData = pData;
        this.mId = pId;
    }

    public void registerListener(Listener listener)
    {
        mListeners.add(listener);
    }

    @Override
    protected T doInBackground(Void... params) {
        T result = null;
        try {
            // Set the Content-Type header
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType((MediaType.APPLICATION_XML));
            requestHeaders.set("id", mId);
            HttpEntity<T> requestEntity = new HttpEntity<T>(mData, requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

            // Add the Jackson and String message converters
            restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

            restTemplate.setErrorHandler(new MyResponseErrorHandler());

            // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
            StringBuilder objectUrl = new StringBuilder();
            objectUrl.append(mUrl);
            objectUrl.append(mId);
            ResponseEntity<T> responseEntity = restTemplate.exchange(objectUrl.toString(), HttpMethod.DELETE, requestEntity, typeParameterClass);
            result = responseEntity.getBody();
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(T object) {
        for (Listener listener : mListeners) {
            listener.onDeleteResponse(object);
        }
    }
}
