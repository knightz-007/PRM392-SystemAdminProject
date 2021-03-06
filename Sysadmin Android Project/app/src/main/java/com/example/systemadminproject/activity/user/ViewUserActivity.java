package com.example.systemadminproject.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.systemadminproject.R;
import com.example.systemadminproject.activity.brand.ViewBrandActivity;
import com.example.systemadminproject.model.brand.BrandData;
import com.example.systemadminproject.model.brand.BrandResponse;
import com.example.systemadminproject.model.user.UserData;
import com.example.systemadminproject.model.user.UserResponse;
import com.example.systemadminproject.service.BrandService;
import com.example.systemadminproject.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserActivity extends AppCompatActivity {
    private GridView gvUser;
    UserAdapter userAdapter = new UserAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        gvUser = findViewById(R.id.gvUser);

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed (() -> {
            callAPI();
        }, 1000);

    }

    public void callAPI() {
        UserService.getApi().getUsersList("", 0, 1, 999)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        List<UserData> userList = response.body().getData();
                        userAdapter.setUserList(userList);
                        gvUser.setAdapter(userAdapter);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(ViewUserActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickToSearch(View view) {
        EditText searchName = findViewById(R.id.txtSearchName);
        String searchTerm = searchName.getText().toString();

        UserService.getApi().getUsersList(searchTerm, 0, 1, 999)
            .enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            List<UserData> userList = response.body().getData();
                            userAdapter.setUserList(userList);
                            gvUser.setAdapter(userAdapter);
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Toast.makeText(ViewUserActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    public void clickToReset(View view) {
        EditText searchName = findViewById(R.id.txtSearchName);
        searchName.getText().clear();
        callAPI();
        gvUser.setAdapter(userAdapter);
    }
}