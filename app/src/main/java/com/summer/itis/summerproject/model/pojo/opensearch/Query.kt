package com.summer.itis.summerproject.model.pojo.opensearch

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element

class Query {
    @Element
    var content: String? = null

    @Attribute
    var space: String? = null

    override fun toString(): String {
        return "ClassPojo [content = $content]"
    }
}
