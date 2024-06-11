package com.example.sjsk.firestore

import android.util.Log
import com.google.gson.annotations.SerializedName
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.InputStream


//API에서 받을 JSON 데이터에 맞춰 Kotlin 데이터 클래스를 정의

@Root(name = "response", strict = false)
data class ApiResponse(
//    @SerializedName("Header")
//    val header: String,
    @field:Element(name = "body", required = false)
    var body: Body? = null
)

//data class Header(
//    @SerializedName("Body")
//    val resultCode: String,
//    @SerializedName("Body")
//    val resultMsg: String
//)

@Root(name = "body", strict = false)
data class Body(
    @field:Element(name = "numOfRows", required = false)
    var numOfRows: String? = null,

    @field:Element(name = "pageNo", required = false)
    var pageNo: String? = null,

    @field:Element(name = "totalCount", required = false)
    var totalCount: String? = null,

    @field:ElementList(name = "items", required = false, inline = true)
    var items: List<Item>? = null
)

@Root(name = "item", strict = false)
data class Item(
//    val NUM: String, //번호

    @field:Element(name = "FOOD_CD", required = false)
    var FOOD_CD: String? = null,

    @field:Element(name = "ITEM_REPORT_NO", required = false)
    var ITEM_REPORT_NO: String? = null,

    @field:Element(name = "FOOD_NM_KR", required = false)
    var FOOD_NM_KR: String? = null,

    @field:Element(name = "FOOD_OR_NM", required = false)
    var FOOD_OR_NM: String? = null,

    @field:Element(name = "FOOD_REF_NM", required = false)
    var FOOD_REF_NM: String? = null,

    @field:Element(name = "FOOD_CAT3_NM", required = false)
    var FOOD_CAT3_NM: String? = null,

    @field:Element(name = "SERVING_SIZE", required = false)
    var SERVING_SIZE: String? = null,

    @field:Element(name = "AMT_NUM1", required = false)
    var AMT_NUM1: String? = null,

    @field:Element(name = "AMT_NUM3", required = false)
    var AMT_NUM3: String? = null,

    @field:Element(name = "AMT_NUM4", required = false)
    var AMT_NUM4: String? = null,

    @field:Element(name = "AMT_NUM6", required = false)
    var AMT_NUM6: String? = null,

    @field:Element(name = "AMT_NUM7", required = false)
    var AMT_NUM7: String? = null,

    @field:Element(name = "AMT_NUM13", required = false)
    var AMT_NUM13: String? = null,

    @field:Element(name = "AMT_NUM22", required = false)
    var AMT_NUM22: String? = null,

    @field:Element(name = "AMT_NUM23", required = false)
    var AMT_NUM23: String? = null,

    @field:Element(name = "NUTRI_AMOUNT_SERVING", required = false)
    var NUTRI_AMOUNT_SERVING: String? = null,

    @field:Element(name = "Z10500", required = false)
    var Z10500: String? = null,

    @field:Element(name = "MAKER_NM", required = false)
    var MAKER_NM: String? = null,

    @field:Element(name = "UPDATE_YMD", required = false)
    var UPDATE_YMD: String? = null

)

































