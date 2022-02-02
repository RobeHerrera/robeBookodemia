package com.robe.robebookodemia.model.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesAttributes(
    val name: String,
    val slug: String
): java.io.Serializable