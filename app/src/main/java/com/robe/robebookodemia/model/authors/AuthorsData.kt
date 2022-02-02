package com.robe.robebookodemia.model.authors

import com.robe.robebookodemia.dataclass.Links
import com.robe.robebookodemia.model.categories.CategoriesRelationships
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorsData(
    val type: String,
    val id: String,
    val attributes: AuthorsAttributes,
    val relationships: CategoriesRelationships,
    val links: Links
): java.io.Serializable
