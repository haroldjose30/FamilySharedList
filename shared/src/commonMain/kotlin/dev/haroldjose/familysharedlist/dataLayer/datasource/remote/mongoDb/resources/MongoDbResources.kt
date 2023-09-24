package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.resources

enum class MongoDbResources {;

    enum class DataSource(val value: String) {
        FAMILYSHAREDLISTBACKEND0("FAMILYSHAREDLISTBACKEND0")
    }

    enum class Database(val value: String) {
        ACCOUNT("Db_Account_Main"),
        FAMILYSHAREDLIST("FamilySharedListDB_PROD"),
        DEMO("Db_Account_Demo_Shared")
    }

    enum class Collection(val value: String) {
        ACCOUNT("Col_Account"),
        SHOPPINGLIST("shoppingList"),
        MY_LIST("Col_MyLists")
    }
}