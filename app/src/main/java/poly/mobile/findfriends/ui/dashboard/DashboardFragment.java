package poly.mobile.findfriends.ui.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import poly.mobile.findfriends.Friend;
import poly.mobile.findfriends.Login;
import poly.mobile.findfriends.R;
import poly.mobile.findfriends.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    FirebaseDatabase realTimeDB = null;
    FirebaseStorage storageDB = null;
    Uri uri_global = null;
    private FragmentDashboardBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.cameraDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        // Save to realTime DB
        binding.saveDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.firstNameDash.getText().toString();
                String lastName = binding.lastNameDash.getText().toString();
                String phoneNbr = binding.phoneNbrDash.getText().toString();
                String latitude = binding.latitudeDash.getText().toString();
                String longitude = binding.longitudeDash.getText().toString();

                Friend friend = new Friend(firstName,lastName,phoneNbr,longitude,latitude,uri_global.toString());
                System.out.println(uri_global);
                if(uri_global != null){
                    // upload d'image sur FireBase Storage
                    storageDB = FirebaseStorage.getInstance();
                    StorageReference ref_images = storageDB.getReference("images/");
                    String image_key = System.currentTimeMillis()+"";
                    StorageReference ref_image = ref_images.child("image_"+image_key+".jpg");
                    ref_image.putFile(uri_global).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            ref_image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Ajout d'un friend sur la base (RealTime dataBase)
                                    realTimeDB = FirebaseDatabase.getInstance();
                                    DatabaseReference ref_table_friends = realTimeDB.getReference("friends");
                                    String key = ref_table_friends.push().getKey();
                                    DatabaseReference ref_friend = ref_table_friends.child("friend"+key);
                                    ref_friend.setValue(friend);
                                    Toast.makeText(getContext(), "Friend saved successfully !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Profile photo required to register friend", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    // Show Popup when click camera icon
    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(getActivity(),v);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.item_camera_menu){
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i,1);
                }
                else{
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/");
                    startActivityForResult(i,2);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bitmap image = (Bitmap)data.getExtras().get("data");
            binding.cameraDash.setImageBitmap(image);
        }
        else{ //requestCode == 2
            Uri uri_local = data.getData();
            this.uri_global = uri_local;
            binding.cameraDash.setImageURI(uri_local);
        }
    }

}