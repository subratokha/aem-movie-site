package com.aem.movie.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class MovieModel {

    @SlingObject
    private Resource currentResource;

    @ValueMapValue
    @Default(values = "10")
    private String results;

    public String getResults(){
        return results;
    }
}
