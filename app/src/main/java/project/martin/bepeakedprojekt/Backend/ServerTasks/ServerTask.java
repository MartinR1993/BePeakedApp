package project.martin.bepeakedprojekt.Backend.ServerTasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
        catch (UnknownHostException e) {
            e.printStackTrace();
            JSONObject hostError = new JSONObject();
            hostError.put(ServerTags.TAG_ERROR, ServerTags.ERROR_NO_HOST);
            return hostError;
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

    public static void showMessageDialouge(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    protected void showConfirmDialoge(Context context, String title, String message, DialogInterface.OnClickListener yesAction, DialogInterface.OnClickListener noAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("YES", yesAction);
        builder.setNegativeButton("NO", noAction);

        AlertDialog alert = builder.create();
        alert.show();
    }
}