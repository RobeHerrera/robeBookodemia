package com.robe.robebookodemia.dataclass

import com.robe.robebookodemia.dataclass.Book
import kotlinx.serialization.Serializable

@Serializable
data class Books(val data: MutableList<Book>): java.io.Serializable
