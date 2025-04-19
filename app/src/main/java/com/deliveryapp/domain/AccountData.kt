package com.deliveryapp.domain

data class AccountData(
    val uid: Int,
    val imageLink: String,
    val name: String,
    val cardForSalary: List<String>,
    val closeSalary: Salary,
    val statistic: AccountStatistic
)

data class AccountStatistic(
    val weekWorkTime: String,
    val allWorkTime: String,
    val salaryMonth: String,
    val salaryAll: String,
)

data class Salary(
    val date: String,
    val salary: String,
)






