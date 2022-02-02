package com.robe.robebookodemia.model.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesRelationships(
    val books: CategoriesBook
): java.io.Serializable
