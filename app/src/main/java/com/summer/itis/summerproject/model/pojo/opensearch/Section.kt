package com.summer.itis.summerproject.model.pojo.opensearch

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
class Section {
    @ElementList(inline = true, required = false)
    var item: List<Item>? = null

    override fun toString(): String {
        return "ClassPojo [Item = $item]"
    }
}
