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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewCategoryItem extends AppCompatActivity
{
    private ArrayList<String> categories = new ArrayList<>();
    private DatabaseReference CategoryRef;
    private Spinner sp_category_popup;
    private String selected_Category , mUri , CategoryItemNameText;
    private EditText CategoryItemName;
    private ImageButton CategoryItemImage;
    private Button SaveCategoryItemBtn;

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
        setContentView(R.layout.activity_add_new_category_item);

        CategoryItemName = findViewById(R.id.AddItemCategoryNameText);
        CategoryItemImage = findViewById(R.id.ItemCategorySelectImage);
        SaveCategoryItemBtn = findViewById(R.id.SaveItemCategoryBtn);

        CategoryRef = FirebaseDatabase.getInstance().getReference().child("All_Categories");
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        loadingBar = new ProgressDialog(this);


        //-------------------------------for spinner category----------------------------------------------------


        CategoryRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories.clear();

                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren())
                {

                    String category_key = categorySnapshot.getKey();
                    categories.add(category_key);
                }

                sp_category_popup = findViewById(R.id.sp_category);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(AddNewCategoryItem.this, android.R.layout.simple_spinner_item, categories);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_category_popup.setAdapter(areasAdapter);

                sp_category_popup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        selected_Category = categories.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //---------------------------------------------------------------------------------------

        CategoryItemImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openImage();
            }
        });

        SaveCategoryItemBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Save_Category_Item_Info_Method();
            }
        });

       // storageReference= FirebaseStorage.getInstance().getReference(selected_Category);

    }





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
            final StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
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

    public void Save_Category_Item_Info_Method()
    {
        CategoryItemNameText = CategoryItemName.getText().toString();

        if(TextUtils.isEmpty(CategoryItemNameText))
        {
            Toast.makeText(AddNewCategoryItem.this, "Please Enter Category Name !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("complete Add new Item setup");
            loadingBar.setMessage("please wait until Adding new Item setup complete...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            HashMap CategoryItemMap = new HashMap();

            CategoryItemMap.put("Category_Item_Name" , CategoryItemNameText);
            CategoryItemMap.put("Category_Item_Icon" , mUri);

            Calendar calendarForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
//            currentQuestionTime = currentTime.format(calendarForTime.getTime());
//            CategoryKey = CategoryNameText + currentQuestionTime;

            CategoryRef.child(selected_Category).child("CategoryItems").child(CategoryItemNameText).push().updateChildren(CategoryItemMap).addOnCompleteListener(new OnCompleteListener()
            {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddNewCategoryItem.this, "Adding Item setup finished successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        CategoryItemName.setText("");
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(AddNewCategoryItem.this, "error occured : "+message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        CategoryItemName.setText("");
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
