package com.vimalcvs.stickers_app.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.adapter.image.impl.PicassoAdapter;
import com.sangcomz.fishbun.define.Define;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vimalcvs.stickers_app.BuildConfig;
import com.vimalcvs.stickers_app.Manager.PrefManager;
import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.Sticker;
import com.vimalcvs.stickers_app.StickerPack;
import com.vimalcvs.stickers_app.adapter.CategorySelectAdapter;
import com.vimalcvs.stickers_app.adapter.SelectableCategoryViewHolder;
import com.vimalcvs.stickers_app.api.ProgressRequestBody;
import com.vimalcvs.stickers_app.api.apiClient;
import com.vimalcvs.stickers_app.api.apiRest;

import com.vimalcvs.stickers_app.editor.FileUtils;
import com.vimalcvs.stickers_app.editor.editimage.EditImageActivity;
import com.vimalcvs.stickers_app.editor.picchooser.SelectPictureActivity;
import com.vimalcvs.stickers_app.entity.ApiResponse;
import com.vimalcvs.stickers_app.entity.CategoryApi;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKER_PACK_AUTHORITY;
import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKER_PACK_ID;
import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKER_PACK_NAME;

public class UploadActivity extends AppCompatActivity implements  ProgressRequestBody.UploadCallbacks,SelectableCategoryViewHolder.OnItemSelectedListener{

    public static String mainpath;


    private Bitmap  TrayImage ;
    private ArrayList<CategoryApi> categoriesListObj = new ArrayList<CategoryApi>();
    private List<Bitmap>  stickersList = new ArrayList<>();
    List<Sticker> mStickers = new ArrayList<>();
    List<String> mEmojis,mDownloadFiles;
    private static final int ADD_PACK = 22200;
    private String packId ;

    private int PICK_IMAGE_TRAY_CIRCLE =  300;
    private int PICK_IMAGE_TRAY_RECTANGLE =  301;
    private int PICK_IMAGE_TRAY_NO_CROP=  302;

    private int PICK_IMAGE_STICKER_ADD_CIRCLE =  200;
    private int PICK_IMAGE_STICKER_ADD_RECTANGLE=  201;
    private int PICK_IMAGE_STICKER_ADD_NO_CROP = 202;

    private ProgressDialog register_progress;
    private ProgressDialog uploading_progress;

    private RelativeLayout relative_layout_select;
    private RelativeLayout relative_layout_add_sticker;
    private ImageView image_view_tray_image;
    private EditText edit_text_input_name;
    private EditText edit_text_input_publisher;
    private TextInputLayout text_input_layout_name;
    private TextInputLayout text_input_layout_publisher;
    private RecyclerView recyclerView;
    private BitmapListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private RelativeLayout relative_layout_upload_pack;
    private RecyclerView recycle_view_selected_category;
    private LinearLayoutManager gridLayoutManagerCategorySelect;


    private String imageurl;
    Integer counter = 0;
    private String id_ser;
    private String key_ser;

    private CategorySelectAdapter categorySelectAdapter;
    private EditText edit_text_input_website;
    private EditText edit_text_input_email;
    private EditText edit_text_input_privacy;
    private EditText edit_text_input_license;
    private ImageView image_view_show_more;
    private LinearLayout linear_layout_more;
    private boolean categoriesIsLoaded = false;
    private RelativeLayout relative_layout_add_to_whatsapp;
    private StickerPack stickerPack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainpath = getFilesDir() + "/" + "stickers_asset";

        PrefManager prf = new PrefManager(getApplicationContext());
        id_ser=  prf.getString("ID_USER");
        key_ser=  prf.getString("TOKEN_USER");

