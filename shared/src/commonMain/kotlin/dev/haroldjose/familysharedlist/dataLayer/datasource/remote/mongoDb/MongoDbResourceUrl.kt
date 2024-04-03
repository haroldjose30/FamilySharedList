package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb
enum class MongoDbResourceUrl(val value: String) {
    FIND("data/v1/action/find"),
    FIND_ONE("data/v1/action/findOne"),
    INSERT_ONE("data/v1/action/insertOne"),
    INSERT_MANY("data/v1/action/insertMany"),
    UPDATE_ONE("data/v1/action/updateOne"),
    DELETE_ONE("data/v1/action/deleteOne"),
    SET_SHARED_ACCOUNT_BY_CODE("custom/v1/account/SetSharedAccountByCode"),
    CREATE_SAMPLE_DATA_FOR_FIRST_ACCESS("custom/v1/account/CreateSampleDataForFirstAccess")
}