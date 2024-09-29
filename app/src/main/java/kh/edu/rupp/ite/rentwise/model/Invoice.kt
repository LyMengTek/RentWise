package kh.edu.rupp.ite.rentwise.model

import java.util.Date

data class Invoice(
    val dueDate: Date,
    val paid: Boolean,
    val room: Room,
    val electricity: Double,
    val water: Double,
    val other: Double,
    val user: User
)
