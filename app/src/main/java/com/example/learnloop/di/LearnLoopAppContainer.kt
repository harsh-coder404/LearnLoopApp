package com.example.learnloop.di

import com.example.learnloop.data.repository.FakeLearnLoopRepository
import com.example.learnloop.data.repository.LearnLoopRepository
import com.example.learnloop.data.source.FakeLearnLoopDataSource

object LearnLoopAppContainer {
    val repository: LearnLoopRepository by lazy {
        FakeLearnLoopRepository(FakeLearnLoopDataSource())
    }
}
