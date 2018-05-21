/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.bakeme.R;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 24/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class creates a Fragment , spesifically the fragment containing the navigation bar to change the step you are in from
 * the selected recipe.
 */
public class NavBarFragment extends Fragment
{
    private  final String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    private  View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.d(DEBUG_TAG,"------------ON CREATE VIEW---------------");
        //inflate the view
        if(mView == null)
        {
            mView = inflater.inflate(R.layout.fragment_nav_bar, container, false);
        }
        return mView;
    }
}
