
package com.fat.mah.listeners;

import com.fat.mah.Post;

public interface OnPostChangedListener {
    public void onObjectChanged(Post obj);

    public void onError(String errorText);
}
