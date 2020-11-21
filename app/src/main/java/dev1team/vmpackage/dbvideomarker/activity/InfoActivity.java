package dev1team.vmpackage.dbvideomarker.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import dev1team.vmpackage.dbvideomarker.R;
import dev1team.vmpackage.dbvideomarker.util.MediaStoreLoader;

public class InfoActivity extends AppCompatActivity {

    int id;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        id = intent.getIntExtra("ContentID", -1);
        Uri URI = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
        getVideoInfo(URI);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getVideoInfo(Uri uri) {

        ImageView iv1 = findViewById(R.id.info_thumb);
        TextView tv1 = findViewById(R.id.info_name);
        TextView tv2 = findViewById(R.id.info_dur);
        TextView tv3 = findViewById(R.id.info_size);
        TextView tv4 = findViewById(R.id.info_mime);
        TextView tv5 = findViewById(R.id.info_resol);
        TextView tv6 = findViewById(R.id.info_added);
        TextView tv7 = findViewById(R.id.info_path);

        MediaStoreLoader loader = new MediaStoreLoader();
        ContentResolver resolver = getApplicationContext().getContentResolver();

        String[] projections = {
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATA
        };

        Cursor c = resolver.query(uri, projections, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {
                int index;
                index = c.getColumnIndex(projections[0]);
                String name = c.getString(index);

                index = c.getColumnIndex(projections[1]);
                String millisDur = c.getString(index);
                long millis = Long.parseLong(millisDur);

                index = c.getColumnIndex(projections[2]);
                String bytesize = c.getString(index);
                int size = Integer.parseInt(bytesize);

                index = c.getColumnIndex(projections[3]);
                String mime = c.getString(index);

                index = c.getColumnIndex(projections[4]);
                String resol = c.getString(index);

                index = c.getColumnIndex(projections[5]);
                String added = c.getString(index);

                index = c.getColumnIndex(projections[6]);
                String path = c.getString(index);

                String changedTime = loader.getReadableDuration(millis);
                String changedSize = loader.getReadableFileSize(size);

                //Glide.with(this).asBitmap().load(uri).into(iv1);
                iv1.setImageBitmap(loader.getThumbnail(id, 20000,this));
                tv1.setText(name);
                tv2.setText(changedTime);
                tv3.setText(changedSize);
                tv4.setText(mime);
                tv5.setText(resol);
                tv6.setText(added);
                tv7.setText(path);
            }
        }
        assert c != null;
        c.close();
    }
}
