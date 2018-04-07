package com.example.amusuopaschal.mymed;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amusuopaschal.mymed.RoomDatabase.Medication;
import com.example.amusuopaschal.mymed.RoomDatabase.MedicationDatabase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient googleSignInClient;
    private TextView textView;

    private Cursor cursor;

    private RecyclerView recyclerView;
    private MyMedAdapter myMedAdapter;
    MedicationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.tv_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myMedAdapter = new MyMedAdapter(this, cursor);
        recyclerView.setAdapter(myMedAdapter);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAndDisplayItem();
            }
        });

        if (database == null){
            database = Room.databaseBuilder(
                    getApplicationContext(), MedicationDatabase.class, "Medication_Db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

    }

    Medication test;
    String testName;
    private void insertAndDisplayItem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.medicationDao().insertAll(new Medication(1, "Malaria"));
                test = database.medicationDao().findById(1);
                testName = test.getMedName();
            }
        });
        textView.setText(testName);



    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null){
            Log.d(TAG, "No last signed in account");

        } else {
            String user = "User signed in: " +account.getEmail();
            textView.setText(user);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sign_in){
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String user = "User signed in: " +account.getEmail();
            textView.setText(user);

        } catch (ApiException e) {
            e.printStackTrace();
            Log.d(TAG, "Sign in not successful");
            Toast.makeText(this, "Sign in not successful", Toast.LENGTH_SHORT).show();
        }
    }
}
