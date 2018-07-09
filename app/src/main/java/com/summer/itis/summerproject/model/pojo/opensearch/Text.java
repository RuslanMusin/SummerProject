package com.summer.itis.summerproject.model.pojo.opensearch;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Text
{
    @org.simpleframework.xml.Text
    private String content;

    @Attribute
    private String space;

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+"]";
    }
}
