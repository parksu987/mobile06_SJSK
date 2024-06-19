package com.example.myapplication.firestore

import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.InputStream


//API에서 받을 JSON 데이터에 맞춰 Kotlin 데이터 클래스를 정의

@Root(name = "response", strict = false)
data class ApiResponse(
    @field:Element(name = "header", required = false)
    var header: Header? = null,

    @field:Element(name = "body", required = false)
    var body: Body? = null
)

@Root(name = "header", strict = false)
data class Header(
    @field:Element(name = "resultCode", required = false)
    var resultCode: String? = null,

    @field:Element(name = "resultMsg", required = false)
    var resultMsg: String? = null
)

@Root(name = "body", strict = false)
data class Body(
    @field:Element(name = "numOfRows", required = false)
    var numOfRows: String? = null,

    @field:Element(name = "pageNo", required = false)
    var pageNo: String? = null,

    @field:Element(name = "totalCount", required = false)
    var totalCount: String? = null,

    @field:ElementList(entry = "items", required = false, inline = true)
    var items: ArrayList<Items>? = null
)


@Root(name = "items", strict = false)
data class Items(
    @field:ElementList(entry = "item", required = false, inline = true)
    var item: ArrayList<Item>? = null
)

@Parcelize
@Root(name = "item", strict = false)
data class Item(

    @field:Element(name = "FOOD_CD", required = false)
    var FOOD_CD: String? = null, //식품코드

    @field:Element(name = "ITEM_REPORT_NO", required = false)
    var ITEM_REPORT_NO: String? = null, //품목제조보고번호

    @field:Element(name = "FOOD_NM_KR", required = false)
    var FOOD_NM_KR: String? = null, //식품명

    @field:Element(name = "FOOD_OR_NM", required = false)
    var FOOD_OR_NM: String? = null, //식품기원명

    @field:Element(name = "FOOD_REF_NM", required = false)
    var FOOD_REF_NM: String? = null, //대표식품명

    @field:Element(name = "FOOD_CAT3_NM", required = false)
    var FOOD_CAT3_NM: String? = null, //식품소분류명

    @field:Element(name = "SERVING_SIZE", required = false)
    var SERVING_SIZE: String? = null, //영양성분함량기준량

    @field:Element(name = "AMT_NUM1", required = false)
    var AMT_NUM1: String? = null, //에너지(kcal)

    @field:Element(name = "AMT_NUM3", required = false)
    var AMT_NUM3: String? = null, //단백질(g)

    @field:Element(name = "AMT_NUM4", required = false)
    var AMT_NUM4: String? = null, //지방(g)

    @field:Element(name = "AMT_NUM6", required = false)
    var AMT_NUM6: String? = null, //탄수화물(g)

    @field:Element(name = "AMT_NUM7", required = false)
    var AMT_NUM7: String? = null, //당류(g)

    @field:Element(name = "AMT_NUM13", required = false)
    var AMT_NUM13: String? = null, //나트륨(mg)

    @field:Element(name = "AMT_NUM22", required = false)
    var AMT_NUM22: String? = null, //콜레스테롤(mg)

    @field:Element(name = "AMT_NUM23", required = false)
    var AMT_NUM23: String? = null, //포화지방산(g)

    @field:Element(name = "NUTRI_AMOUNT_SERVING", required = false)
    var NUTRI_AMOUNT_SERVING: String? = null, //1회 섭취참고량

    @field:Element(name = "Z10500", required = false)
    var Z10500: String? = null, //식품중량

    @field:Element(name = "MAKER_NM", required = false)
    var MAKER_NM: String? = null, //업체명

    @field:Element(name = "UPDATE_YMD", required = false)
    var UPDATE_YMD: String? = null //데이터기준일자
) : Parcelable




