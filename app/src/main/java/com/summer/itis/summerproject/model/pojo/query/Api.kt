package com.summer.itis.summerproject.model.pojo.query

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "api")
class Api {
    @Element
    var query: Query? = null

    @Attribute
    var batchcomplete: String? = null

    override fun toString(): String {
        return "ClassPojo [query = $query, batchcomplete = $batchcomplete]"
    }
}
