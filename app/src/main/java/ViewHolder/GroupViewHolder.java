package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlamloi.MAD.R;

/**
 * Created by Don on 11/04/2018.
 */

public class GroupViewHolder extends RecyclerView.ViewHolder {

    public ImageView groupDisplayIv;
    public TextView groupNameTv;
    public RelativeLayout groupParentRl;

    public GroupViewHolder(View itemView) {
        super(itemView);
        groupDisplayIv = itemView.findViewById(R.id.group_display_imageview);
        groupNameTv = itemView.findViewById(R.id.group_name_textview);
        groupParentRl = itemView.findViewById(R.id.group_parent_relativelayout);

    }
}
