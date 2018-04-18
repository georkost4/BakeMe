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
 * This class is used to represent a Step object used by a Recipe object and
 * describes the steps that one has to follow for a recipe . It contain's the
 * following fields:
 * int id representing the id of the step
 * String shortDescription representing a short description of the step
 * String description representing a full description of the step
 * String videoURL representing a url pointing to a video resource showing the step to execute
 * String thumbnailURL representing a url pointing to the video thumbnail url
 */
public class Step
{
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    /**
     * Default constructor
     * @param id The id of the step
     * @param shortDescription The short description of the step
     * @param description The full description of the step
     * @param videoURL The video demonstrating the step
     * @param thumbnailURL The thumbnail url of the video
     */
    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
