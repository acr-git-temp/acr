package com.serd.cashregister.rest.generics;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class GetTemplate<T> extends AsyncTask<Void, Void, T> {

    public interface Listener<T> {
        public void onGetResponse(T object);
    }

    final Class<T> typeParameterClass;
    private List<Listener> mListeners = new ArrayList<Listener>();
    private String mUrl;

    public GetTemplate(Class<T> typeParameterClass, String url)
    {
        this.typeParameterClass = typeParameterClass;
        this.mUrl = url;
    }

    public void registerListener(Listener listener)
    {
        mListeners.add(listener);
    }

    @Override
    protected T doInBackground(Void... params) {
        try {
            //final String url = "http://tomas.frankl.sweb.cz/rests2.xml";
            RestTemplate restTemplate = new RestTemplate();
            SimpleXmlHttpMessageConverter converter = new SimpleXmlHttpMessageConverter();
            restTemplate.getMessageConverters().add(converter);
            T pluGroups = restTemplate.getForObject(mUrl, typeParameterClass);
            return pluGroups;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(T pluGroups) {
        for (Listener listener : mListeners) {
            listener.onGetResponse(pluGroups);
        }
    }
}
