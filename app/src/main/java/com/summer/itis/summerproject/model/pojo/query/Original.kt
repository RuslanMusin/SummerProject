package com.summer.itis.summerproject.model.pojo.query

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element

class Original {
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
