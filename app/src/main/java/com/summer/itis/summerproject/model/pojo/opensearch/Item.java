package com.summer.itis.summerproject.model.pojo.opensearch;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Item")
public class Item
{
    @Element(name = "Text")
    private Text Text;

    @Element(name = "Url")
    private Url Url;

    @Element(name = "Description",required = false)
    private Description Description;

    @Element(name = "Image",required = false)
    private Image Image;

    @Attribute(required = false)
    private String space;

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public com.summer.itis.summerproject.model.pojo.opensearch.Image getImage() {
        return Image;
    }

    public void setImage(com.summer.itis.summerproject.model.pojo.opensearch.Image image) {
        Image = image;
    }

    public com.summer.itis.summerproject.model.pojo.opensearch.Text getText() {
        return Text;
    }

    public void setText(com.summer.itis.summerproject.model.pojo.opensearch.Text text) {
        Text = text;
    }

    public com.summer.itis.summerproject.model.pojo.opensearch.Description getDescription() {
        return Description;
    }

    public void setDescription(com.summer.itis.summerproject.model.pojo.opensearch.Description description) {
        Description = description;
    }

    public com.summer.itis.summerproject.model.pojo.opensearch.Url getUrl() {
        return Url;
    }

    public void setUrl(com.summer.itis.summerproject.model.pojo.opensearch.Url url) {
        Url = url;
    }
}

