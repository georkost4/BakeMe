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
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Repository.LocalRepository;
import com.dsktp.sora.bakeme.Repository.RemoteRepository;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class MainScreenController {

    private String DEBUG_TAG = "MainScreenController";

    private static MainScreenController instance = null;

    private ArrayList<Recipe> mRecipeList = null;
    private MyRecipeAdapter mAdapter = null;

    private LocalRepository mLocalRepo;
    private RemoteRepository mRemoteRepo;

    private MainScreenController()
    {
        //instantiate the repo object's
        mLocalRepo = new LocalRepository();
        mRemoteRepo = new RemoteRepository(this);//todo maybe unnesesary instatiation
    }

    public static MainScreenController getController()
    {
        if(instance == null)
        {
           instance =  new MainScreenController();
        }
        return instance;
    }

    public ArrayList<Recipe> makeNetworkRequestForRecipes() {
        return mRecipeList;
    }

    public void setRecipeList(ArrayList<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
    }



    public void updateTheUi(ArrayList<Recipe> list)
    {
       if(mAdapter!=null)
       {
           // if there is an adapter update the UI
           mAdapter.setRecipeList(list);
       }
       else
       {
           //just log an error
           Log.e(DEBUG_TAG,"There was no adapter set  to update the UI ");
       }

    }


    public void setAdapter(MyRecipeAdapter adapter)
    {
      mAdapter = adapter;
    }



    public void setLocalRepo(LocalRepository mLocalRepo) {
        this.mLocalRepo = mLocalRepo;
    }

    public LocalRepository getLocalRepo() {
        return mLocalRepo;
    }

    public void saveToLocalRepository(final ArrayList<Recipe> recipes)
    {
        AsyncTask anonym = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects)
            {
                //insert only if there isn't any previously data
                if(!mLocalRepo.checkIfThereIsCachedList()) {
                    mLocalRepo.saveAllRecipe(recipes);
                }
                return null;
            }
        }.execute();
    }

    public ArrayList<Recipe> fetchRecipes() {

        //check to see if there is a cached list from local repository
        boolean doWeHaveCachedList = mLocalRepo.checkIfThereIsCachedList();

        if(doWeHaveCachedList)
        {
            return mLocalRepo.getAllRecipes();
        }
        else
        {
            //make a request to the remote repo
            mRemoteRepo.getRecipes();
        }
        return null;
    }
}
