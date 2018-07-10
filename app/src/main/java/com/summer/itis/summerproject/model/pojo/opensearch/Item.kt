package com.summer.itis.summerproject.model.pojo.opensearch


import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Item")
class Item {
    @Element(name = "Text")
    var text: com.summer.itis.summerproject.model.pojo.opensearch.Text? = null

    @Element(name = "Url")
    var url: com.summer.itis.summerproject.model.pojo.opensearch.Url? = null

    @Element(name = "Description", required = false)
    var description: com.summer.itis.summerproject.model.pojo.opensearch.Description? = null

    @Element(name = "Image", required = false)
    var image: com.summer.itis.summerproject.model.pojo.opensearch.Image? = null

    @Attribute(required = false)
    var space: String? = null
}

