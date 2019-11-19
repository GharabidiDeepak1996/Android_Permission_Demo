package com.example.permissionsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //request the permission
private static final int REQUEST_CAMERA=125;
private static final int REQUEST_STORAGE=225;
private static  final int REQUESTT_CONTACT=325;
private static  final int REQUEST_GROUP_PERMISSION=425;
//which permission we called
    private static final int TEXT_CAMER=1;
    private static  final int TEXT_STORAGE=2;
    private static final int TEXT_CONTACT=3;
    PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        permissionUtil=new PermissionUtil( this );
    }
//check whether permission is already granted or not
    private int checkPermission(int permission){
        int status= PackageManager.PERMISSION_DENIED;
        switch (permission){
            case TEXT_CAMER:
                status= ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA );
                break;
            case TEXT_STORAGE:
                status= ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE );
                break;
            case TEXT_CONTACT:
                status= ContextCompat.checkSelfPermission( this, Manifest.permission.READ_CONTACTS );
                break;
        }
        return status;
    }
    //request new permission
    private  void requestPermission(int permission){
        switch (permission){
            case TEXT_CAMER:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
                break;
            case TEXT_STORAGE:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE);
                break;
            case TEXT_CONTACT:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUESTT_CONTACT);
                break;
        }
    }

    //Display premission explemation why we need this permission
    private void showPermissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        if (permission == TEXT_CAMER) {
            builder.setMessage( "This app need to access your devices camera..please allow" );
            builder.setTitle( "camera permission needed" );
        } else if (permission == TEXT_STORAGE) {
            builder.setMessage( "This app need to access your devices storage..please allow" );
            builder.setTitle( "storage permission needed" );
        } else if (permission == TEXT_CONTACT) {
            builder.setMessage( "This app need to access your devices contact..please allow" );
            builder.setTitle( "contact permission needed" );
        }
        //positive button
        builder.setPositiveButton( "Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (permission == TEXT_CAMER)
                    requestPermission( TEXT_CAMER );
                else if (permission == TEXT_STORAGE)
                    requestPermission( TEXT_STORAGE );
                else if (permission == TEXT_CONTACT)
                    requestPermission( TEXT_CONTACT );

            }
        } );
        //nagetive button
        builder.setNegativeButton( "cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
            }
        } );
        //to show permission box
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
//request group permission
    private void reuestGroupPermission(ArrayList<String> permission){
        String[] permissionList=new String[permission.size()];
        permission.toArray(permissionList);
        ActivityCompat.requestPermissions( this,permissionList,REQUEST_GROUP_PERMISSION );

    }
    public void camera(View view) {
        if(checkPermission( TEXT_CAMER )!=PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale( MainActivity.this,Manifest.permission.CAMERA )){
                showPermissionExplanation( TEXT_CAMER );
            }
            else if(!permissionUtil.checkPermissionPreference( "camera" )){

                requestPermission( TEXT_CAMER );
                permissionUtil.updatePermissionPermission( "camera" );
            }
else{
                Toast.makeText( this,"Please allow ccamera permission in your app settings",Toast.LENGTH_LONG ).show();
                Intent intent=new Intent(  );
                intent.setAction( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
                Uri uri= Uri.fromParts("package",this.getPackageName(),null);
                intent.setData( uri );
                this.startActivity( intent );
            }
        }
        else{
            Toast.makeText( this,"You have camera permission",Toast.LENGTH_LONG ).show();
            Intent intent=new Intent(this,ResultActivity.class  );
            intent.putExtra( "Message","you can noe take photo and recode videos" );
            startActivity( intent );
        }

    }

    public void storage(View view) {

    }

    public void contact(View view) {

    }

    public void group(View view) {
        ArrayList<String> permissionNeeded=new ArrayList<>(  );
        ArrayList<String> permissionAvailable=new ArrayList<>(  );

        permissionAvailable.add(Manifest.permission.READ_CONTACTS);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionAvailable.add(Manifest.permission.CAMERA);
        for(String permission :permissionAvailable){
            if(ContextCompat.checkSelfPermission( this,permission )!=PackageManager.PERMISSION_GRANTED)
permissionNeeded.add( permission );
        }
        reuestGroupPermission( permissionNeeded );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case REQUEST_CAMERA:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   Toast.makeText( this,"You have camera permission",Toast.LENGTH_LONG ).show();
                   Intent intent=new Intent(this,ResultActivity.class  );
                   intent.putExtra( "Message","you can noe take photo and recode videos" );
                   startActivity( intent );
               }
               else{
                   Toast.makeText( this,"camera permission is denied.Turn off camera modules of the app",Toast.LENGTH_LONG ).show();

               }
               break;
           case REQUESTT_CONTACT:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   Toast.makeText( this,"You have contact permission",Toast.LENGTH_LONG ).show();
                   Intent intent=new Intent(this,ResultActivity.class  );
                   intent.putExtra( "Message","you can noe take photo and recode videos" );
                   startActivity( intent );
               }
               else{
                   Toast.makeText( this,"contact permission is denied.Turn off contact modules of the app",Toast.LENGTH_LONG ).show();

               }
               break;
           case REQUEST_STORAGE:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   Toast.makeText( this,"You have storage permission",Toast.LENGTH_LONG ).show();
                   Intent intent=new Intent(this,ResultActivity.class  );
                   intent.putExtra( "Message","you can noe take photo and recode videos" );
                   startActivity( intent );
               }
               else{
                   Toast.makeText( this,"storage permission is denied.Turn off storage modules of the app",Toast.LENGTH_LONG ).show();

               }
               break;
           case REQUEST_GROUP_PERMISSION:
               String result="";
               int i=0;
               for(String perm:permissions){
                   String status="";
                   if(grantResults[i]==PackageManager.PERMISSION_GRANTED)
                       status="GRANTED";
                   else
                       status="DENIED";
                   result=result+"\n"+perm+":"+status;
                   i++;
               }
               AlertDialog.Builder builder=new AlertDialog.Builder( this );
               builder.setTitle( "Group permission Details" );
               builder.setMessage( result );
                       builder.setPositiveButton( "OK",new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       } );
               //to show permission box
               AlertDialog alertDialog=builder.create();
               alertDialog.show();
               break;

       }
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }
}
