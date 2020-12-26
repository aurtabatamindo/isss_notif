package com.example.isss_usr.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.isss_usr.R;
import com.example.isss_usr.SendNotificationPack.APIService;
import com.example.isss_usr.SendNotificationPack.Client;
import com.example.isss_usr.SendNotificationPack.Data;
import com.example.isss_usr.SendNotificationPack.MyResponse;
import com.example.isss_usr.SendNotificationPack.NotificationSender;
import com.example.isss_usr.SendNotificationPack.Token;
import com.example.isss_usr.SharedPrefmanager;
import com.example.isss_usr.Start;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private HomeViewModel homeViewModel;
    Button btn_map, btn_incident, btn_multimedia, btn_test,btn_logout, btn_start,send;
    private static SharedPrefmanager mInstance;
    private static Context mCtx;

    private APIService apiService;
    final private String admin = "kYXn3HuW8UahtK8KEF5vJahgUQe2";
    final private String title = "!SOS!";
    final private String pesan = "!PERMINTAAN TOLONG!";


    private void SharedPrefmanager(Context context) {
        mCtx = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        btn_start = (Button) root.findViewById(R.id.btn_start);
        send = (Button) root.findViewById(R.id.send);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Tokens").child(admin.toString().trim()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String usertoken=dataSnapshot.getValue(String.class);
                        sendNotifications(usertoken, title.toString().trim(),pesan.toString().trim());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        UpdateToken();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Start.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
////calling the logout method
//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().finish();
//                SharedPrefmanager.getInstance(getApplicationContext()).logout();
//            }
//        });


        return root;
    }
    private void UpdateToken(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        Token token= new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(HomeFragment.mCtx, "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}