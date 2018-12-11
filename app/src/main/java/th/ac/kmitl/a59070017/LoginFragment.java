package th.ac.kmitl.a59070017;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences share  = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String a = share.getString("name", null);
        if(!(a == null)){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        }
        Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
        login();
        register();

    }
    private void login(){
        Button _loginBtn = getView().findViewById(R.id.login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userid = getView().findViewById(R.id.login_userid);
                EditText _password = getView().findViewById(R.id.login_password);
                String _useridStr = _userid.getText().toString();
                String _passwordStr = _password.getText().toString();
                SQLiteDatabase myDB = getActivity().openOrCreateDatabase("mydb", Context.MODE_PRIVATE, null);
                Cursor myCursor = myDB.rawQuery("select userid, name, age, password, quote from user where userid = "+"'"+_useridStr+"' and password = '"+_passwordStr+"'", null);

                if(myCursor.getCount() > 0){
                    myCursor.moveToFirst();
                    Toast.makeText(getActivity(), myCursor.getString(0)+" "+myCursor.getString(1), Toast.LENGTH_SHORT).show();
                    SharedPreferences share  = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = share.edit();
                    ed.putString("userid",myCursor.getString(0));
                    ed.putString("age", myCursor.getString(2));
                    ed.putString("name", myCursor.getString(1));
                    ed.putString("password", myCursor.getString(3));
                    ed.putString("quote", myCursor.getString(4));
                    ed.commit();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                }


                //Toast.makeText(getActivity(), String.valueOf(myCursor.getCount()), Toast.LENGTH_SHORT).show();

                myCursor.close();
                myDB.close();
                //final Cursor myCursor = myDB.rawQuery("select date, sleep, wake, _id from sleep where uid = "+"'"+_auth.getCurrentUser().getUid().toString()+"'", null);
            }
        });

    }
    private void register(){
        TextView _registerBtn = getView().findViewById(R.id.login_register);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
