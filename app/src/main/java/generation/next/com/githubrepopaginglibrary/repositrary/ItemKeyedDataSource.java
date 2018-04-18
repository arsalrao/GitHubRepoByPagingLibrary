package generation.next.com.githubrepopaginglibrary.repositrary;
//A data source that uses the "userId" field of User as the key for next/prev user list.

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import generation.next.com.githubrepopaginglibrary.model.User;
import generation.next.com.githubrepopaginglibrary.network.GitHubApi;
import generation.next.com.githubrepopaginglibrary.network.GitHubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemKeyedDataSource extends android.arch.paging.ItemKeyedDataSource<Long, User> {
    public static final String TAG = ItemKeyedDataSource.class.getSimpleName();
    GitHubService gitHubService;
    LoadInitialParams<Long> initialParams;
    LoadParams<Long> afterParams;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;

    public ItemKeyedDataSource(Executor retryExecutor) {
        this.retryExecutor = retryExecutor;
        gitHubService = GitHubApi.createGitHubService();
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<User> callback) {
        Log.i(TAG, "Loading range " + 1 + " count " + params.requestedLoadSize);
        initialParams = params;
        final List<User> gitHubUserList = new ArrayList<>();
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        gitHubService.getUser(Long.parseLong("1"), params.requestedLoadSize)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful() && response.code() == 200) {
                            gitHubUserList.addAll(response.body());
                            callback.onResult(gitHubUserList);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);
                            initialParams = null;
                        } else {
                            initialLoading.postValue(new NetworkState(Status.FAILED, "Failed"));
                            networkState.postValue(new NetworkState(Status.FAILED, "Failed"));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        String errorMsg = t.getMessage();
                        if (t == null) {
                            errorMsg = "Unknown error";
                        }
                        networkState.postValue(new NetworkState(Status.FAILED, errorMsg));
                    }
                });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull final LoadCallback<User> callback) {
        Log.i(TAG, "Loading Range " + params.key + " Count " + params.requestedLoadSize);
        afterParams = params;
        final List<User> gitHubUserList = new ArrayList<>();
        networkState.postValue(NetworkState.LOADING);
        gitHubService.getUser(params.key, params.requestedLoadSize)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful() && response.code() == 200) {
                            gitHubUserList.addAll(response.body());
                            callback.onResult(gitHubUserList);
                            networkState.postValue(NetworkState.LOADED);
                            afterParams = null;
                        } else {
                            networkState.postValue(new NetworkState(Status.FAILED, "Failed"));
                            Log.e(TAG, response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        String errMsg = t.getMessage();
                        if (t == null) {
                            errMsg = "Unknown error";
                        }
                        networkState.postValue(new NetworkState(Status.FAILED, errMsg));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull User item) {
        return item.userId;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }
}
