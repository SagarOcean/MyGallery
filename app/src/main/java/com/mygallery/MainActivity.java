package com.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.usage.ExternalStorageStats;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ImageView imageView;
    ArrayList<File> list;
    private static final int EXTERNAL_STORAGE=3;

   /** @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case EXTERNAL_STORAGE:{
                if(!(grantResults.length>0) && !(grantResults[0]== PackageManager.PERMISSION_GRANTED)){
                    AlertDialog dialog =dialog = new AlertDialog.Builder(MainActivity.this).create();
                    dialog.setTitle("Error !!");
                    dialog.setMessage("Storage permission not granted");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    });
                    dialog.show();
                    return;
                }
            }
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE);

        gridView = (GridView) findViewById(R.id.image_grid);
        list = imageReader(Environment.getExternalStorageDirectory());
        gridView.setAdapter(new GridAdapter());
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(),"please grant the storage permission",Toast.LENGTH_SHORT).show();
            }
            else {

            }
        }else Toast.makeText(getApplicationContext(),"storage permission granted",Toast.LENGTH_SHORT).show();



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,FullImage.class);
                intent.putExtra("img",list.get(i).toString());
                startActivity(intent);
            }
        });

    }

    private ArrayList<File> imageReader(File externalStorageDirectory) {
        ArrayList<File> images = new ArrayList<>();
        File[] files = externalStorageDirectory.listFiles();
        for(int i= 0 ; i<files.length; i++){

            if(files[i].isDirectory())
                images.addAll(imageReader(files[i]));
            else {

                if(files[i].getName().endsWith(".jpg")){
                    images.add(files[i]);
                }
            }
        }
        return images;
    }
    public class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {


            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View containerView=null;
            containerView = getLayoutInflater().inflate(R.layout.row_image,viewGroup,false);
            imageView = (ImageView) containerView.findViewById(R.id.image_item);

            imageView.setImageURI(Uri.parse(list.get(i).toString()));
            return containerView;
        }
    }
}
