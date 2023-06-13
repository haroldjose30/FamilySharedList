package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.resources

enum class MongoDbResources {;

    enum class DataSource(val value: String) {
        CLUSTER0("Cluster0");
    }

    enum class Database(val value: String) {
        ACCOUNT("Db_Account_Main"),
        FAMILYSHAREDLIST("FamilySharedListDB_PROD")
    }

    enum class Collection(val value: String) {
        ACCOUNT("Col_Account"),
        SHOPPINGLIST("shoppingList")
    }
}