package com.example.permissionsdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Switch;

public class PermissionUtil {
    private Context context;
    private SharedPreferences preferences;
    ;
    private SharedPreferences.Editor editor;

    public PermissionUtil(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences( context.getString( R.string.permission_preference ), Context.MODE_PRIVATE );
        editor = preferences.edit();
    }

    //this for new
    public void updatePermissionPermission(String permission) {
        switch (permission) {
            case "camera":
                editor.putBoolean( context.getString( R.string.permission_camera ), true );
                editor.commit();
                break;
            case "storage":
                editor.putBoolean( context.getString( R.string.permission_storage ), true );
                editor.commit();
                break;
            case "contact":
                editor.putBoolean( context.getString( R.string.permission_contact ), true );
                editor.commit();
                break;
        }
    }
//check allow or not
    public boolean checkPermissionPreference(String permission) {
        boolean isShow = false;
        switch (permission) {
            case "camera":
                isShow=preferences.getBoolean( context.getString( R.string.permission_camera ), false );
                break;
            case "storage":
                isShow=preferences.getBoolean( context.getString( R.string.permission_storage ), false );
                break;
            case "contact":
                isShow=preferences.getBoolean( context.getString( R.string.permission_contact ), false );
                break;
        }
        return isShow;
    }

}
