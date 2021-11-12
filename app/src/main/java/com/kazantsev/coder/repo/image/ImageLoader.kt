package com.kazantsev.coder.repo.image

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}