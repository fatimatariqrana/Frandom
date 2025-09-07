
package com.fat.mah.main.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;

import com.fat.mah.R;
import com.fat.mah.PostStatus;
import com.fat.mah.main.base.BasePresenter;
import com.fat.mah.main.postDetails.PostDetailsActivity;
import com.fat.mah.PostManager;
import com.fat.mah.ProfileManager;
import com.fat.mah.listeners.OnObjectChangedListenerSimple;
import com.fat.mah.Post;
import com.fat.mah.Profile;


class ProfilePresenter extends BasePresenter<ProfileView> {


    private Activity activity;
    private ProfileManager profileManager;

    private Profile profile;

    ProfilePresenter(Activity activity) {
        super(activity);
        this.activity = activity;
        profileManager = ProfileManager.getInstance(context);

    }



    void onPostClick(Post post, View postItemView) {
        PostManager.getInstance(context).isPostExistSingleValue(post.getId(), exist -> {
            ifViewAttached(view -> {
                if (exist) {
                    view.openPostDetailsActivity(post, postItemView);
                } else {
                    view.showSnackBar(R.string.error_post_was_removed);
                }
            });
        });
    }

    public Spannable buildCounterSpannable(String label, int value) {
        SpannableStringBuilder contentString = new SpannableStringBuilder();
        contentString.append(String.valueOf(value));
        contentString.append("\n");
        int start = contentString.length();
        contentString.append(label);
        contentString.setSpan(new TextAppearanceSpan(context, R.style.TextAppearance_Second_Light), start, contentString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return contentString;
    }

    public void onEditProfileClick() {
        if (checkInternetConnection()) {
            ifViewAttached(ProfileView::startEditProfileActivity);
        }
    }

    public void onCreatePostClick() {
        if (checkInternetConnection()) {
            ifViewAttached(ProfileView::openCreatePostActivity);
        }
    }

    public void loadProfile(Context activityContext, String userID) {
        profileManager.getProfileValue(activityContext, userID, new OnObjectChangedListenerSimple<Profile>() {
            @Override
            public void onObjectChanged(Profile obj) {
                profile = obj;
                ifViewAttached(view -> {
                    view.setProfileName(profile.getUsername());

                    if (profile.getPhotoUrl() != null) {
                        view.setProfilePhoto(profile.getPhotoUrl());
                    } else {
                        view.setDefaultProfilePhoto();
                    }

                    int likesCount = (int) profile.getLikesCount();
                    String likesLabel = context.getResources().getQuantityString(R.plurals.likes_counter_format, likesCount, likesCount);
                    view.updateLikesCounter(buildCounterSpannable(likesLabel, likesCount));
                });
            }
        });
    }

    public void onPostListChanged(int postsCount) {
        ifViewAttached(view -> {
            String postsLabel = context.getResources().getQuantityString(R.plurals.posts_counter_format, postsCount, postsCount);
            view.updatePostsCounter(buildCounterSpannable(postsLabel, postsCount));
            view.showLikeCounter(true);
            view.showPostCounter(true);
            view.hideLoadingPostsProgress();


        });
        }

    public void checkPostChanges(Intent data) {
        ifViewAttached(view -> {
            if (data != null) {
                PostStatus postStatus = (PostStatus) data.getSerializableExtra(PostDetailsActivity.POST_STATUS_EXTRA_KEY);

                if (postStatus.equals(PostStatus.REMOVED)) {
                    view.onPostRemoved();
                } else if (postStatus.equals(PostStatus.UPDATED)) {
                    view.onPostUpdated();
                }
            }
        });
    }
}
