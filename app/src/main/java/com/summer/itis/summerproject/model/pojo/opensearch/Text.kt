package com.summer.itis.summerproject.model.pojo.opensearch

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root
class Text {
    @org.simpleframework.xml.Text
    var content: String? = null

    @Attribute
    var space: String? = null

    override fun toString(): String {
        return "ClassPojo [content = $content]"
    }
}
