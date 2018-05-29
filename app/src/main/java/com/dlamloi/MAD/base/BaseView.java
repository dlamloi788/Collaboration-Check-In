package com.dlamloi.MAD.base;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

public interface BaseView<T> {

    void populateRecyclerView(ArrayList<T> t);

    void notifyItemInserted(int position);

    void hideFab();

    void showFab();
}
