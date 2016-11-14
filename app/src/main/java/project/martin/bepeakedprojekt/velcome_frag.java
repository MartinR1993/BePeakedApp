package project.martin.bepeakedprojekt;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**d
 * Created by Martin on 09-11-2016.
 */

public class velcome_frag extends Fragment implements Runnable {
    Handler handler = new Handler();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.velcome, container, false);

        handler.postDelayed(this, 3000);

        return rod;
    }

    @Override
    public void run() {
        Intent i = new Intent(getActivity(), login_akt.class);
        startActivity(i);
    }
}
