package com.example.resturantmenuapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddCategoryActivity extends AppCompatActivity
{
    private EditText CategoryName;
    private ImageButton CategoryIconBtn;
    private Button SaveCategoryBtn;
    private DatabaseReference CategoryRef;
    private String CategoryNameText ,mUri , currentQuestionTime , CategoryKey;
    private ProgressDialog loadingBar;

    final static int Gallery_pick = 1;// to used as second parametar in open phone gallery method
    private static final int IMAGE_REQUEST =1;
    private StorageReference userProfileImageRef,storageReference;
    private Uri imageUri;
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        CategoryName = findViewById(R.id.AddCategoryNameText);
        CategoryIconBtn = findViewById(R.id.CategorySelectIcon);
        SaveCategoryBtn = findViewById(R.id.SaveCategoryBtn);

        CategoryRef = FirebaseDatabase.getInstance().getReference().child("All_Categories");
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        loadingBar = new ProgressDialog(this);

        CategoryIconBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              openImage();
            }
        });

        SaveCategoryBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
             Save_Category_Info_Method();
            }
        });


    }

    //---------------method of croping image-----------------------------------------------------

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void uploadImage()
    {
        /*final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading...");
        pd.show();*/
        if (imageUri !=null){
            final  StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                         mUri = downloadUri.toString();
                       // HashMap<String,Object> map = new HashMap<>();
                        //CategoryMap.put("Category_Icon",mUri);
                        //   pd.dismiss();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        // pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    // pd.dismiss();
                }
            });
        }
        else
            {
            Toast.makeText(getApplicationContext(),"No image Selected",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== IMAGE_REQUEST && resultCode==RESULT_OK
                && data != null && data.getData()!=null)
        {
            imageUri = data.getData();

            if(uploadTask != null && uploadTask.isInProgress())
            {
                Toast.makeText(getApplicationContext(),"upload in progress",Toast.LENGTH_SHORT).show();


            }else
                {
                uploadImage();
            }
        }
    }

    public void Save_Category_Info_Method()
    {
        CategoryNameText = CategoryName.getText().toString();

        if(TextUtils.isEmpty(CategoryNameText))
        {
            Toast.makeText(AddCategoryActivity.this, "Please Enter Category Name !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("complete Category setup");
            loadingBar.setMessage("please wait until Creating Category setup complete...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
             HashMap CategoryMap = new HashMap();

            CategoryMap.put("Category_Name" , CategoryNameText);
            CategoryMap.put("Category_Icon" , mUri);

            Calendar calendarForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
            currentQuestionTime = currentTime.format(calendarForTime.getTime());
            CategoryKey = CategoryNameText + currentQuestionTime;

            CategoryRef.child(CategoryNameText).updateChildren(CategoryMap).addOnCompleteListener(new OnCompleteListener()
            {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddCategoryActivity.this, "Category setup finished successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        CategoryName.setText("");
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(AddCategoryActivity.this, "error occured : "+message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        CategoryName.setText("");
                    }
                }
            });


        }
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);

    }
}



