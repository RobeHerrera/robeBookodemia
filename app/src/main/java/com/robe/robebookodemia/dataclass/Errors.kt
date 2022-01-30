package com.robe.robebookodemia.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Errors(
    val errors: List<Error>
    )
