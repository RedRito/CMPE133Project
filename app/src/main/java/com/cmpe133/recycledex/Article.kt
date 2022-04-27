package com.cmpe133.recycledex

data class Article(var title: String = "",
                   var author: String = "",
                   var category: String = "",
                   var description: String = "",
                   var link: String = "",
                   )
{
    override fun toString(): String {
        return "$title $author $category $description"
    }
}
