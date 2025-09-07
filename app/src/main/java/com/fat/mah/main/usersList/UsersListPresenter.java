
package com.fat.mah.main.usersList;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.fat.mah.R;
import com.fat.mah.main.base.BasePresenter;


class UsersListPresenter extends BasePresenter<UsersListView> {


    private String currentUserId;
    private Activity activity;

    UsersListPresenter(Activity activity) {
        super (activity);
        this.activity = activity;


        currentUserId = FirebaseAuth.getInstance ().getUid ();
    }


    public void onRefresh(String userId, int userListType) {
    //   loadUsersList(userId, userListType, true);
    }

     public void loadUsersList(String userId, int userListType, boolean isRefreshing) {
    //     if (userListType == UsersListType.FOLLOWERS) {
    //       loadFollowers(userId, isRefreshing);
    //  } else if (userListType == UsersListType.FOLLOWINGS) {
    //    loadFollowings(userId, false);
    //}
    }

    public void chooseActivityTitle(int userListType) {
        ifViewAttached (view -> {
            if (userListType == UsersListType.FOLLOWERS) {
                view.setTitle (R.string.title_followers);
            } else if (userListType == UsersListType.FOLLOWINGS) {
                view.setTitle (R.string.title_followings);
            }
        });

    }
}

