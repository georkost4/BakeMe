package com.dsktp.sora.bakeme;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dsktp.sora.bakeme.Model.Ingredient;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Model.Step;
import com.dsktp.sora.bakeme.Repository.Local.DaoAccess;
import com.dsktp.sora.bakeme.Repository.Local.RecipeDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RoomDatabaseTest {
    private DaoAccess mRecipeDao;
    private RecipeDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, RecipeDatabase.class).build();
        mRecipeDao = mDb.daoAccess();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeRecipeAndReadFromDatabase() throws Exception
    {
        //Create Ingredient Test ArrayList with fake data
        Ingredient testIngredient = new Ingredient(2.0,"g","pepermint");
        Ingredient testIngredient1 = new Ingredient(4.0,"kg","black_mamba");
        ArrayList<Ingredient> testIngredients = new ArrayList<>();
        testIngredients.add(testIngredient);
        testIngredients.add(testIngredient1);
        //Create Step Test ArrayList with fake data
        Step testStep = new Step(1,"2","ada3","4","5");
        Step testStep1 = new Step(2,"2faf","423","4121asd","5");
        ArrayList<Step> testSteps = new ArrayList<>();
        testSteps.add(testStep1);
        testSteps.add(testStep);
        //Create fake Recipe objects and insert them to the db
        Recipe testRecipe = new Recipe(1,"Papia",testIngredients,testSteps,2,"sd");
        Recipe testRecipe1 = new Recipe(2,"Papia",testIngredients,testSteps,2,"sd");
        mRecipeDao.insert(testRecipe);
        mRecipeDao.insert(testRecipe1);
        List<Recipe> recipeFromDatabase = mRecipeDao.getRecipes();
        //Assert that the recipe id from the database matches the recipe id inserted
        assertThat(recipeFromDatabase.get(0).getId(), equalTo(testRecipe.getId()));
        assertThat(recipeFromDatabase.get(1).getId(), equalTo(testRecipe1.getId()));
    }
}
