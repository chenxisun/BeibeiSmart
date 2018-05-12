package edu.buaa.beibeismart.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.buaa.beibeismart.Activity.Baike_Dongwu_Activity;
import edu.buaa.beibeismart.Activity.Baike_Shucai_Activity;
import edu.buaa.beibeismart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShucaiFragment extends Fragment {

    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shucai, container, false);
        btn = (Button) v.findViewById(R.id.btn_shucai);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Baike_Shucai_Activity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}
