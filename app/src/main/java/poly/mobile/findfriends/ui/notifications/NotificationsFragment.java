package poly.mobile.findfriends.ui.notifications;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import poly.mobile.findfriends.MainActivity;
import poly.mobile.findfriends.MapsActivity;
import poly.mobile.findfriends.R;
import poly.mobile.findfriends.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {


    private FragmentNotificationsBinding binding;

    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String number = binding.phone.getText().toString();

        FusedLocationProviderClient client = new FusedLocationProviderClient(getActivity());
        if (MainActivity.gps_permission){
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        binding.latitude.setText(location.getLatitude()+"");
                        binding.longitude.setText(location.getLongitude()+"");
                        //System.out.println(location.getLatitude()+" / "+location.getLatitude());
                    }
                    else{
                        System.out.println("error");
                    }
                }
            });
        }

        // Send Msg
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.readSms_permission){
                    if(number.length() == 8){
                        String latitude = binding.latitude.getText().toString();
                        String longitude =  binding.longitude.getText().toString();
                        SmsManager manager = SmsManager.getDefault(); // SIM 1
                        manager.sendTextMessage(
                                number,
                                null,
                                "findFriends \n Latitude = "+latitude+"\n Longitude = " +longitude,
                                null,
                                null);
                    }
                    else{
                        Toast.makeText(getContext(), "Invalid data !", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "No authorization !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}