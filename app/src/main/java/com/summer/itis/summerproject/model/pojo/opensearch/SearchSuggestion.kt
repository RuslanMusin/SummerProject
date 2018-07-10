package com.summer.itis.summerproject.model.pojo.opensearch

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element

class SearchSuggestion {
    @Element
    var section: Section? = null

    @Element
    var query: String? = null

    @Attribute(required = false)
    var xmlns: String? = null

    @Attribute
    var version: String? = null

    override fun toString(): String {
        return "ClassPojo [Section = $section, Query = $query, xmlns = $xmlns, version = $version]"
    }
}
