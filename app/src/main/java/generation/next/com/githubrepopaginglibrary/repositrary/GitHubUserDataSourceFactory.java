package generation.next.com.githubrepopaginglibrary.repositrary;

import android.arch.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;

public class GitHubUserDataSourceFactory extends android.arch.paging.DataSource.Factory {
    ItemKeyedDataSource itemKeyedDataSource;
    MutableLiveData<ItemKeyedDataSource> mutableLiveData;
    Executor executor;

    public GitHubUserDataSourceFactory(Executor executor) {
        this.executor = executor;
        this.mutableLiveData = new MutableLiveData<ItemKeyedDataSource>();
    }

    @Override
    public android.arch.paging.DataSource create() {
        itemKeyedDataSource = new ItemKeyedDataSource(executor);
        mutableLiveData.postValue(itemKeyedDataSource);

        return itemKeyedDataSource;

    }

    public MutableLiveData<ItemKeyedDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
