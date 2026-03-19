package com.hrm.codehigh

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform