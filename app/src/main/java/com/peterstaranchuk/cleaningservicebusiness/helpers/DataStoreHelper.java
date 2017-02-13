package com.peterstaranchuk.cleaningservicebusiness.helpers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.objects.LocalPersistence;

import ru.profit_group.scorocode_sdk.scorocode_objects.DocumentInfo;


/**
 * Created by Peter Staranchuk.
 */

public class DataStoreHelper {
    private final Context context;
    private DocumentInfo userInfo;

    public DataStoreHelper(Context context) {
        this.context = context;
    }


    public void storeUserInfo(DocumentInfo userInfo) {
        LocalPersistence.witeObjectToFile(context, userInfo, LocalPersistence.FILE_USER_INFO);
    }

    public DocumentInfo getUserInfo() {
        if(userInfo != null) {
            return userInfo;
        }

        return (DocumentInfo) LocalPersistence.readObjectFromFile(context, LocalPersistence.FILE_USER_INFO);
    }

    public void clearUserData() {
        storeUserInfo(new DocumentInfo());
    }

    @NonNull
    public String getUserName() {
        return String.valueOf(getUserInfo().get(context.getString(R.string.key_username)));
    }

    @NonNull
    public String getUserId() {
        return String.valueOf(getUserInfo().get(context.getString(R.string.key_user_id)));
    }

    @NonNull
    public String getUserPhone() {
        return String.valueOf(getUserInfo().get(context.getString(R.string.key_user_phone)));
    }

}
