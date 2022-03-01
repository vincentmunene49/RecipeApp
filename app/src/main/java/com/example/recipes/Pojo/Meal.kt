package com.example.recipes.Pojo

data class Meal(
    val _links: Links,
    val count: Int,
    val from: Int,
    val hits: List<Hit>,
    val to: Int
)