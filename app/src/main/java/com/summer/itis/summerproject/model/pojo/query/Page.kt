package com.summer.itis.summerproject.model.pojo.query


import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root
class Page {
    @Attribute
    var ns: String? = null
    @Attribute
    var title: String? = null
    @Element(required = false)
    var original: Original? = null
    @Attribute
    var description: String? = null
    @Attribute
    var _idx: String? = null
    @Attribute
    var descriptionsource: String? = null
    @Element
    var extract: Extract? = null
    @Attribute
    var pageid: String? = null

    @Attribute(required = false)
    var space: String? = null
}

