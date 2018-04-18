package generation.next.com.githubrepopaginglibrary.network;

import java.util.List;

import generation.next.com.githubrepopaginglibrary.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("/users")
    Call<List<User>> getUser(@Query("since") Long since, @Query("per_page") int perPage);
}
