package com.dlamloi.MAD.base;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public interface BaseView<T> {

    void setRecyclerViewData(ArrayList<T> t);

    void notifyItemInserted(int position);

}
