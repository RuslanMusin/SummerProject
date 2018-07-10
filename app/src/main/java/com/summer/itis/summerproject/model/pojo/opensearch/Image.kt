package com.summer.itis.summerproject.model.pojo.opensearch

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root
class Image {
    @Attribute
    var height: String? = null
    @Attribute
    var source: String? = null
    @Attribute
    var width: String? = null

    override fun toString(): String {
        return "ClassPojo [height = $height, source = $source, width = $width]"
    }
}
