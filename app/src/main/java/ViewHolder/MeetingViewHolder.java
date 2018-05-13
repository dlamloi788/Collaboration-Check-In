package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlamloi.MAD.R;

/**
 * Created by Don on 13/05/2018.
 */

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout meetingRowLayout;
    public View colorMarginV;
    public TextView meetingTitleTv;
    public TextView meetingDateTv;
    public TextView meetingTimeTv;
    public TextView meetingSuburbTv;
    public TextView meetingAuthorTv;

    public MeetingViewHolder(View itemView) {
        super(itemView);
        meetingRowLayout = itemView.findViewById(R.id.meeting_row_relativelayout);
        colorMarginV = itemView.findViewById(R.id.color_margin);
        meetingTitleTv = itemView.findViewById(R.id.meeting_title_textview);
        meetingDateTv = itemView.findViewById(R.id.meeting_date_textview);
        meetingTimeTv = itemView.findViewById(R.id.meeting_time_textview);
        meetingSuburbTv = itemView.findViewById(R.id.meeting_suburb_textview);
        meetingAuthorTv = itemView.findViewById(R.id.meeting_author_textview);

    }
}
