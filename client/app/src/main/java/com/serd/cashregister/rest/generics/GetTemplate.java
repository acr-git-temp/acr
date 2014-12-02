package com.serd.cashregister.rest.generics;

import android.os.AsyncTask;

import com.serd.cashregister.rest.sync.IErrorHandler;
import com.serd.cashregister.rest.sync.SerdRestTemplate;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class GetTemplate<T> extends AsyncTask<Void, Void, T> {

    public interface Listener<T> {
        public void onGetResponse(T object);
    }

    final Class<T> typeParameterClass;
    private List<Listener> mListeners = new ArrayList<Listener>();
    private String mUrl;
    private String mError;
    private IErrorHandler mErrorHandler;

    public GetTemplate(Class<T> typeParameterClass, String url)
    {
        this.typeParameterClass = typeParameterClass;
        this.mUrl = url;
    }

    public void registerListener(Listener listener)
    {
        mListeners.add(listener);
    }

    public void registerErrorHandler(IErrorHandler errorHandler)
    {
        mErrorHandler = errorHandler;
    }

    @Override
    protected T doInBackground(Void... params) {
        T result = null;
        try {
            //final String url = "http://tomas.frankl.sweb.cz/rests2.xml";
            SerdRestTemplate restTemplate = new SerdRestTemplate();
            SimpleXmlHttpMessageConverter converter = new SimpleXmlHttpMessageConverter();
            restTemplate.getMessageConverters().add(converter);
            result = restTemplate.getForObject(mUrl, typeParameterClass);
            return result;
        } catch (Exception e) {
            mError = e.getMessage();
            //Log.e("MainActivity", e.getMessage(), e);
        }
        return result;
    }

    @Override
    protected void onPostExecute(T object) {
        if (object == null) {
            mErrorHandler.onError(mError);
        }
        else {
            for (Listener listener : mListeners) {
                listener.onGetResponse(object);
            }
        }
    }
}
