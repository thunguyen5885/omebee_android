package com.omebee.android.global;

import android.content.Context;

import com.omebee.android.R;
import com.omebee.android.layers.services.models.BrandWSModel;
import com.omebee.android.layers.services.models.CategoryWSModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phan on 10/3/2014.
 */
public class AppModel {
    private static final String TAG = "AppModel";
    private Context mContext;

    public AppModel(Context context){
        mContext = context;
    }


    public JSONObject getUpdatedCategoryTree(){

        return createDumpDataForCategory();
    }
    private JSONObject createDumpDataForCategory(){

        InputStream is = mContext.getResources().openRawResource(R.raw.category);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }catch (UnsupportedEncodingException e) {
        }
        catch (IOException e) {
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            return jsonObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
