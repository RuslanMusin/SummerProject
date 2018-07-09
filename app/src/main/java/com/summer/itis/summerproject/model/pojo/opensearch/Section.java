package com.summer.itis.summerproject.model.pojo.opensearch;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class Section
{
    @ElementList(inline = true, required = false)
    private List<Item> Item;

    public List<Item> getItem() {
        return Item;
    }

    public void setItem(List<Item> item) {
        Item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Item = "+Item+"]";
    }
}
