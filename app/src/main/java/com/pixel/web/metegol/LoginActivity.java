package com.pixel.web.metegol;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pixel.web.metegol.model.UserClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@pixel.com:hello", "bar@pixel.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    //register usuario
    private EditText name_text;
    private EditText mail_text;
    private EditText password_text;
    private EditText cellphone_text;
    private FirebaseFirestore firestoreDB;
    public  FirebaseAuth firebaseAuth;

    FirebaseAuth.AuthStateListener Listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
        firestoreDB = FirebaseFirestore.getInstance();
        //inicializamos el objeto firebaseAuth
         firebaseAuth = FirebaseAuth.getInstance();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button register_new_button = (Button) findViewById(R.id.new_user_button);
        register_new_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                register_user_dialog();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user==null){
                    //no esta logeado
                }
                else{
                    //esta logeado
                    //Toast.makeText(getApplicationContext(), "CORRECTO Y LOGEADO",Toast.LENGTH_LONG).show();
                    Intent miintent= new Intent(getApplicationContext(),MainActivity.class);
                   // miintent.putExtra(FirebaseAuth,firebaseAuth);

                    //  miintent.putExtra("nombres",nombresx);
                    startActivity(miintent);
                    finish();
                }
            }
        };

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(Listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Listener!=null){
            firebaseAuth.addAuthStateListener(Listener);
        }

    }

    private void addUserFire(String email,String password){
        //registramos un nuevo usuario

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "Se ha registrado el usuario con el email: " , Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(LoginActivity.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "No se pudo registrar el usuario "+task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                              Log.e("error",""+task.getException().getMessage().toString());
                            }
                        }
                      //  progressDialog.dismiss();
                    }
                });
    }
    private void updateNote(String id, String title, String content) {
        /*Map<String, Object> note = (new Note(id, title, content)).toMap();

        firestoreDB.collection("notes")
                .document(id)
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Note document update successful!");
                        Toast.makeText(getApplicationContext(), "Note has been updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Note document", e);
                        Toast.makeText(getApplicationContext(), "Note could not be updated!", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    private void addUser(String name, String email, String clave, String cellphone) {
        Map<String, Object> users = new UserClass(name, email,clave,cellphone).toMap();

        firestoreDB.collection("users")
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       // Log.e("Agregado", "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Registrado!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Orror", "Error adding Note document", e);
                       // Toast.makeText(getApplicationContext(), "Note could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //PARA EL DIALOGO
    public void register_user_dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewx  = this.getLayoutInflater().inflate(R.layout.register_user_form, null);
         final AlertDialog alertDialog;
        //detalleHistorialDialog.setCancelable(false);

        builder.setView(viewx);
        builder.setTitle("Registro de Usuario");
       // builder.setCancelable(false);
        name_text=(EditText)viewx.findViewById(R.id.name_user);
        mail_text=(EditText)viewx.findViewById(R.id.mail_user);
        password_text=(EditText)viewx.findViewById(R.id.password_user);
        cellphone_text=(EditText)viewx.findViewById(R.id.cellphone_user);

        Button register_button = (Button) viewx.findViewById(R.id.button_register_user);


        name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                register_user();
            }
        });

        mail_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                register_user();
            }
        });

        password_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                register_user();
            }
        });

        cellphone_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                register_user();
            }
        });
        /*btnCerrar = (Button)viewx.findViewById(R.id.buttonCerrar);
        btnCerrar.setEnabled(true);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalleHistorialDialog.cancel();
            }
        });*/
        //  Toast.makeText( this.getApplicationContext(), "id: ", Toast.LENGTH_SHORT).show();
        alertDialog = builder.create();
        alertDialog.show();
        register_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(register_user()){
                    addUserFire( mail_text.getText().toString().trim(),password_text.getText().toString().trim());
                    //registramos
                    addUser(name_text.getText().toString().trim(), mail_text.getText().toString().trim(),
                            password_text.getText().toString().trim(), cellphone_text.getText().toString().trim());
                    alertDialog.cancel();
                }
            }
        });
    }
    private boolean register_user(){
        boolean valido=true;
        if(name_text.getText().toString().trim().equals("")){
            //Log.d("cordenadaxxxx", "vacio");
            Drawable ic_right_name = getResources().getDrawable(R.drawable.ic_baseline_error_24px);
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_person_outline_black_24dp);
            name_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,ic_right_name, null );
            valido=false;
        }else{
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_person_outline_black_24dp);
            name_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,null, null );
        }
        //mail
        if(mail_text.getText().toString().trim().equals("")){
            valido=false;
            Drawable ic_right_name = getResources().getDrawable(R.drawable.ic_baseline_error_24px);
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_baseline_mail_outline_24px);
            mail_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,ic_right_name, null );

        }else{
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_baseline_mail_outline_24px);
            mail_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,null, null );
        }
        //clave
        if(password_text.getText().toString().trim().equals("")||password_text.getText().toString().length()<6){
            valido=false;
            Drawable ic_right_name = getResources().getDrawable(R.drawable.ic_baseline_error_24px);
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_baseline_lock_24px);
            password_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,ic_right_name, null );

        }else{
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_baseline_lock_24px);
            password_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,null, null );
        }
        //celular
        if(cellphone_text.getText().toString().trim().equals("")){
            valido=false;
            Drawable ic_right_name = getResources().getDrawable(R.drawable.ic_baseline_error_24px);
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_baseline_phone_24px);
            cellphone_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,ic_right_name, null );

        }else{
            Drawable is_left_name = getResources().getDrawable(R.drawable.ic_baseline_phone_24px);
            cellphone_text.setCompoundDrawablesWithIntrinsicBounds( is_left_name, null,null, null );
        }
        return valido;

    }
    private void printKeyHash(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.pixel.web.metegol",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            // TODO: alert the user with a Snackbar/AlertDialog giving them the permission rationale
            // To use the Snackbar from the design support library, ensure that the activity extends
            // AppCompatActivity and uses the Theme.AppCompat theme.
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        printKeyHash();
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //ingresar
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                Intent miintent= new Intent(getApplicationContext(),MainActivity.class);
                                //miintent.putExtra("nombres","hola");
                                //  miintent.putExtra("nombres",nombresx);
                                startActivity(miintent);
                                finish();
                           /* int pos = email.indexOf("@");
                            String user = email.substring(0, pos);
                            Toast.makeText(LoginActivity.this, "Bienvenido: " , Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), WellcomeActivity.class);
                            intencion.putExtra(WellcomeActivity.user, user);
                            startActivity(intencion);*/

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                    Toast.makeText(LoginActivity.this, "Error de red ", Toast.LENGTH_SHORT).show();
                                } else {
                                    showProgress(false);
                                    Toast.makeText(LoginActivity.this, "Incorrecto"+task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                            // progressDialog.dismiss();
                        }
                    });

           // mAuthTask = new UserLoginTask(email, password);
           // mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

