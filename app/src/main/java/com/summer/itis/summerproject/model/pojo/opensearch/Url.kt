package com.summer.itis.summerproject.model.pojo.opensearch

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root
class Url {
    @Text
    var content: String? = null

    @Attribute
    var space: String? = null

    override fun toString(): String {
        return "ClassPojo [content = $content]"
    }
}
