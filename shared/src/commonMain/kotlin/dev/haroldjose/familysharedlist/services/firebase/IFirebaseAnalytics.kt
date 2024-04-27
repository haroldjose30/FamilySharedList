package dev.haroldjose.familysharedlist.services.firebase

public interface IFirebaseAnalytics {

    fun logEvent(name: String)

    fun logEvent(
        name: String,
        params: Map<String,Any>
    )

    fun setAnalyticsCollectionEnabled(enabled: Boolean)

    fun setUserId(id: String?)

    fun setSessionTimeoutDuration(milliseconds: Long)

    fun resetAnalyticsData()

    fun setUserProperty(name: String, value: String)

//    object Event {
//        var AD_IMPRESSION: String = ""
//        var ADD_PAYMENT_INFO: String = ""
//        var ADD_TO_CART: String = ""
//        var ADD_TO_WISHLIST: String = ""
//        var APP_OPEN: String = ""
//        var BEGIN_CHECKOUT: String = ""
//        var CAMPAIGN_DETAILS: String = ""
//        var ECOMMERCE_PURCHASE: String = ""
//        var GENERATE_LEAD: String = ""
//        var JOIN_GROUP: String = ""
//        var LEVEL_END: String = ""
//        var LEVEL_START: String = ""
//        var LEVEL_UP: String = ""
//        var LOGIN: String = ""
//        var POST_SCORE: String = ""
//        var PRESENT_OFFER: String = ""
//        var PURCHASE_REFUND: String = ""
//        var SEARCH: String = ""
//        var SELECT_CONTENT: String = ""
//        var SHARE: String = ""
//        var SIGN_UP: String = ""
//        var SPEND_VIRTUAL_CURRENCY: String = ""
//        var TUTORIAL_BEGIN: String = ""
//        var TUTORIAL_COMPLETE: String = ""
//        var UNLOCK_ACHIEVEMENT: String = ""
//        var VIEW_ITEM: String = ""
//        var VIEW_ITEM_LIST: String = ""
//        var VIEW_SEARCH_RESULTS: String = ""
//        var EARN_VIRTUAL_CURRENCY: String = ""
//        var SCREEN_VIEW: String = ""
//        var REMOVE_FROM_CART: String = ""
//        var CHECKOUT_PROGRESS: String = ""
//        var SET_CHECKOUT_OPTION: String = ""
//        var ADD_SHIPPING_INFO: String = ""
//        var PURCHASE: String = ""
//        var REFUND: String = ""
//        var SELECT_ITEM: String = ""
//        var SELECT_PROMOTION: String = ""
//        var VIEW_CART: String = ""
//        var VIEW_PROMOTION: String = ""
//    }
//
//    object Param {
//        var ACHIEVEMENT_ID: String = ""
//        var AD_FORMAT: String = ""
//        var AD_PLATFORM: String = ""
//        var AD_SOURCE: String = ""
//        var AD_UNIT_NAME: String = ""
//        var CHARACTER: String = ""
//        var TRAVEL_CLASS: String = ""
//        var CONTENT_TYPE: String = ""
//        var CURRENCY: String = ""
//        var COUPON: String = ""
//        var START_DATE: String = ""
//        var END_DATE: String = ""
//        var EXTEND_SESSION: String = ""
//        var FLIGHT_NUMBER: String = ""
//        var GROUP_ID: String = ""
//        var ITEM_CATEGORY: String = ""
//        var ITEM_ID: String = ""
//        var ITEM_LOCATION_ID: String = ""
//        var ITEM_NAME: String = ""
//        var LOCATION: String = ""
//        var LEVEL: String = ""
//        var LEVEL_NAME: String = ""
//        var SIGN_UP_METHOD: String = ""
//        var METHOD: String = ""
//        var NUMBER_OF_NIGHTS: String = ""
//        var NUMBER_OF_PASSENGERS: String = ""
//        var NUMBER_OF_ROOMS: String = ""
//        var DESTINATION: String = ""
//        var ORIGIN: String = ""
//        var PRICE: String = ""
//        var QUANTITY: String = ""
//        var SCORE: String = ""
//        var SHIPPING: String = ""
//        var TRANSACTION_ID: String = ""
//        var SEARCH_TERM: String = ""
//        var SUCCESS: String = ""
//        var TAX: String = ""
//        var VALUE: String = ""
//        var VIRTUAL_CURRENCY_NAME: String = ""
//        var CAMPAIGN: String = ""
//        var SOURCE: String = ""
//        var MEDIUM: String = ""
//        var TERM: String = ""
//        var CONTENT: String = ""
//        var ACLID: String = ""
//        var CP1: String = ""
//        var ITEM_BRAND: String = ""
//        var ITEM_VARIANT: String = ""
//        var ITEM_LIST: String = ""
//        var CHECKOUT_STEP: String = ""
//        var CHECKOUT_OPTION: String = ""
//        var CREATIVE_NAME: String = ""
//        var CREATIVE_SLOT: String = ""
//        var AFFILIATION: String = ""
//        var INDEX: String = ""
//        var DISCOUNT: String = ""
//        var ITEM_CATEGORY2: String = ""
//        var ITEM_CATEGORY3: String = ""
//        var ITEM_CATEGORY4: String = ""
//        var ITEM_CATEGORY5: String = ""
//        var ITEM_LIST_ID: String = ""
//        var ITEM_LIST_NAME: String = ""
//        var ITEMS: String = ""
//        var LOCATION_ID: String = ""
//        var PAYMENT_TYPE: String = ""
//        var PROMOTION_ID: String = ""
//        var PROMOTION_NAME: String = ""
//        var SCREEN_CLASS: String = ""
//        var SCREEN_NAME: String = ""
//        var SHIPPING_TIER: String = ""
//    }
}