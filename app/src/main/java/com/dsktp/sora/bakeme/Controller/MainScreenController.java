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
import java.util.Collections;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contain's helper methods for interacting
 * with the MainScreenActivity class and the fragment's hosted
 * by the activity
 */
public class MainScreenController {

    private String DEBUG_TAG = "MainScreenController";

    private static MainScreenController instance = null;

    private MyRecipeAdapter mAdapter = null;

    private LocalRepository mLocalRepo;
    private RemoteRepository mRemoteRepo;


    /**
     * Private consructor to work along with the getController() method
     * to make usage of the Singleton pattern
     */
    private MainScreenController()
    {
        //instantiate the repo object's
        mLocalRepo = new LocalRepository();
        mRemoteRepo = new RemoteRepository(this);//todo maybe unnesesary instatiation
    }

    /**
     * This method create's a single instance of this class
     * @return a MainScreenController object
     */
    public static MainScreenController getController()
    {
        if(instance == null)
        {
           instance =  new MainScreenController();
        }
        return instance;
    }


    /**
     * This method inform's the recyclerView adapter with the new Recipe data
     * fetched from the repository and update's the UI
     * @param list The list containing the Recipe's from the Repository
     */
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


    /**
     * Setter method for the MyRecipeAdapter field
     * @param adapter The adapter for the recyclerView
     */
    public void setAdapter(MyRecipeAdapter adapter)
    {
      mAdapter = adapter;
    }


    /**
     * Setter method for the mLocalRepo field
     * @param mLocalRepo the LocalRepository object
     */
    public void setLocalRepo(LocalRepository mLocalRepo) {
        this.mLocalRepo = mLocalRepo;
    }

    /**
     * Getter method for the mLocalRepo field
     * @return the mLocalRepo object
     */
    public LocalRepository getLocalRepo() {
        return mLocalRepo;
    }

    /**
     * Method for saving the recipe list fetched from the web to the local sqlite database.
     * @param recipes The recipe list fetched from the network
     */
    public void saveToLocalRepository(final ArrayList<Recipe> recipes)
    {
        //insert only if there isn't any previously data
        if(!mLocalRepo.checkIfThereIsCachedList())
        {
            mLocalRepo.saveAllRecipe(recipes);
        }
    }

    /**
     * This method fetche's the recipe list from the Repository local or remote
     * depending on whether we have a local copy at the database
     * @return The list from the local repo or null if we
     */
    public ArrayList<Recipe> fetchRecipes() {

        //check to see if there is a cached list from local repository
        boolean doWeHaveCachedList = mLocalRepo.checkIfThereIsCachedList();

        if(doWeHaveCachedList)
        {
            //we have a cached recipe list to the local repo
            //so return the list
            return mLocalRepo.getAllRecipes();
        }
        else
        {
            //make a request to the remote repo
            mRemoteRepo.getRecipes(); // todo change the async to synchronous method
        }
        return null;
    }
}
