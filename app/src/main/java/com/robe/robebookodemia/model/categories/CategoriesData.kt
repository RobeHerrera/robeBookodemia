package com.robe.robebookodemia.model.categories

import com.robe.robebookodemia.dataclass.Links
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesData(
    val type: String,
    val id: String,
    val attributes: CategoriesAttributes,
    val relationships: CategoriesRelationships,
    val links: Links
): java.io.Serializable
