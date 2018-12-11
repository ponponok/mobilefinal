package th.ac.kmitl.a59070017;

import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Toast;

public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerAccount();
    }

    private void registerAccount(){

        Button registerBtn = getView().findViewById(R.id.register_btn);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userid = getView().findViewById(R.id.register_userid);
                EditText _name = getView().findViewById(R.id.register_name);
                EditText _age = getView().findViewById(R.id.register_age);
                EditText _password = getView().findViewById(R.id.register_password);
                String _useridStr = _userid.getText().toString();
                String _nameStr = _name.getText().toString();
                String _ageStr = _age.getText().toString();
                String _passwordStr = _password.getText().toString();
                if(_useridStr.isEmpty() || _nameStr.isEmpty() || _ageStr.isEmpty() || _passwordStr.isEmpty()  ){
                    Toast.makeText(getActivity(), "plase fill all ", Toast.LENGTH_SHORT).show();

                }else if(_useridStr.length() < 6 || _userid.length() > 12){
                    Toast.makeText(getActivity(), "user id length must in lenght 6-12", Toast.LENGTH_SHORT).show();

                }else if(Integer.parseInt(_ageStr) < 10 || Integer.parseInt(_ageStr) > 80 ){
                    Toast.makeText(getActivity(), "age must in lengh 10-80", Toast.LENGTH_SHORT).show();

                }else if(_passwordStr.length() <= 6){
                    Toast.makeText(getActivity(), "password lenght must more 6 character", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getActivity(), "success ", Toast.LENGTH_SHORT).show();
                    SQLiteDatabase myDB = getActivity().openOrCreateDatabase("mydb", Context.MODE_PRIVATE, null);
                    myDB.execSQL("CREATE TABLE IF NOT EXISTS user(userid VARCHAR(200) PRIMARY KEY, name VARCHAR(200), age VARCHAR(200), password VARCHAR(200) , quote VARCHAR(200))");
                    ContentValues cv = new ContentValues();
                    cv.put("userid", _useridStr);
                    cv.put("name", _nameStr);
                    cv.put("age", _ageStr);
                    cv.put("password", _passwordStr);
                    cv.put("quote", "");

                    myDB.insert("user", null, cv);
                    myDB.close();


                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}
