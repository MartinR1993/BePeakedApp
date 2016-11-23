package project.martin.bepeakedprojekt.Backend;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by SteamedCow on 23/11-16.
 * Original created by Ravi Tamada on 01/09/16.
 * www.androidhive.info
 */
class HttpHandler
{
    private static final String TAG = HttpHandler.class.getSimpleName();

    static String makeServiceCall(String reqUrl) {
        String response = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(reqUrl).openConnection();
            conn.setRequestMethod("GET");

            // read the response
            final InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private static String convertStreamToString(InputStream is) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        catch (IOException e) {
            Log.e(TAG, "IO Exception: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                Log.e(TAG, "IO Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}