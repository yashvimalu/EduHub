package com.example.gyaandaan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class TeacherFragment extends Fragment {
    TextInputLayout uname,emailid,number,password,conf_password;
    Button register;
    float v=0;
    FirebaseDatabase rootFD;
    DatabaseReference reference;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.teacher_fragment,container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        uname = root.findViewById(R.id.name);
        emailid = root.findViewById(R.id.email);
        number = root.findViewById(R.id.mobileNo);
        password = root.findViewById(R.id.password);
        conf_password = root.findViewById(R.id.confirm_password);
        register = root.findViewById(R.id.register);

        uname.setAlpha(v);
        emailid.setAlpha(v);
        number.setAlpha(v);
        password.setAlpha(v);
        conf_password.setAlpha(v);

        uname.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(200).start();
        emailid.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        number.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
        password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(800).start();
        conf_password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(800).start();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailid.clearFocus();
                number.clearFocus();
                password.clearFocus();
                conf_password.clearFocus();
                uname.clearFocus();

                final String fullname = uname.getEditText().getText().toString();
                final String email = emailid.getEditText().getText().toString();
                final String mob_no= number.getEditText().getText().toString();
                final String pwd= password.getEditText().getText().toString();
                final String conf_pwd = conf_password.getEditText().getText().toString();

                if(fullname.equals("")){
                    uname.requestFocus();
                    uname.setError("Enter fullname");
                    emailid.clearFocus();
                    number.clearFocus();
                    password.clearFocus();
                    conf_password.clearFocus();

                    emailid.setError(null);
                    number.setError(null);
                    password.setError(null);
                    conf_password.setError(null);
                }
                else if(email.equals("")){
                    uname.clearFocus();
                    number.clearFocus();
                    password.clearFocus();
                    conf_password.clearFocus();

                    uname.setError(null);
                    number.setError(null);
                    password.setError(null);
                    conf_password.setError(null);

                    emailid.requestFocus();
                    emailid.setError("Enter email");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    uname.clearFocus();
                    number.clearFocus();
                    password.clearFocus();
                    conf_password.clearFocus();

                    emailid.setError("Please enter a valid email address");
                    emailid.requestFocus();
                }
                else if(mob_no.equals("")){
                    uname.clearFocus();
                    emailid.clearFocus();
                    password.clearFocus();
                    conf_password.clearFocus();

                    uname.setError(null);
                    emailid.setError(null);
                    password.setError(null);
                    conf_password.setError(null);

                    number.requestFocus();
                    number.setError("Enter 10 digit mobile number");
                }
                else if(mob_no.length()<1 || mob_no.length()<10 || mob_no.length()>11){
                    emailid.clearFocus();
                    uname.clearFocus();
                    password.clearFocus();
                    conf_password.clearFocus();

                    uname.setError(null);
                    emailid.setError(null);
                    password.setError(null);
                    conf_password.setError(null);

                    number.requestFocus();
                    number.setError("Enter 10 digit mobile number");
                }
                else if(pwd.equals("")){
                    emailid.clearFocus();
                    number.clearFocus();
                    uname.clearFocus();
                    conf_password.clearFocus();

                    uname.setError(null);
                    number.setError(null);
                    emailid.setError(null);
                    conf_password.setError(null);

                    password.requestFocus();
                    password.setError("Enter password");
                }
                else if (!PASSWORD_PATTERN.matcher(pwd).matches()) {
                    emailid.clearFocus();
                    number.clearFocus();
                    uname.clearFocus();
                    conf_password.clearFocus();


                    uname.setError(null);
                    number.setError(null);
                    emailid.setError(null);
                    conf_password.setError(null);

                    password.setError("Password is too weak");
                    password.requestFocus();
                }
                else if(!(pwd.equals(conf_pwd)) ){
                    emailid.clearFocus();
                    number.clearFocus();
                    password.clearFocus();
                    uname.clearFocus();


                    uname.setError(null);
                    number.setError(null);
                    password.setError(null);
                    emailid.setError(null);

                    conf_password.setError("Passwords do not match");
                    conf_password.requestFocus();
                }
                else{
                    rootFD = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/");
                    reference = rootFD.getReference().child("Teacher");
                    MemberDetails details = new MemberDetails(fullname, email, pwd, mob_no);
                    reference.child(fullname).setValue(details);

                    reference = rootFD.getReference().child("Users");
                    UserTable table = new UserTable(fullname,"Teacher");
                    reference.child(fullname).setValue(table);

                    Toast.makeText(getActivity().getApplicationContext(),"Registered Successfully!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), Welcome.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        return root;
    }
}