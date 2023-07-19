package poly.mobile.findfriends;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MySmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String messageBody,phoneNumber;
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle =intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    messageBody = messages[0].getMessageBody();
                    phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Toast.makeText(context,"Message : "+messageBody+"Received from;"+ phoneNumber, Toast.LENGTH_LONG ).show();

                    if(messageBody.contains("findFriends")){
                        //Show notification
                        NotificationCompat.Builder mynotif = new NotificationCompat.Builder(context, "findFriendsCannel");
                        mynotif.setContentTitle("Find friends");
                        mynotif.setContentText("New position received.");
                        mynotif.setSmallIcon(android.R.drawable.ic_dialog_map);
                        mynotif.setAutoCancel(true);

                        Intent i = new Intent(context,MapsActivity.class);

                        PendingIntent pi = PendingIntent.getActivity(context,1,i,PendingIntent.FLAG_UPDATE_CURRENT);
                        mynotif.setContentIntent(pi);

                        // Vibration of the phone
                        mynotif.setVibrate(new long[]{ 0,1000,1000,2000});

                        NotificationManagerCompat manager= NotificationManagerCompat.from(context);

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel canal = new NotificationChannel(
                                    "findFriendsCannel",
                                    "Channel for app find me",
                                    NotificationManager.IMPORTANCE_DEFAULT);
                            AudioAttributes attr=new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .setUsage(AudioAttributes.USAGE_ALARM)
                                    .build();
                            Uri son = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            canal.setSound(son,attr);
                            manager.createNotificationChannel(canal);
                        }
                        manager.notify(0,mynotif.build());
                    }
                }
            }
        }
    }
}