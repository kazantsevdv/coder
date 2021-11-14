package com.kazantsev.coder.view.profilefragment

import com.kazantsev.coder.repo.db.model.UsersDb

data class UserProfile(
    val id: String,
    val avatarUrl: String,
    val name: String,
    val userTag: String,
    val department: String,
    val position: String,
    val birthday: String,
    val years:String,
    val phone: String
)

