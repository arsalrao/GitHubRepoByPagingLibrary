package generation.next.com.githubrepopaginglibrary.repositrary;

public class NetworkState {
    public final static NetworkState LOADED;
    public final static NetworkState LOADING;

    static {
        LOADED = new NetworkState(generation.next.com.githubrepopaginglibrary.repositrary.Status.SUCCESS, "Success");
        LOADING = new NetworkState(generation.next.com.githubrepopaginglibrary.repositrary.Status.RUNNING, "loading");

    }

    private final generation.next.com.githubrepopaginglibrary.repositrary.Status status;
    private final String msg;

    public NetworkState(generation.next.com.githubrepopaginglibrary.repositrary.Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public generation.next.com.githubrepopaginglibrary.repositrary.Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}

