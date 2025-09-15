package com.takaobrog.androidapiapp.time

interface TimeProvider {
    fun now(): java.time.Instant
}