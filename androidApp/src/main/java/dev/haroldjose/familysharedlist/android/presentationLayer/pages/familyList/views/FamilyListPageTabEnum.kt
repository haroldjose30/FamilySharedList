package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views

enum class FamilyListPageTabEnum(val value: Int) {
    PRIORIZED(0) {
        override fun isPrioritized() = true
        override fun isPending() = false
        override fun isCompleted() = false
     },
    PENDING(1){
        override fun isPrioritized() = false
        override fun isPending() = true
        override fun isCompleted() = false
    },
    COMPLETED(2){
        override fun isPrioritized() = false
        override fun isPending() = false
        override fun isCompleted() = true
    };

    abstract fun isPrioritized(): Boolean
    abstract fun isPending(): Boolean
    abstract fun isCompleted(): Boolean

    companion object {
        fun getBy(value: Int): FamilyListPageTabEnum = entries.firstOrNull { it.value == value } ?: PENDING
    }
}