package com.example.khokhlovart_loftschool.moneytracker;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.khokhlovart_loftschool.moneytracker.Api.Api;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.List;

public class AuthActivity extends AppCompatActivity {
    GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 777;
    private static final int LOADER_ITEMS_AUTHORIZATION = 1;
    private static GoogleSignInAccount account;

    public static GoogleSignInAccount getAccount(){return account;}
    public static void setAccount(GoogleSignInAccount acc){account = acc;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestEmail()
                                        .build();
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        //CardView buttonSingIn = (CardView) findViewById(R.id.btn_auth);
        ImageButton buttonSingIn = (ImageButton) findViewById(R.id.btn_auth);
        buttonSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess() && result.getSignInAccount() != null) {
               // final GoogleSignInAccount account = result.getSignInAccount();

                setAccount(result.getSignInAccount());
                getSupportLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<AuthRes>() {
                            @Override
                            public Loader<AuthRes> onCreateLoader(int id, Bundle args) {
                                return new AsyncTaskLoader<AuthRes>(AuthActivity.this) {
                                    @Override
                                    public AuthRes loadInBackground() {
                                        try {
                                            AuthRes aRes = ((App) getApplicationContext()).getApi().auth(account.getId()).execute().body();
                                            return aRes;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                };
                            }

                            @Override
                            public void onLoadFinished(Loader<AuthRes> loader, AuthRes result) {
                                if (result != null) {
                                    ((App) getApplicationContext()).setAuthToken(result.authToken);
                                    finish();
                                } else {

                                }

                            }

                            @Override
                            public void onLoaderReset(Loader<AuthRes> loader) {

                            }
                        }
                ).forceLoad();


            }
        }
        else
        {
            Toast.makeText(this, "Авторизация не удалась!", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
