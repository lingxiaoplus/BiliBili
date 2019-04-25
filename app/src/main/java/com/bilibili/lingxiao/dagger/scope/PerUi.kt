package com.bilibili.lingxiao.dagger.scope

import javax.inject.Scope
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 自定义Scope实现单例
 * 不只用dagger提供的Singleton而且还用自定义的，是因为我们使用的域不一样
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class PerUi