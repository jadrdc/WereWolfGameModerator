package agustinreinosowerewolf.com.werewolfgamemoderator.googleservices;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleAuthManager {
    public GoogleApiClient mGoogleapiClient;

    public GoogleAuthManager() {

    }

    public GoogleApiClient authGoogle(Context context)
    {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().build();
        mGoogleapiClient = new GoogleApiClient.Builder(context).addApi(Auth.GOOGLE_SIGN_IN_API,options).build();
        return mGoogleapiClient;
    }
}
