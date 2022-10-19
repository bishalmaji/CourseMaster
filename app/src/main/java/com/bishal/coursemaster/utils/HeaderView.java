package com.bishal.coursemaster.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.bishal.coursemaster.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;


@Parent
@SingleTop
@Layout(R.layout.row_video_list)
public class HeaderView {

    private static final String TAG = "HeaderView";
    private final Context mContext;
    private final String mHeaderText;
    @View(R.id.vl_title)
    TextView headerText;

    public HeaderView(Context context, String headerText) {
        this.mContext = context;
        this.mHeaderText = headerText;
    }

    @Resolve
    private void onResolve() {
        headerText.setText(mHeaderText);
    }

    @Expand
    private void onExpand() {
        Toast.makeText(mContext, "onExpand " + mHeaderText, Toast.LENGTH_SHORT).show();
    }

    @Collapse
    private void onCollapse() {
        Toast.makeText(mContext, "onCollapse " + mHeaderText, Toast.LENGTH_SHORT).show();
    }
}