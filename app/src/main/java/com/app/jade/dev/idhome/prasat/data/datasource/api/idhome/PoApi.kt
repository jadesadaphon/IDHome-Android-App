package com.app.jade.dev.idhome.prasat.data.datasource.api.idhome

import android.util.Log
import com.app.jade.dev.idhome.prasat.AppConfig
import com.app.jade.dev.idhome.prasat.ui.data.PoData
import com.app.jade.dev.idhome.prasat.ui.data.PoProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class PoApi {

    private val tag = "PoApi"

    companion object {
        private val client = OkHttpClient()
    }

    suspend fun getData(): MutableList<PoData>? {
        return withContext(Dispatchers.IO) {
            try {
                val url = "${AppConfig.API_PI}purchaseorder"
                val request = Request.Builder()
                    .url(url)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        return@withContext if (!responseBody.isNullOrEmpty()) {
                            resultFormat(responseBody)
                        } else {
                            Log.e(tag, "Response body is null or empty")
                            null
                        }
                    } else {
                        Log.e(tag, "HTTP error code: ${response.code}")
                        null
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, "Exception: ${e.message}", e)
                null
            }
        }
    }

    private suspend fun resultFormat(jsonString: String): MutableList<PoData>? {
        return withContext(Dispatchers.Main) {
            try {
                val poList = mutableListOf<PoData>()

                val data = JSONArray(jsonString)

                for (i in 0 until data.length()) {
                    val item = data.getJSONObject(i)

                    val productsJson = item.getJSONArray("สินค้า")
                    val products = mutableListOf<PoProducts>()
                    for (j in 0 until productsJson.length()) {
                        val product = productsJson.getJSONObject(j)
                        products.add(
                            PoProducts(
                                productCode = product.optString("รหัสสินค้า"),
                                productName = product.optString("ชื่อสินค้า"),
                                productBarcode = product.optString("บาร์โค้ดสินค้า"),
                                productUnit = product.optString("หน่วยสินค้า"),
                                productStockQty = product.optDouble("จำนวนสต็อก"),
                                productPoQty = product.optDouble("จำนวนสั่งซื้อ")
                            )
                        )
                    }

                    poList.add(
                        PoData(
                            documentNumber = item.optString("เลขที่เอกสาร"),
                            documentDate = item.optString("วันที่เอกสาร"),
                            credit = item.optString("เครดิต"),
                            deliveryDate = item.optString("กำหนดส่งมอบ"),
                            deliveryMethod = item.optString("การส่งมอบ"),
                            vendorCode = item.optString("รหัสเจ้าหนี้"),
                            vendorName = item.optString("ชื่อเจ้าหนี้"),
                            createdBy = item.optString("ผู้ทำเอกสาร"),
                            totalAmount = item.optDouble("รวมจำนวนเงิน"),
                            discountOnInvoice = item.optString("ส่วนลดท้ายบิล"),
                            discountAmount = item.optDouble("มูลค่าส่วนลดท้ายบิล"),
                            productValue = item.optDouble("มูลค่าสินค้า"),
                            vatRate = item.optDouble("ภาษีมูลค่าเพิ่ม"),
                            vatAmount = item.optDouble("มูลค่าภาษีมูลค่าเพิ่ม"),
                            totalAmountWithVat = item.optDouble("มูลค่ารวมภาษี"),
                            exemptValue = item.optDouble("มูลค่ายกเว้น"),
                            grandTotal = item.optDouble("รวมเงินทั้งสิ้น"),
                            currency = item.optString("สกุลเงิน"),
                            products = products
                        )
                    )
                }
                return@withContext poList
            } catch (e: Exception) {
                Log.e("resultFormat", "Exception: ${e.message}")
                return@withContext null
            }
        }
    }

}
