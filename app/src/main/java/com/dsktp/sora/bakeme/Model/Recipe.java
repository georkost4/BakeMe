/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Model;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 18/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This class is used to describe a Recipe object
 * with the following fields:
 * int id representing the id of the recipe
 * String name representing the name of the recipe
 * ArrayList<Ingredient> representing a list of ingredient's used for this recipe
 * ArrayList<Step> representing a list with step's for executing the recipe
 * int servings representing the amount of serving's from the recipe
 * String Image representing the image of the final product created from this recipe
 */
public class Recipe
{
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int servings;
    @SerializedName("image")
    private String imageURL;

    /**
     * Default constructor
     * @param id The id of the recipe
     * @param name The name of the recipe
     * @param ingredients The ingredient's list of the recipe
     * @param steps The steps of execution
     * @param servings The serving's this recipe has
     * @param imageURL The image of the final product
     */
    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String imageURL) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
