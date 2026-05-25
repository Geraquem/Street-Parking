package com.mmfsin.streetparking.domain.interfaces

interface IConfigRepository {
    fun getRadius(): Double
    fun updateRadius(newRadius: Double)
}