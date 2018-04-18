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


/**
 * This class represents an Ingredient object used by a Recipe object
 * It contains the following fields:
 * double quantity represents the quantity of the ingredient to the recipe
 * String measure represents the measuring system kg,g,lb
 * String ingredient represents the name of the ingredient
 */
public class Ingredient
{
    private double quantity;
    private String measure;
    private String ingredient;

    /**
     * Default constructor
     * @param quantity The quantity of the ingredient
     * @param measure The measuring system
     * @param ingredient The name of the ingredient
     */
    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
