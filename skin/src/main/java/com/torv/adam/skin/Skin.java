package com.torv.adam.skin;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by AdamLi on 2017/2/8.
 */

public enum Skin {
    instance;

    public static final String BUNDLE_KEY_PACKAGE_NAME = "bundle_key_package_name";
    public static final String SP_KEY_PACKAGE_NAME = "sp_key_package_name";

    static final String DEFTYPE_DRAWABLE = "drawable";
    static final String DEFTYPE_STRING = "string";
    static final String DEFTYPE_STYLE = "style";
    static final String DEFTYPE_LAYOUT = "layout";
    static final String DEFTYPE_ID = "id";
    static final String DEFTYPE_COLOR = "color";
    static final String DEFTYPE_RAW = "raw";
    static final String DEFTYPE_ANIM = "anim";
    static final String DEFTYPE_ATTR = "attr";

    private static final String TAG = Skin.class.getSimpleName();

    private String mCurrentPackageName;
    Resources mResources;

    public synchronized void init(Context context, String packageName) {
        if(null == context) {
            return;
        }
        String tempName = packageName;
        if(TextUtils.isEmpty(tempName)) {
            if(!TextUtils.isEmpty(mCurrentPackageName)) {
                Log.d(TAG, "skin not update and already init, return");
                return;
            } else {
                // not init, need init, first check if saved?
                tempName = getPackageNameFromSP(context);
                if(TextUtils.isEmpty(tempName)) {
                    // package name not saved, use default
                    tempName = context.getPackageName();
                }
            }
        } else {
            if(tempName.equalsIgnoreCase(mCurrentPackageName)) {
                Log.d(TAG, "save skin as before");
                return;
            }
        }
        mCurrentPackageName = tempName;
        savePackageNameToSP(context, mCurrentPackageName);

        try {
            Context themeCtx = context.createPackageContext(mCurrentPackageName, Context.CONTEXT_IGNORE_SECURITY);
            mResources = themeCtx.getResources();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mResources = context.getResources();
            mCurrentPackageName = context.getPackageName();
        }
    }

    public int getLayoutId(String layoutName) {
        return getId(layoutName, DEFTYPE_LAYOUT);
    }

    public int getViewId(String layoutName) {
        return getId(layoutName, DEFTYPE_ID);
    }

    private int getId(String resName, String deftype) {
        int id = -1;
        if(null != mResources && !TextUtils.isEmpty(resName)) {
            id = mResources.getIdentifier(resName, deftype, mCurrentPackageName);
        }
        return id;
    }

    public int getColor(String colorName) {
        int color = -1;
        if(!TextUtils.isEmpty(colorName) && null != mResources) {
            color = mResources.getColor(mResources.getIdentifier(colorName, DEFTYPE_COLOR, mCurrentPackageName));
        }
        return color;
    }

    private void savePackageNameToSP(Context context, String packageName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_KEY_PACKAGE_NAME, packageName);
        editor.commit();
    }

    private String getPackageNameFromSP(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SP_KEY_PACKAGE_NAME, "");
    }
}
