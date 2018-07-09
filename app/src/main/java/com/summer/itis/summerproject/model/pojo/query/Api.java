package com.summer.itis.summerproject.model.pojo.query;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "api")
public class Api
{
    @Element
    private Query query;

    @Attribute
    private String batchcomplete;

    public Query getQuery ()
    {
        return query;
    }

    public void setQuery (Query query)
    {
        this.query = query;
    }

    public String getBatchcomplete ()
    {
        return batchcomplete;
    }

    public void setBatchcomplete (String batchcomplete)
    {
        this.batchcomplete = batchcomplete;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [query = "+query+", batchcomplete = "+batchcomplete+"]";
    }
}
