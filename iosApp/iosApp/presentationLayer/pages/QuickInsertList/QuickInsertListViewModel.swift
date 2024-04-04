import Foundation
import SwiftUI
import shared

class QuickInsertListViewModel: QuickInsertListViewModelProtocol {
    @Published var loading = false
    @Published var text = "" {
        didSet {
            if text.isEmpty {
                textFieldTitle = "Informe os itens sendo cada linha um novo item."
            } else {
                let count = text.components(separatedBy: "\n").count
                let itemText = count == 1 ? "item" : "itens"
                textFieldTitle = "\(count) \(itemText)"
            }
        }
    }
    @Published var textFieldTitle = "Informe os itens sendo cada linha um novo item."
    var goToFamilyListPage: () -> Void = {}

    private let createFamilyListUseCase: CreateFamilyListUseCase

    init(createFamilyListUseCase: CreateFamilyListUseCase) {
        self.createFamilyListUseCase = createFamilyListUseCase
    }

    //@MainActor
    func quickInsertItem() async {
        guard !text.isEmpty else { return }

        let listOfItem: [FamilyListModel] = text.components(separatedBy: .newlines).compactMap({ item in
            let quantityStr = item.filter { $0.isNumber }
            let name = item.filter { !$0.isNumber }

            if name.isEmpty {
                return nil
            }

            return FamilyListModel(
                name: name.isEmpty ? item : name,
                isPrioritized: true,
                quantity: Int32(quantityStr) ?? 1
            )
        })

        text = ""
        loading = true
        //TODO: handle error
        try? await createFamilyListUseCase.execute(items: listOfItem)
        loading = false
        goToFamilyListPage()
    }
}
