/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dsktp.sora.bakeme.Rest.RecipeClient;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        RecipeClient.makeRequest();
    }
}
