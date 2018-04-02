package edu.buaa.beibeismart.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.buaa.beibeismart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment {


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;*/
        View view = inflater.inflate(R.layout.fragment_learnenglish_movie,container,false);
        return view;
    }

}
