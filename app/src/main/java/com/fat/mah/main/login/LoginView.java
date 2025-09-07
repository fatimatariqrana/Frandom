
package com.fat.mah.main.login;

import com.google.firebase.auth.AuthCredential;
import com.fat.mah.main.base.BaseView;


public interface LoginView extends BaseView {
    void startCreateProfileActivity();

    void signInWithGoogle();

    void signInWithFacebook();

    void setProfilePhotoUrl(String url);

    void firebaseAuthWithCredentials(AuthCredential credential);
}
