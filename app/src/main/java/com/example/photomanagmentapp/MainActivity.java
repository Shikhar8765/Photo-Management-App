package com.example.photomanagmentapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> imgpaths;
     static final int PERMISSION_REQUEST_CODE = 200;

    RecyclerView imagesRv;
    RecyclerViewAdapter imageRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgpaths = new ArrayList<>();
        imagesRv = findViewById(R.id.idRVImages);
        imageRVAdapter = new RecyclerViewAdapter(MainActivity.this, imgpaths);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 4);
        imagesRv.setLayoutManager(manager);
        imagesRv.setAdapter(imageRVAdapter);
        requestpermissions();




    }

    boolean checkpermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    void requestpermissions() {
        if (checkpermissions()) {
            Toast.makeText(this, "Permissions Granted....", Toast.LENGTH_SHORT).show();
            getImagePath();
        } else {
            requestPermission();
        }
    }

    void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    void getImagePath() {
        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            String orderBy = MediaStore.Images.Media._ID;
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            int count = cursor.getCount();
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                imgpaths.add(cursor.getString(dataColumnIndex));
            }
            imageRVAdapter.notifyDataSetChanged();
            cursor.close();
        }
    }

   public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
                        getImagePath();
                    } else {
                        Toast.makeText(this, "Permission Denied,Permissions are Required to use the app", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}

