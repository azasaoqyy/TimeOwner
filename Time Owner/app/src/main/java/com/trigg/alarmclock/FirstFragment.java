package com.trigg.alarmclock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by encore on 2015/7/20.
 */
public class FirstFragment extends Fragment {

    private ProgressDialog pDialog;
    private static String url = "http://192.168.1.191:2222/advocates?account=johney0416&password=123456789";

    private static final String TAG_TITLE = "id";
    private static final String TAG_LOCATION = "message";
    private View v;
    private ListView listView;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contactList = new ArrayList<HashMap<String, String>>();
        v = inflater.inflate(R.layout.first_layout,container,false);
        listView = (ListView)v.findViewById(R.id.listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String title = ((TextView) view.findViewById(R.id.title))
                        .getText().toString();
                String location = ((TextView) view.findViewById(R.id.location))
                        .getText().toString();


                // Starting single contact activity
                Intent in = new Intent(getActivity(),
                        SingleContactActivity.class);
                in.putExtra(TAG_TITLE, title);
                in.putExtra(TAG_LOCATION, location);

                startActivity(in);

            }
        });

        // Calling async task to get json
        new GetContacts().execute();
        //return inflater.inflate(R.layout.first_layout,container,false);
        return v;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            /*pDialog = new ProgressDialog(FirstFragment.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler serviceHandler = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = serviceHandler.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    //contacts = jsonObj.getJSONArray(TAG_CONTACTS);
                    // looping through All Contacts


                    //String id = c.getString(TAG_ID);
                    String title = jsonObj.getString(TAG_TITLE);
                    String location = jsonObj.getString(TAG_LOCATION);
                    // tmp hashmap for single contact
                    HashMap<String, String> contact = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    //contact.put(TAG_ID, id);
                    contact.put(TAG_TITLE, title);
                    contact.put(TAG_LOCATION, location);

                    // adding contact to contact list
                    contactList.add(contact);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            /*if (pDialog.isShowing())
                pDialog.dismiss();*/
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), contactList,
                    R.layout.list_item, new String[] { TAG_TITLE, TAG_LOCATION},
                    new int[] { R.id.title, R.id.location });

            listView.setAdapter(adapter);
        }

    }



    
}
