import shared

extension FamilyListModel: Identifiable {
    
    public var id: String {
        self.uuid
    }
    
    convenience init(
        name: String,
        isCompleted: Bool = false,
        isPrioritized: Bool = false,
        quantity: Int32 = 1,
        price: Double = 0,
        product: ProductModel? = nil
    ) {
        self.init(
            uuid: "",
            name: name,
            isCompleted: isCompleted,
            isPrioritized: isPrioritized,
            quantity: quantity,
            price: price,
            product: product
        )
    }
}
