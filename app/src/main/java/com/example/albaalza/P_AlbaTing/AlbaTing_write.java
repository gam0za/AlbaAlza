package com.example.albaalza.P_AlbaTing;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.R;

public class AlbaTing_write extends AppCompatActivity {

    private TextView title,content;
    private ImageView image,button;
    private AlbaTing1Fragment albaTing1Fragment;
    private AlbaTing2Fragment albaTing2Fragment;
    private AlbaTing3Fragment albaTing3Fragment;
    private AlbaTing4Fragment albaTing4Fragment;

   private static final int SELECT_PICTURE=1; //앨범 접근을 위한 코드
    private String selectedImagePath;

   private String str;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode== SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                image.setImageURI(selectedImageUri);
            }
        }
//
//        Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
//        image.setImageBitmap(bitmap);

    }

    private String getPath(Uri uri) {
        if(uri==null){
            return null;
        }
        String [] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index=cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return  uri.getPath();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting_write);

        title=(TextView)findViewById(R.id.edit_title);
        content=(TextView)findViewById(R.id.edit_writing);
        image=(ImageView)findViewById(R.id.add_image);
        button=(ImageView)findViewById(R.id.write_confirm);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                       "Select Picture" ),SELECT_PICTURE);

               /*   실직적인 카메라 인텐트를 생성하는 부분으로
                   카메라 갤러리 호출과 요청코드에 따른 응답을 받기위해
                    startActivityForResult를 이용한다,,,   */
            }
        });




        albaTing1Fragment= new AlbaTing1Fragment();
        albaTing2Fragment =new AlbaTing2Fragment();
        albaTing3Fragment = new AlbaTing3Fragment();
        albaTing4Fragment = new AlbaTing4Fragment();

        Intent intent = getIntent();
       str= intent.getStringExtra("albating");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();

                switch (str){
                    case "대타":{
                    finish();
                    }
                    case "공지":{
                        finish();
                    }
                    case  "이벤트":{
                        finish();
                    }
                    case "잡담":{

                        finish();
                    }

                }
            }
        });


    }
}
