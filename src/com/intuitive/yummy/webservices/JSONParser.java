package com.intuitive.yummy.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    public JSONParser() {}
 
    public JSONObject makeHttpRequest(String url, String method,
            ArrayList<PostParameter> params, String jsonString) {
 
        // Making HTTP request
        try {
        	
            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                if(params != null) httpPost.setEntity(new UrlEncodedFormEntity(params));
 
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
 
            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                // TODO check response code
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }else if(method.equals("JSON")){
            	DefaultHttpClient httpClient = new DefaultHttpClient();
            	HttpPost request = new HttpPost(url);
            	
            	ArrayList<PostParameter> postList = new ArrayList<PostParameter>();
            	postList.add(new PostParameter("app_data", jsonString));
            	request.setEntity(new UrlEncodedFormEntity(postList));
            	
            	//Log.d("request data", new UrlEncodedFormEntity(postList).toString());
                HttpResponse httpResponse = httpClient.execute(request);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
        	e.printStackTrace();
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            Log.d("JSON Parser", "response = " + json.toString());
        }
 
        return jObj;
 
    }
}