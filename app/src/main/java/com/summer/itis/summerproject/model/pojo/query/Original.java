package com.summer.itis.summerproject.model.pojo.query;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Original
{
    @Attribute
    private String height;
    @Attribute
    private String source;
    @Attribute
    private String width;

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [height = "+height+", source = "+source+", width = "+width+"]";
    }
}
