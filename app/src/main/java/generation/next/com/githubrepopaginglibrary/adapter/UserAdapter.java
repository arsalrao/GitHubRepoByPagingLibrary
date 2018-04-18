package generation.next.com.githubrepopaginglibrary.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import generation.next.com.githubrepopaginglibrary.R;
import generation.next.com.githubrepopaginglibrary.model.User;
import generation.next.com.githubrepopaginglibrary.repositrary.NetworkState;
import generation.next.com.githubrepopaginglibrary.repositrary.Status;

public class UserAdapter extends PagedListAdapter<User, RecyclerView.ViewHolder> {
    private NetworkState networkstate;

    public UserAdapter() {
        super(User.DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == R.layout.item_user_list) {
            view = inflater.inflate(R.layout.item_user_list, parent, false);
            return new UserItemViewHolder(view);
        } else if (viewType == R.layout.network_state_item) {
            view = inflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateViewHolder(view);
        } else {
            throw new IllegalArgumentException("Unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_user_list:
                ((UserItemViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateViewHolder) holder).bindView(networkstate);
                break;

        }
    }

    private boolean hasExtraRow() {
        return networkstate != null && networkstate != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.item_user_list;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkstate;
        boolean previousExtraRow = hasExtraRow();
        this.networkstate = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    static class UserItemViewHolder extends RecyclerView.ViewHolder {
        TextView userId;
        TextView userName;

        UserItemViewHolder(View view) {
            super(view);
            userId = view.findViewById(R.id.userId);
            userName = view.findViewById(R.id.userName);
        }

        public void bindTo(User item) {
            userId.setText(String.valueOf(item.userId));
            userName.setText(item.firstName);
        }

    }

    public static class NetworkStateViewHolder extends RecyclerView.ViewHolder {
        TextView errorMsg;
        ProgressBar progressBar;

        NetworkStateViewHolder(View view) {
            super(view);
            errorMsg = view.findViewById(R.id.error_msg);
            progressBar = view.findViewById(R.id.progress_bar);

        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == Status.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
            if (networkState != null && networkState.getStatus() == Status.FAILED) {

                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText("Failed");
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
