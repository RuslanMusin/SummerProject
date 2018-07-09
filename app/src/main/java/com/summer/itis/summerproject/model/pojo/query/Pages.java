package com.summer.itis.summerproject.model.pojo.query;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Pages
{
    @ElementList(inline = true,required = false)
    private List<Page> page;

    public List<Page> getPage() {
        return page;
    }

    public void setPage(List<Page> page) {
        this.page = page;
    }
}
