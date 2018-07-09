package com.summer.itis.summerproject.model.pojo.query;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Page
{
    @Attribute
    private String ns;
    @Attribute
    private String title;
    @Element(required = false)
    private Original original;
    @Attribute
    private String description;
    @Attribute
    private String _idx;
    @Attribute
    private String descriptionsource;
    @Element
    private Extract extract;
    @Attribute
    private String pageid;

    @Attribute(required = false)
    private String space;

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getNs ()
    {
        return ns;
    }

    public void setNs (String ns)
    {
        this.ns = ns;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public Original getOriginal() {
        return original;
    }

    public void setOriginal(Original original) {
        this.original = original;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String get_idx ()
    {
        return _idx;
    }

    public void set_idx (String _idx)
    {
        this._idx = _idx;
    }

    public String getDescriptionsource ()
    {
        return descriptionsource;
    }

    public void setDescriptionsource (String descriptionsource)
    {
        this.descriptionsource = descriptionsource;
    }

    public Extract getExtract ()
    {
        return extract;
    }

    public void setExtract (Extract extract)
    {
        this.extract = extract;
    }

    public String getPageid ()
    {
        return pageid;
    }

    public void setPageid (String pageid)
    {
        this.pageid = pageid;
    }
}

