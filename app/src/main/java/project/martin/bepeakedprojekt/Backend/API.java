package project.martin.bepeakedprojekt.Backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class API {
    private static final String TAG = API.class.getSimpleName();

    public static void getImages(int exerciseID, ImageView... ivs) {
        Log.d(TAG, "API call started");
        new GetImages(exerciseID, ivs).execute();
    }

    private static class GetImages extends AsyncTask<String, Void, Bitmap[]>
    {
        private static final String apiUrl = "https://wger.de/api/v2/exerciseimage/?exercise=";
        private static final String apiUrlPostfix = "&format=json";
        private final ImageView[] ivs;
        private final int exerciseID;

        private GetImages(int exerciseID, ImageView... ivs) {
            this.exerciseID = exerciseID;
            this.ivs = ivs;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap[] doInBackground(String... arg0) {
            final Bitmap[] images = new Bitmap[ivs.length];

            // Making a request to url and getting JSON object string for images
            String jsonStr = HttpHandler.makeServiceCall(apiUrl + exerciseID + apiUrlPostfix);
            Log.d(TAG, "Response from http handler: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //Read image urls from JSON object string
                    final JSONObject jsonObj = new JSONObject(jsonStr);
                    final JSONArray results = jsonObj.getJSONArray("results");

                    final String[] imageUrls = new String[results.length()];
                    for (int i = 0; i < results.length(); i++) {
                        imageUrls[i] = ((JSONObject) results.get(i)).getString("image");
                    }

                    //Compare imageView size with image url size.
                    if(ivs.length > imageUrls.length)
                        throw new ArrayIndexOutOfBoundsException("Less images returned than ImageViews: images=" + imageUrls.length + ", ImageViews=" + ivs.length + ", imageID=" + exerciseID);
                    else if (ivs.length < imageUrls.length)
                        Log.w(TAG, "More images returned than ImageViews: images=" + imageUrls.length + ", ImageViews=" + ivs.length);

                    //Download and images
                    InputStream in;
                    for (int i = 0; i < ivs.length; i++) {
                        in = new java.net.URL(imageUrls[i]).openStream();
                        images[i] = BitmapFactory.decodeStream(in);
                        in.close();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e.getMessage());
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "Malformed URL Exception: " + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e(TAG, "IO Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
            }
            return images;
        }

        @Override
        protected void onPostExecute(Bitmap[] result) {
            if (result != null) {
                if(result.length > 0) {
                    for (int i = 0; i < ivs.length; i++) {
                        ivs[i].setImageBitmap(result[i]);
                    }
                    Log.d(TAG, ivs.length + " images was set from API call");
                }
                else
                    Log.e(TAG, "No images was loaded");
            }
            Log.d(TAG, "API call complete");
        }
    }
}