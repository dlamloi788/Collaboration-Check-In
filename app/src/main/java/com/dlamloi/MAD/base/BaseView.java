/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dlamloi.MAD.base;

import java.util.ArrayList;

/**
 * Created by Don on 16/05/2018.
 */

/**
 * A BaseView interface for views that require recyclerview populating
 *
 * @param <T> The type of baseview
 */
public interface BaseView<T> {

    /**
     * Populates the recyclerview with the provided type of data
     *
     * @param t the type of view
     */
    void populateRecyclerView(ArrayList<T> t);

    /**
     * Notifies the adapter that an item has been inserted
     *
     * @param position the position the item was inserted at
     */
    void notifyItemInserted(int position);

    /**
     * Hides the floating action button
     */
    void hideFab();

    /**
     * Shows the floating action button
     */
    void showFab();
}
