package th.ac.kmitl.a59070017;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cre();
        signout();
        prifile();
    }

    private void cre(){
        TextView hello = getView().findViewById(R.id.home_hello);
        TextView qo = getView().findViewById(R.id.home_qout);
        SharedPreferences share  = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        hello.setText("Hello "+share.getString("name", ""));
        qo.setText("this is my quote"  +'"' + share.getString("quote", "") +'"' );
    }
    private void prifile(){
        Button proBtn = getView().findViewById(R.id.home_setup);
        proBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void signout(){
        Button outBtn = getView().findViewById(R.id.home_signout);
        outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences share  = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = share.edit();
                ed.putString("userid",null);
                ed.putString("age", null);
                ed.putString("name", null);
                ed.putString("password", null);
                ed.putString("quote", null);
                ed.commit();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
