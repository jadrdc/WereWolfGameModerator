package agustinreinosowerewolf.com.werewolfgamemoderator.googleservices;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class GoogleAuthManager {
    public GoogleSignInClient mGoogleapiClient;

    public GoogleAuthManager() {

    }

    public GoogleSignInClient authGoogle(Context context) {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().requestIdToken("104577148857-mln5c4ul71g74vgkirhfv76fc3hf4mhf.apps.googleusercontent.com")
                .build();
        //   mGoogleapiClient = new GoogleApiClient.Builder(context).addApi(Auth.GOOGLE_SIGN_IN_API,options).build();
        mGoogleapiClient = GoogleSignIn.getClient(context, options);
        return mGoogleapiClient;
    }

    public GoogleSignInClient authGame(Context context) {/*
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail().
                requestScopes(Games.SCOPE_GAMES_LITE).build();*/
        return GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
    }

    public void logOut() {
    }


}
