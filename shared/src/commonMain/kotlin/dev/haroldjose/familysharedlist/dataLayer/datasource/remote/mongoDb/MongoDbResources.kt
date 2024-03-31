package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb

enum class MongoDbResources {;

    enum class DataSource(val value: String) {
        FAMILY_SHARED_LIST_BACKEND0("FAMILYSHAREDLISTBACKEND0")
    }

    enum class Database(val value: String) {
        ACCOUNT("Db_Account_Main"),
        DEMO("Db_Demo")
    }

    enum class Collection(val value: String) {
        ACCOUNT("Col_Account"),
        MY_LIST("Col_MyLists")
    }
}