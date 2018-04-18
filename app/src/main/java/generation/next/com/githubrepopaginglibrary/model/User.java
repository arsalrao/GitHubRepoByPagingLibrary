package generation.next.com.githubrepopaginglibrary.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

@Entity

public class User {
    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(User oldItem, User newItem) {

            return oldItem.userId == newItem.userId;
        }

        @Override
        public boolean areContentsTheSame(User oldItem, User newItem) {
            return oldItem.equals(newItem);
        }
    };
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public long userId;
    @SerializedName("login")
    @ColumnInfo(name = "first_name")
    public String firstName;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        User user = (User) obj;
        return user.userId == this.userId && user.firstName == this.firstName;

    }


}
