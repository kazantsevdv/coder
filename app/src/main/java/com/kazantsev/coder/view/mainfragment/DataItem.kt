package com.kazantsev.coder.view.mainfragment

sealed class DataItem {
    data class ItemName(
        val id: String,
        val avatarUrl: String,
        val name: String,
        val userTag: String,
        val position: String
    ) : DataItem()

    data class ItemBirthday(
        val id: String,
        val avatarUrl: String,
        val name: String,
        val userTag: String,
        val position: String,
        val birthday: String
    ) : DataItem()

    data class Delimiter(
        val date: String
    ) : DataItem()
}