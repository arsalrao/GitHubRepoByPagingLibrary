package generation.next.com.githubrepopaginglibrary.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubApi {
    public static generation.next.com.githubrepopaginglibrary.network.GitHubService createGitHubService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com");
        return builder.build().create(generation.next.com.githubrepopaginglibrary.network.GitHubService.class);
    }

}
