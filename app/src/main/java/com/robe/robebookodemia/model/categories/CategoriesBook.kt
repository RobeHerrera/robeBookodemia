package com.robe.robebookodemia.model.categories

import com.robe.robebookodemia.dataclass.Links
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesBook(
    val links: Links
): java.io.Serializable