//
//fun readExcelFile(inputStream: InputStream): List<NutritionInfo> {
//    val nutritionInfoList = mutableListOf<NutritionInfo>()
//    val logTag = "ExcelReader"
//
//    try {
//        val workbook = WorkbookFactory.create(inputStream) as XSSFWorkbook
//        val sheet = workbook.getSheetAt(0)
//
//        for (row in sheet) {
//            if (row.rowNum == 0) continue // 첫 번째 행은 헤더이므로 건너뜀
//
//            val tlrvnazhem_A = row.getCell(0)?.toString() ?: ""
//            val vnaahrwpwhqhrhqjsgh_B = row.getCell(1)?.toString() ?: ""
//            val tlrvnaaud_C = row.getCell(2)?.toString() ?: ""
//            val tlrvnarldnjsaud_G = row.getCell(6)?.toString() ?: ""
//            val eovytlrvnaaud_K = row.getCell(10)?.toString() ?: ""
//            val tlrvnathqnsfbaud_O = row.getCell(14)?.toString() ?: ""
//            val duddidtjdqnsgkafidrlwnsfid_R = row.getCell(17)?.toString() ?: ""
//            val dpsjwl_S = row.getCell(18)?.toString() ?: ""
//            val eksqorwlf_U = row.getCell(20)?.toString() ?: ""
//            val wlqkd_V = row.getCell(21)?.toString() ?: ""
//            val xkstnghkanf_X = row.getCell(23)?.toString() ?: ""
//            val ekdfb_Y = row.getCell(24)?.toString() ?: ""
//            val skxmfba_AE = row.getCell(30)?.toString() ?: ""
//            val zhffptmxpfhf_AN = row.getCell(39)?.toString() ?: ""
//            val vhghkwlqkdtks_AO = row.getCell(40)?.toString() ?: ""
//            val dlfghltjqcnlckarhfid_EV = row.getCell(151)?.toString() ?: ""
//            val tlrvnawndfid_EW = row.getCell(152)?.toString() ?: ""
//            val wpwhtkaud_EX = row.getCell(153)?.toString() ?: ""
//            val epdlxjrlwnsdlfwk_FG = row.getCell(162)?.toString() ?: ""
//
//            // 로그 출력
//            Log.d(logTag, "Row ${row.rowNum}: vnaahrwpwhqhrhqjsgh_B=$vnaahrwpwhqhrhqjsgh_B, tlrvnaaud_C=$tlrvnaaud_C, tlrvnarldnjsaud_G=$tlrvnarldnjsaud_G, eovytlrvnaaud_K=$eovytlrvnaaud_K, tlrvnathqnsfbaud_O=$tlrvnathqnsfbaud_O, duddidtjdqnsgkafidrlwnsfid_R=$duddidtjdqnsgkafidrlwnsfid_R, dpsjwl_S=$dpsjwl_S, eksqorwlf_U=$eksqorwlf_U, wlqkd_V=$wlqkd_V, xkstnghkanf_X=$xkstnghkanf_X, ekdfb_Y=$ekdfb_Y, skxmfba_AE=$skxmfba_AE, zhffptmxpfhf_AN=$zhffptmxpfhf_AN, vhghkwlqkdtks_AO=$vhghkwlqkdtks_AO, dlfghltjqcnlckarhfid_EV=$dlfghltjqcnlckarhfid_EV, tlrvnawndfid_EW=$tlrvnawndfid_EW, wpwhtkaud_EX=$wpwhtkaud_EX, epdlxjrlwnsdlfwk_FG=$epdlxjrlwnsdlfwk_FG")
//
//            nutritionInfoList.add(
//                NutritionInfo(
//                    vnaahrwpwhqhrhqjsgh_B,
//                    tlrvnaaud_C,
//                    tlrvnarldnjsaud_G,
//                    eovytlrvnaaud_K,
//                    tlrvnathqnsfbaud_O,
//                    duddidtjdqnsgkafidrlwnsfid_R,
//                    dpsjwl_S,
//                    eksqorwlf_U,
//                    wlqkd_V,
//                    xkstnghkanf_X,
//                    ekdfb_Y,
//                    skxmfba_AE,
//                    zhffptmxpfhf_AN,
//                    vhghkwlqkdtks_AO,
//                    dlfghltjqcnlckarhfid_EV,
//                    tlrvnawndfid_EW,
//                    wpwhtkaud_EX,
//                    epdlxjrlwnsdlfwk_FG
//                )
//            )
//        }
//
//        workbook.close()
//    } catch (e: OutOfMemoryError) {
//        Log.e(logTag, "Out of Memory Error while reading the Excel file", e)
//    } catch (e: Exception) {
//        Log.e(logTag, "Exception while reading the Excel file", e)
//    }
//
//    return nutritionInfoList
//}