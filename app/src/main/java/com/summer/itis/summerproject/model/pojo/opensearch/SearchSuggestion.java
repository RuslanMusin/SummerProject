package com.summer.itis.summerproject.model.pojo.opensearch;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class SearchSuggestion
{
    @Element
    private Section Section;

    @Element
    private String Query;

    @Attribute(required = false)
    private String xmlns;

    @Attribute
    private String version;

    public Section getSection ()
    {
        return Section;
    }

    public void setSection (Section Section)
    {
        this.Section = Section;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public String getXmlns ()
    {
        return xmlns;
    }

    public void setXmlns (String xmlns)
    {
        this.xmlns = xmlns;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Section = "+Section+", Query = "+Query+", xmlns = "+xmlns+", version = "+version+"]";
    }
}
