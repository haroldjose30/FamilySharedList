import shared

extension FamilyListModel: Identifiable {
    
    public var id: String {
        self.uuid
    }
    
    convenience init(
        name: String
    ) {
        
        self.init(
            uuid: "",
            name: name,
            isCompleted: false, 
            isPrioritized: false,
            quantity: 1
        )
    }
}
