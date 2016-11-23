package project.martin.bepeakedprojekt.Backend;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Martin on 22-11-2016.
 */
public class API {
    private final String TAG = getClass().getSimpleName();

    public void getImages() {
        Log.w(TAG, "! API STARTED !");
//        HttpHandler httpHandler = new HttpHandler();
//        JSONObject jsonObj = parseJSON(httpHandler.makeServiceCall("https://wger.de/api/v2/exerciseimage/?exercise=74&format=json"));
//        System.err.println(jsonObj.toString());
        GetImages getImgaes = new GetImages();
        getImgaes.execute();
        Log.w(TAG, "! API COMPLETE !");
    }




    private class GetImages extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... arg0) {
            Log.w(TAG, "! ASYNC STARTED !");
            HttpHandler httpHandler = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = httpHandler.makeServiceCall("https://wger.de/api/v2/exerciseimage/?exercise=74&format=json");

            Log.i(TAG, "Response from http handler: " + jsonStr);

            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.i(TAG, "! JSON: " + jsonObj.toString() + " !");

                    JSONArray results = jsonObj.getJSONArray("results");

                    String[] imageUrls = new String[results.length()];
                    for (int i = 0; i < results.length(); i++) {
                        imageUrls[i] = ((JSONObject) results.get(i)).getString("image");
                    }

                    Log.i(TAG, "! IMAGE URL'S !");
                    for (String imageUrl: imageUrls) {
                        Log.i(TAG, imageUrl);
                    }
                }
                catch (JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e.getMessage());
                    e.printStackTrace();
                }

                // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
//                    for (int i = 0; i < contacts.length(); i++) {
//                        JSONObject c = contacts.getJSONObject(i);
//
//                        String id = c.getString("id");
//                        String name = c.getString("name");
//                        String email = c.getString("email");
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");
//
//                        // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = phone.getString("mobile");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");
//
//                        // tmp hash map for single contact
//                        HashMap<String, String> contact = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        contact.put("id", id);
//                        contact.put("name", name);
//                        contact.put("email", email);
//                        contact.put("mobile", mobile);
//
//                        // adding contact to contact list
//                        contactList.add(contact);
//                    }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /**
             * Updating parsed JSON data into ListView
             * */
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, contactList,
//                    R.layout.list_item, new String[]{"name", "email",
//                    "mobile"}, new int[]{R.id.name,
//                    R.id.email, R.id.mobile});
//
//            lv.setAdapter(adapter);
            Log.w(TAG, "! ASYNC COMPLETE !");
        }

    }
}
