package com.example.macbook.libprac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.list_viewer)
    protected TextView mListViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Realm realm = Realm.getDefaultInstance();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitApiInterface service = retrofit.create(GitApiInterface.class);

        Call<List<Repo>> reposCall = service.listRepos("inkp80");
        reposCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
                Log.d(TAG, "repos' size: " + repos.size());
                mListViewer.setText("");
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(repos);
                //for(int i=0;i<repos.size();i++){
                    //mListViewer.append(repos.get(i).getName()+"-"+repos.get(i).getId()+"\n");
                //}
                realm.commitTransaction();

                RealmResults<Repo> all = realm.where(Repo.class).findAll();
                for(int i=0;i<all.size();i++){
                    mListViewer.append(all.get(i).getName()+"-"+all.get(i).getId()+"\n");
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }


}
