package agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import agustinreinosowerewolf.com.werewolfgamemoderator.googleservices.GoogleAuthManager;

public class UserViewModel extends AndroidViewModel {
    private LiveData<GoogleAuthManager> mAuthManager;


    private MutableLiveData<Boolean> isLoading;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<GoogleAuthManager> getmAuthManager() {
        if (mAuthManager == null) {
            mAuthManager = new MutableLiveData<>();
            ((MutableLiveData<GoogleAuthManager>) mAuthManager).setValue(new GoogleAuthManager());
            sign();
        }
        return mAuthManager;
    }


    public void sign() {
        mAuthManager.getValue().authGoogle(getApplication());
    }

    public MutableLiveData<Boolean> getIsLoading() {

        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
            ((MutableLiveData<Boolean>) isLoading).setValue(false);
        }
        return isLoading;
    }

    public boolean   isLogged()
    {
        return mAuthManager.getValue().isLogged(getApplication());
    }
    public void signOut() {
        mAuthManager.getValue().mGoogleapiClient.signOut();

    }


}
