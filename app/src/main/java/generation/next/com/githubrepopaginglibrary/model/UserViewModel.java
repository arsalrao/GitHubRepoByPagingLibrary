package generation.next.com.githubrepopaginglibrary.model;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import generation.next.com.githubrepopaginglibrary.repositrary.GitHubUserDataSourceFactory;
import generation.next.com.githubrepopaginglibrary.repositrary.ItemKeyedDataSource;
import generation.next.com.githubrepopaginglibrary.repositrary.NetworkState;

public class UserViewModel extends ViewModel {
    public LiveData userList;
    public LiveData<NetworkState> networkState;
    Executor executor;
    LiveData<ItemKeyedDataSource> tDataSource;

    public UserViewModel() {
        executor = Executors.newFixedThreadPool(5);
        GitHubUserDataSourceFactory gitHubUserDataSourceFactory = new GitHubUserDataSourceFactory(executor);
        tDataSource = gitHubUserDataSourceFactory.getMutableLiveData();
        networkState = Transformations.switchMap(gitHubUserDataSourceFactory.getMutableLiveData(), (Function<ItemKeyedDataSource, LiveData<NetworkState>>) ItemKeyedDataSource::getNetworkState);

        PagedList.Config pageListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build();

        userList = new LivePagedListBuilder(gitHubUserDataSourceFactory, pageListConfig).setFetchExecutor(executor).build();
                /*.setBackgroundThreadExecutor(executor)
                .build()*/

    }

}

