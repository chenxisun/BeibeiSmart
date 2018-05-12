package edu.buaa.beibeismart.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.buaa.beibeismart.Activity.Baike_Dongwu_Activity;
import edu.buaa.beibeismart.Activity.MainActivity;
import edu.buaa.beibeismart.R;
import android.content.Intent;

/**
 * A simple {@link Fragment} subclass.
 */
public class DongwuFragment extends Fragment{

    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dongwu, container, false);
        btn =(Button)v.findViewById(R.id.btn_dongwu);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getActivity(),Baike_Dongwu_Activity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}
