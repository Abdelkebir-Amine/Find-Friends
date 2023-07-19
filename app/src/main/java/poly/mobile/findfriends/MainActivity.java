package poly.mobile.findfriends;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import poly.mobile.findfriends.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static boolean readSms_permission = false;
    public static boolean gps_permission = false;
    public static boolean call_permission = false;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /***** Permission SMS *****/
        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                                                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
            // If Permission => OK
            readSms_permission = true;
        }
        else{
            // Request Permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS},
                    1);
        }

        /***** Permission GPS *****/
        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                                                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            // If Permission => OK
            gps_permission = true;
        }
        else{
            // Request Permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                    2);
        }

        /***** Permission CALL *****/
        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                                                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            // If Permission => OK
            call_permission = true;
        }
        else{
            // Request Permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    3);
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if(grantResults.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readSms_permission = true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Authorization not granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
        if (requestCode == 2){
            if(grantResults.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    gps_permission = true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Authorization not granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
        if (requestCode == 3){
            if(grantResults.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call_permission = true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Authorization not granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}










