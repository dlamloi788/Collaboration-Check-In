package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dlamloi.MAD.R;

/**
 * Created by Don on 20/04/2018.
 */

public class UpdateViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTv;
    public TextView publishedDateTv;
    public TextView detailsTv;
    public TextView publisherTv;


    public UpdateViewHolder(View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.update_title_textview);
        publisherTv = itemView.findViewById(R.id.update_publisher_textview);
        detailsTv = itemView.findViewById(R.id.update_details_textview);
        publishedDateTv = itemView.findViewById(R.id.update_published_date_textview);

    }
}
