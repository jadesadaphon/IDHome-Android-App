package com.app.jade.dev.idhome.prasat.ui.data

data class PoData(
    val documentNumber: String = "",
    val documentDate: String = "",
    val credit: String = "",
    val deliveryDate: String = "",
    val deliveryMethod: String = "",
    val vendorCode: String = "",
    val vendorName: String = "",
    val createdBy: String = "",
    val totalAmount: Double = 0.0,
    val discountOnInvoice: String = "",
    val discountAmount: Double = 0.0,
    val productValue: Double = 0.0,
    val vatRate: Double = 0.0,
    val vatAmount: Double = 0.0,
    val totalAmountWithVat: Double = 0.0,
    val exemptValue: Double = 0.0,
    val grandTotal: Double = 0.0,
    val currency: String = "",
    val products: List<PoProducts>
)