        int id =(new Random().nextInt(99999) + 10000);
        packId =String.valueOf(id);
        setContentView(R.layout.activity_upload);
        initView();
        initAction();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode==SELECT_GALLERY_IMAGE_CODE_TO_STICKER){
                addSticker();
            }
            if (requestCode==SELECT_GALLERY_IMAGE_CODE_TO_TRY_IMAGE){
                SelectTrayImage();
            }
        }
    }
    public void DialogTrayImage(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.crop_mode_dialog);

        RelativeLayout  relative_layout_crop_as_circle=(RelativeLayout) dialog.findViewById(R.id.relative_layout_crop_as_circle);
        RelativeLayout  relative_layout_crop_as_rect=(RelativeLayout) dialog.findViewById(R.id.relative_layout_crop_as_rect);
        RelativeLayout  relative_layout_no_crop=(RelativeLayout) dialog.findViewById(R.id.relative_layout_no_crop);
        relative_layout_no_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTrayImage();
                dialog.dismiss();
            }
        });
        relative_layout_crop_as_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAblumWithPermissionsCheck(1003);
                dialog.dismiss();
            }
        });
        relative_layout_crop_as_rect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAblumWithPermissionsCheck(1004);
                dialog.dismiss();
            }
        });

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
        dialog.show();
    }
    public void DialogaddSticker(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.crop_mode_dialog);

        RelativeLayout  relative_layout_crop_as_circle=(RelativeLayout) dialog.findViewById(R.id.relative_layout_crop_as_circle);
        RelativeLayout  relative_layout_crop_as_rect=(RelativeLayout) dialog.findViewById(R.id.relative_layout_crop_as_rect);
        RelativeLayout  relative_layout_no_crop=(RelativeLayout) dialog.findViewById(R.id.relative_layout_no_crop);
        relative_layout_no_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSticker();
                dialog.dismiss();

            }
        });
        relative_layout_crop_as_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAblumWithPermissionsCheck(1005);

                dialog.dismiss();
            }
        });
        relative_layout_crop_as_rect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAblumWithPermissionsCheck(1006);

                dialog.dismiss();
            }
        });

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
        dialog.show();
    }

    private void initAction() {
        this.relative_layout_add_to_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPack();
            }
        });
        this.relative_layout_upload_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        this.relative_layout_add_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadActivity.this, new String[] {  Manifest.permission.WRITE_EXTERNAL_STORAGE }, 500);
                }else {
                    DialogaddSticker();
                }
            }
        });
        this.relative_layout_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 600);
                }else {
                    DialogTrayImage();
                }
            }
        });
        this.image_view_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!categoriesIsLoaded){
                    getCategory();
                }
                if (linear_layout_more.getVisibility() == View.VISIBLE){
                    linear_layout_more.setVisibility(View.GONE);
                    image_view_show_more.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down));
                }else{
                    linear_layout_more.setVisibility(View.VISIBLE);
                    image_view_show_more.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up));
                }
            }
        });
    }



    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
    private void initView() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.create_pack));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.linear_layout_more    =  (LinearLayout) findViewById(R.id.linear_layout_more);
        this.image_view_show_more    =  (ImageView) findViewById(R.id.image_view_show_more);
        this.recycle_view_selected_category    =  (RecyclerView) findViewById(R.id.recycle_view_selected_category);
        this.relative_layout_add_to_whatsapp    =  (RelativeLayout) findViewById(R.id.relative_layout_add_to_whatsapp);
        this.relative_layout_upload_pack    =  (RelativeLayout) findViewById(R.id.relative_layout_upload_pack);
        this.text_input_layout_name         =  (TextInputLayout) findViewById(R.id.text_input_layout_name);
        this.text_input_layout_publisher    =  (TextInputLayout) findViewById(R.id.text_input_layout_publisher);
        this.edit_text_input_email           =  (EditText) findViewById(R.id.edit_text_input_email);
        this.edit_text_input_website           =  (EditText) findViewById(R.id.edit_text_input_website);
        this.edit_text_input_privacy           =  (EditText) findViewById(R.id.edit_text_input_privacy);
        this.edit_text_input_license           =  (EditText) findViewById(R.id.edit_text_input_license);
        this.edit_text_input_name           =  (EditText) findViewById(R.id.edit_text_input_name);
        this.edit_text_input_publisher      =  (EditText) findViewById(R.id.edit_text_input_publisher);
        this.relative_layout_select         =  (RelativeLayout) findViewById(R.id.relative_layout_select);
        this.relative_layout_add_sticker    =  (RelativeLayout) findViewById(R.id.relative_layout_add_sticker);
        this.image_view_tray_image          =  (ImageView) findViewById(R.id.image_view_tray_image);
        this.image_view_tray_image          =  (ImageView) findViewById(R.id.image_view_tray_image);
        this.recyclerView          =  (RecyclerView) findViewById(R.id.recyclerView);

        this.gridLayoutManager=  new GridLayoutManager(UploadActivity.this,4);
        gridLayoutManagerCategorySelect = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        this.adapter  = new BitmapListAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

    }
    public static final int REQUEST_PERMISSON_SORAGE = 1;
    public static final int REQUEST_PERMISSON_CAMERA = 2;

    public static final int SELECT_GALLERY_IMAGE_CODE_TO_STICKER = 1578;
    public static final int SELECT_GALLERY_IMAGE_CODE_TO_TRY_IMAGE = 1572;

    public static final int EDITOR_IMAGE_CODE_TO_STICKER = 1598;
    public static final int EDITOR_IMAGE_CODE_TO_TRY_IMAGE = 1592;

    public static final int REMOVE_BG_IMAGE_CODE_TO_STICKER = 1568;
    public static final int REMOVE_BG__IMAGE_CODE_TO_TRY_IMAGE = 1562;


    private void openAblum(int code) {
        FishBun.with(UploadActivity.this)
                .setImageAdapter(new PicassoAdapter())
                .setMaxCount(1)
                .exceptGif(true)
                .setMinCount(1)
                .setActionBarColor(Color.parseColor("#25D366"), Color.parseColor("#25D366"), false)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .setRequestCode(code)
                .startAlbum();
    }
    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // System.out.println("RESULT_OK");
            switch (requestCode) {
                case SELECT_GALLERY_IMAGE_CODE_TO_STICKER : {
                    ArrayList<Uri> dk = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    Uri filepath=  dk.get(0) ;
                    Bitmap bitmap =null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filepath);
                    } catch (IOException e) {
                    }
                    File file =  getFileFromBitmap(bitmap);
                    EditImageActivity.start(this, file.getAbsolutePath() ,file.getAbsolutePath(), EDITOR_IMAGE_CODE_TO_STICKER);
                    break;
                }
                case SELECT_GALLERY_IMAGE_CODE_TO_TRY_IMAGE : {
                    ArrayList<Uri> dk = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    Uri filepath=  dk.get(0) ;
                    Bitmap bitmap =null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filepath);
                    } catch (IOException e) {
                    }
                    File file =  getFileFromBitmap(bitmap);
                    EditImageActivity.start(this,file.getAbsolutePath() ,file.getAbsolutePath(), EDITOR_IMAGE_CODE_TO_TRY_IMAGE);
                }

                case REMOVE_BG_IMAGE_CODE_TO_STICKER: {
                    String newFilePath = data.getStringExtra(CutOut.CUTOUT_EXTRA_RESULT);


                    File file = new File(newFilePath);

                    String filePath = file.getPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                    if (bitmap==null){
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    }

                    stickersList.add(bitmap);
                    adapter.notifyDataSetChanged();

                    break;
                }
                case REMOVE_BG__IMAGE_CODE_TO_TRY_IMAGE: {
                    String newFilePath = data.getStringExtra(CutOut.CUTOUT_EXTRA_RESULT);


                    File file = new File(newFilePath);

                    String filePath = file.getPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                    if (bitmap==null){
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    }



                    image_view_tray_image.setImageBitmap(bitmap);
                    TrayImage = bitmap;
                    imageurl = filePath;
                    break;
                }
                case EDITOR_IMAGE_CODE_TO_STICKER: {
                    String newFilePath = data.getStringExtra(EditImageActivity.EXTRA_OUTPUT);

                    boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IMAGE_IS_EDIT, false);

                    if (isImageEdit){
                    }else{//未编辑  还是用原来的图片
                        newFilePath = data.getStringExtra(EditImageActivity.FILE_PATH);;
                    }
                    Intent intent = new Intent(UploadActivity.this,EditorActivity.class);
                    intent.putExtra("uri",newFilePath);
                    startActivityForResult(intent,REMOVE_BG_IMAGE_CODE_TO_STICKER);

                    break;
                }
                case EDITOR_IMAGE_CODE_TO_TRY_IMAGE: {
                    String newFilePath = data.getStringExtra(EditImageActivity.EXTRA_OUTPUT);

                    boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IMAGE_IS_EDIT, false);

                    if (isImageEdit){
                    }else{//未编辑  还是用原来的图片
                        newFilePath = data.getStringExtra(EditImageActivity.FILE_PATH);;
                    }
                    Intent intent = new Intent(UploadActivity.this,EditorActivity.class);
                    intent.putExtra("uri",newFilePath);
                    startActivityForResult(intent,REMOVE_BG__IMAGE_CODE_TO_TRY_IMAGE);

                    break;
                }

            }
        }

        if (requestCode == 1003) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Uri> uries;
                uries = data.getParcelableArrayListExtra(Define.INTENT_PATH);

                CropImage.ActivityBuilder cropImge =   CropImage.activity(uries.get(0))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop Your TryImage")
                        .setAllowFlipping(true)
                        .setFixAspectRatio(true)
                        .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                        .setActivityMenuIconColor(R.color.black)
                        .setCropMenuCropButtonIcon(R.drawable.ic_check);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    cropImge.setCropShape(CropImageView.CropShape.RECTANGLE);
                }else{
                    cropImge.setCropShape(CropImageView.CropShape.OVAL);
                }
                Intent intent = cropImge.getIntent(UploadActivity.this);
                startActivityForResult(intent,PICK_IMAGE_TRAY_CIRCLE );
            }
        }
        if (requestCode == 1004) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Uri> uries;
                uries = data.getParcelableArrayListExtra(Define.INTENT_PATH);

                CropImage.ActivityBuilder cropImge =   CropImage.activity(uries.get(0))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop Your TryImage")
                        .setAllowFlipping(true)
                        .setFixAspectRatio(true)
                        .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setActivityMenuIconColor(R.color.black)
                        .setCropMenuCropButtonIcon(R.drawable.ic_check);
                Intent intent = cropImge.getIntent(UploadActivity.this);
                startActivityForResult(intent, PICK_IMAGE_TRAY_RECTANGLE);
            }
        }
        if (requestCode == 1005) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Uri> uries;
                uries = data.getParcelableArrayListExtra(Define.INTENT_PATH);

                CropImage.ActivityBuilder cropImge =   CropImage.activity(uries.get(0))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop Your TryImage")
                        .setAllowFlipping(true)
                        .setFixAspectRatio(true)
                        .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                        .setActivityMenuIconColor(R.color.black)
                        .setCropMenuCropButtonIcon(R.drawable.ic_check);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    cropImge.setCropShape(CropImageView.CropShape.RECTANGLE);
                } else {
                    cropImge.setCropShape(CropImageView.CropShape.OVAL);
                }
                Intent intent = cropImge.getIntent(UploadActivity.this);
                startActivityForResult(intent, PICK_IMAGE_STICKER_ADD_CIRCLE);
            }
        }
        if (requestCode == 1006) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Uri> uries;
                uries = data.getParcelableArrayListExtra(Define.INTENT_PATH);

                CropImage.ActivityBuilder cropImge =   CropImage.activity(uries.get(0))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop Your TryImage")
                        .setAllowFlipping(true)
                        .setFixAspectRatio(true)
                        .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                        .setActivityMenuIconColor(R.color.black)
                        .setCropMenuCropButtonIcon(R.drawable.ic_check);
                cropImge.setCropShape(CropImageView.CropShape.RECTANGLE);

                Intent intent = cropImge.getIntent(UploadActivity.this);
                startActivityForResult(intent, PICK_IMAGE_STICKER_ADD_RECTANGLE);
            }
        }
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Uri selectedImage = CutOut.getUri(data);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image_view_tray_image.setImageBitmap(bitmap);
                TrayImage = bitmap;
                imageurl = selectedImage.getPath();
            }
        }
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE_STICKER) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = CutOut.getUri(data);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    stickersList.add(bitmap);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == PICK_IMAGE_TRAY_RECTANGLE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                image_view_tray_image.setImageBitmap(bitmap);
                TrayImage = bitmap;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == PICK_IMAGE_TRAY_CIRCLE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                image_view_tray_image.setImageBitmap(getCroppedBitmap(bitmap));
                TrayImage = getCroppedBitmap(bitmap);
                imageurl = resultUri.getPath();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == PICK_IMAGE_STICKER_ADD_CIRCLE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stickersList.add(getCroppedBitmap(bitmap));
                adapter.notifyDataSetChanged();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == PICK_IMAGE_STICKER_ADD_RECTANGLE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stickersList.add(bitmap);
                adapter.notifyDataSetChanged();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == PICK_IMAGE_STICKER_ADD_NO_CROP) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                if (bitmap != null) {
                    stickersList.add(bitmap);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (requestCode == PICK_IMAGE_TRAY_NO_CROP) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                if (bitmap != null) {
                    image_view_tray_image.setImageBitmap(bitmap);
                    TrayImage = bitmap;
                    imageurl = picturePath  ;

                }else {
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onItemSelected(CategoryApi item) {

    }

    public class BitmapListAdapter extends   RecyclerView.Adapter<BitmapListAdapter.Holder>{

        @NonNull
        @Override
        public BitmapListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bitmap, null);
            BitmapListAdapter.Holder mh = new BitmapListAdapter.Holder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(@NonNull BitmapListAdapter.Holder holder, final int position) {
            holder.bitmap_image.setImageBitmap(stickersList.get(position));
            holder.button_bitmap_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        @Override
        public int getItemCount() {
            return stickersList.size();
        }
        public class Holder  extends RecyclerView.ViewHolder {

            private final ImageView bitmap_image;
            private final Button button_bitmap_item;

            public Holder(@NonNull View itemView) {
                super(itemView);
                this.bitmap_image=(ImageView) itemView.findViewById(R.id.bitmap_image);
                this.button_bitmap_item=(Button) itemView.findViewById(R.id.button_bitmap_item);
            }
        }
    }
    private void openAblumWithPermissionsCheck(int code) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
            return;
        }
        openAblum(code);
    }

    public void SelectTrayImage(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            openAblumWithPermissionsCheck(SELECT_GALLERY_IMAGE_CODE_TO_TRY_IMAGE);
        } else {
            openAblum(SELECT_GALLERY_IMAGE_CODE_TO_TRY_IMAGE);
        }
    }
    public void addSticker(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            openAblumWithPermissionsCheck(SELECT_GALLERY_IMAGE_CODE_TO_STICKER);
        } else {
            openAblum(SELECT_GALLERY_IMAGE_CODE_TO_STICKER);
        }
    }

    public void upload(){
        if (edit_text_input_name.getText().toString().trim().length()<4){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.name_short)).show();
            return;
        }
        if (edit_text_input_publisher.getText().toString().trim().length()<3){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.publisher_short)).show();
            return;
        }
        if (TrayImage==null){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.tray_image_required)).show();
            return;
        }
        if (stickersList.size()<3){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.sticker_number_required)).show();
            return;
        }
        if (stickersList.size()>30){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.sticker_number_required_min)).show();
            return;
        }
        showProgress();

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);

        File TrayImagefile =  getFileFromBitmap(TrayImage);

        ProgressRequestBody requestFile = new ProgressRequestBody(TrayImagefile, UploadActivity.this);

        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", TrayImagefile.getName(), requestFile);

        List<MultipartBody.Part> parts = new ArrayList<>();

        for (int i = 0; i < stickersList.size(); i++) {
            File StickerFile =  getFileFromBitmap(stickersList.get(i));
            ProgressRequestBody requestFileSticker = new ProgressRequestBody(StickerFile, UploadActivity.this);
            MultipartBody.Part bodysticker = MultipartBody.Part.createFormData("sticker_"+i, StickerFile.getName(), requestFileSticker);
            parts.add(bodysticker);
        }
        Call<ApiResponse> request = service.uploadPack(body,parts,stickersList.size(),id_ser,key_ser,edit_text_input_name.getText().toString().trim(),edit_text_input_publisher.getText().toString().trim(),edit_text_input_email.getText().toString().trim(),edit_text_input_website.getText().toString().trim(),edit_text_input_privacy.getText().toString().trim(),edit_text_input_license.getText().toString().trim(),getSelectedCategories());
        request.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    Toasty.success(getApplication(),getResources().getString(R.string.pack_upload_success),Toast.LENGTH_LONG).show();
                }else{
                    Toasty.error(getApplication(),getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
                }
                hideProgress();
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toasty.error(getApplication(),getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }
    public Bitmap getResizedBitmap(Bitmap srcBmp) {
        Bitmap dstBmp;
        if (srcBmp.getWidth() >= srcBmp.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        }else{

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return  dstBmp;
    }
    public File getFileFromBitmap(Bitmap resource){

        int width = 512; // - Dimension in pixels
        int height= 512;  // - Dimension in pixels
        if (resource.getWidth() != resource.getHeight()) {
            resource = getResizedBitmap(resource);
        }
        Bitmap bitmap = Bitmap.createScaledBitmap(
                resource, width, height, false);
        counter++;
        OutputStream fOut = null;
        File file = new File(getApplicationContext().getCacheDir(), "FitnessGirl" + counter + ".png");
        try {
            fOut = new FileOutputStream(file);
            Bitmap pictureBitmap = bitmap; // obtaining the Bitmap
            pictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream

            //MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
        }catch (IOException e){

        }
        return file;
    }
    private void showProgress() {
        uploading_progress= ProgressDialog.show(this, null,getResources().getString(R.string.operation_progress), true);

    }

    private void hideProgress() {
        uploading_progress.dismiss();
    }
    private void getCategory() {
        register_progress= ProgressDialog.show(this, null,getResources().getString(R.string.operation_progress), true);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<CategoryApi>> call = service.AllCategories();
        call.enqueue(new Callback<List<CategoryApi>>() {
            @Override
            public void onResponse(Call<List<CategoryApi>> call, Response<List<CategoryApi>> response) {
                if(response.isSuccessful()){
                    categoriesListObj.clear();
                    for (int i = 0;i<response.body().size();i++){
                        categoriesListObj.add(response.body().get(i));
                    }
                    categorySelectAdapter = new CategorySelectAdapter(UploadActivity.this, categoriesListObj, true, UploadActivity.this);
                    recycle_view_selected_category.setHasFixedSize(true);
                    recycle_view_selected_category.setAdapter(categorySelectAdapter);
                    recycle_view_selected_category.setLayoutManager(gridLayoutManagerCategorySelect);
                    categoriesIsLoaded = true;
                }else {
                    Toasty.error(getApplication(),getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
                }
                register_progress.dismiss();
            }
            @Override
            public void onFailure(Call<List<CategoryApi>> call, Throwable t) {
                Toasty.error(getApplication(),getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
                register_progress.dismiss();
            }
        });
    }

    public String getSelectedCategories(){
        String categories = "";
        if (categorySelectAdapter!=null){
            if (categorySelectAdapter.getSelectedItems()!=null){
                for (int i = 0; i < categorySelectAdapter.getSelectedItems().size(); i++) {
                    categories+="_"+categorySelectAdapter.getSelectedItems().get(i).getId();
                }
            }
        }
        return categories;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // linear_layout_add_to_whatsapp.setVisibility(View.GONE);
            // linear_layout_progress.setVisibility(View.VISIBLE);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {

            try {

                int width_try = 96; // - Dimension in pixels
                int height_try= 96;  // - Dimension in pixels
                Bitmap bitmap_try =  Bitmap.createScaledBitmap(TrayImage, width_try, height_try, false);

                SaveTryImage(bitmap_try,"tray_image.png",stickerPack.identifier);

                ArrayList<StickerPack> stickerPacks =  Hawk.get("whatsapp_sticker_packs",new ArrayList<StickerPack>());
                if(stickerPacks==null){
                    stickerPacks = new ArrayList<>();
                }else {

                }
                for (int i = 0; i < stickerPacks.size(); i++) {
                    if (stickerPacks.get(i).identifier == packId){

                        stickerPacks.remove(i);
                        i--;
                    }
                }
                stickerPacks.add(stickerPack);
                Hawk.put("whatsapp_sticker_packs", stickerPacks);

                int progress =  0;
                for (final Sticker s : stickerPack.getStickers()) {

                    Bitmap resource = stickersList.get(progress);

                    int width = 512; // - Dimension in pixels
                    int height= 512;  // - Dimension in pixels
                    if (resource.getWidth() != resource.getHeight()) {
                        resource = getResizedBitmap(resource);
                    }
                    Bitmap bitmap1 =  Bitmap.createScaledBitmap(resource, width, height, false);

                    SaveImage(bitmap1, s.imageFileName, stickerPack.identifier);
                    progress++;
                    publishProgress(""+(int)((progress*100)/stickerPack.getStickers().size()));
                }


            } catch (Exception e) {
                Log.e("PACKSTICKER",e.getMessage());

            }
            return null;
        }
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            //progress_bar_pack.setProgress(Integer.parseInt(progress[0]));
        }
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // linear_layout_add_to_whatsapp.setVisibility(View.VISIBLE);
            // linear_layout_progress.setVisibility(View.GONE);
            Addtowhatsapp();
        }
    }
    public static void SaveTryImage(Bitmap finalBitmap, String name, String identifier) {

        String root = mainpath + "/" + identifier;
        File myDir = new File(root + "/" + "try");
        myDir.mkdirs();
        String fname = name.replace(".png","").replace(" ","_") + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 40, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void SaveImage(Bitmap finalBitmap, String name, String identifier) {

        String root = mainpath + "/" + identifier;
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = name;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.WEBP, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("error when save",e.getMessage());
        }
    }

    public void createPack(){
        if (edit_text_input_name.getText().toString().trim().length()<4){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.name_short)).show();
            return;
        }
        if (edit_text_input_publisher.getText().toString().trim().length()<3){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.publisher_short)).show();
            return;
        }
        if (TrayImage==null){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.tray_image_required)).show();
            return;
        }
        if (stickersList.size()<3){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.sticker_number_required)).show();
            return;
        }
        if (stickersList.size()>30){
            Toasty.error(getApplicationContext(),getResources().getString(R.string.sticker_number_required_min)).show();
            return;
        }
        mStickers = new ArrayList<>();
        mEmojis = new ArrayList<>();
        mDownloadFiles = new ArrayList<>();
        mEmojis.add("");

        stickerPack = new StickerPack(
                packId,
                edit_text_input_name.getText().toString().trim(),
                edit_text_input_publisher.getText().toString().trim(),
                "tray_image.png",
                "tray_image.png",
                "0",
                "0",
                "false",
                "false",
                "now",
                "1",
                "none",
                "me",
                edit_text_input_email.getText().toString().trim(),
                edit_text_input_website.getText().toString().trim(),
                edit_text_input_privacy.getText().toString().trim(),
                edit_text_input_license.getText().toString().trim()
        );
        for (int j = 0; j < stickersList.size(); j++) {
            mStickers.add(new Sticker(
                    "sticker_"+j+".webp",
                    "sticker_"+j+".webp",
                    "sticker_"+j+".webp",
                    mEmojis
            ));
            mDownloadFiles.add("sticker_"+j+".webp");
        }
        Hawk.put(stickerPack.identifier, mStickers);
        stickerPack.setStickers(Hawk.get(stickerPack.identifier,new ArrayList<Sticker>()));

        new DownloadFileFromURL().execute();
    }
    public void Addtowhatsapp(){

        Intent intent = new Intent();
        intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
        intent.putExtra(EXTRA_STICKER_PACK_ID, packId);
        intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
        intent.putExtra(EXTRA_STICKER_PACK_NAME, edit_text_input_name.getText().toString().trim());
        try {
            startActivityForResult(intent, ADD_PACK);
        } catch (ActivityNotFoundException e) {
            Toasty.info(UploadActivity.this, "WhatsApp Application not installed on this device", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
