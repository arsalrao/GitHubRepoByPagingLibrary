package generation.next.com.githubrepopaginglibrary;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import generation.next.com.githubrepopaginglibrary.adapter.UserAdapter;
import generation.next.com.githubrepopaginglibrary.model.User;
import generation.next.com.githubrepopaginglibrary.model.UserViewModel;

public class MainActivity extends AppCompatActivity {
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.userList);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        UserAdapter adapter = new UserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        viewModel.userList.observe(this, pagedList -> {
            adapter.submitList((PagedList<User>) pagedList);
        });
        viewModel.networkState.observe(this, networkState -> {
            adapter.setNetworkState(networkState);

        });
        recyclerView.setAdapter(adapter);
    }
}
