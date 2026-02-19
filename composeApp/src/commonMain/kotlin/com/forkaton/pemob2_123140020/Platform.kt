package com.forkaton.pemob2_123140020

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform