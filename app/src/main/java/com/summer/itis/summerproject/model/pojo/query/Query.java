package com.summer.itis.summerproject.model.pojo.query;

import org.simpleframework.xml.Element;

public class Query
{
    @Element
    private Pages pages;

    public Pages getPages ()
    {
        return pages;
    }

    public void setPages (Pages pages)
    {
        this.pages = pages;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pages = "+pages+"]";
    }
}
