package com.guilong.articledemo.logic

data class SubmitResponse(
    val code: Int,
    val data: List<Category>,
    val message: String,
    val success: Boolean
) {
    data class Category(
        val id: Int,
        val title: String
    )
}