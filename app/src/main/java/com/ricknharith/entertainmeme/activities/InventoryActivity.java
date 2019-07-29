package com.ricknharith.entertainmeme.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ricknharith.entertainmeme.R;
import com.ricknharith.entertainmeme.helpers.MemeDbHelper;
import com.ricknharith.entertainmeme.helpers.MemeLayoutAdapter;
import com.ricknharith.entertainmeme.models.Meme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements MemeLayoutAdapter.OnClickListener {

    MemeDbHelper memeDbHelper;

    RecyclerView memeListView;
    MemeLayoutAdapter memeLayoutAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Meme> memes;
    ImageButton backBtn;
    String ImagePath;
    String memeposition;
    Bitmap bitmap;
    URI uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        memeDbHelper = new MemeDbHelper(this);

        memes = memeDbHelper.getAllMemes();

        backBtn=(ImageButton)findViewById(R.id.backBtn);
        memeListView = (RecyclerView)findViewById(R.id.memeListView);
        layoutManager = new LinearLayoutManager(this);
        memeListView.setLayoutManager(layoutManager);

        memeLayoutAdapter = new MemeLayoutAdapter(memes, this, this);

        memeListView.setAdapter(memeLayoutAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClickListener(int position) {
        Intent i = new Intent(this, MemeActivity.class);
        i.putExtra("title", memes.get(position).getTitle());
        i.putExtra("url", memes.get(position).getUrl());
        startActivity(i);
    }

    private String saveImage(Bitmap image,int position) {
        String savedImagePath = null;

        String imageFileName = "JPEG_" + memes.get(position).getTitle() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MemeFolder");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(this, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
    @Override
    public void onElipsisButtonClickListener(final View view, final int position) {
        PopupMenu popup = new PopupMenu(this, view);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_meme:
                        memeDbHelper.deleteMeme(memes.get(position).getPostLink(),memes.get(position).getTitle());
                        memes.remove(position);
                        memeLayoutAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.share_meme:
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(memes.get(position).getUrl()));
                        shareIntent.setType("image/jpeg");
                        startActivity(Intent.createChooser(shareIntent, "Share images to.."));
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.inventorymenu, popup.getMenu());
        popup.show();
    }

}
