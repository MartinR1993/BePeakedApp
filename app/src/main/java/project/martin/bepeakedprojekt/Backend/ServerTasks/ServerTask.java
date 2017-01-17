package project.martin.bepeakedprojekt.Backend.ServerTasks;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Lasse on 17/01-17.
 */

public abstract class ServerTask implements IServerTask {
    private final String host;
    private final int port;

    public ServerTask(String host, int port) {
        this.host = host;
        this.port = port;
    }

    protected JSONObject sendRequest(String request) throws JSONException, IOException {
        InputStream connIS = null;
        PrintWriter connOut = null;
        BufferedReader connBR = null;

        try (Socket socket = new Socket(host, port)) {
            connIS = socket.getInputStream();
            connOut = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("REQUEST=" + request);
            connOut.print(request + '\n');
            connOut.flush();

            connBR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = connBR.readLine();
            System.out.println("REPLY=" + input);

            if(input != null)
                return new JSONObject(input);
            else
                return null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connIS != null)
                try {
                    connIS.close();
                } catch (IOException ex) {
                    System.err.println("Could not close input stream!");
                    ex.printStackTrace();
                }

            if(connOut != null) {
                connOut.flush();
                connOut.close();
            }

            if(connBR != null)
                try {
                    connBR.close();
                }
                catch (IOException ex) {
                    System.err.println("Could not close buffered reader!");
                    ex.printStackTrace();
                }
        }
        throw new IOException("Could not get reply from server!");
    }
}