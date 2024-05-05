import Foundation
import SwiftUI
import shared
import KMPNativeCoroutinesAsync

class QuickInsertListViewModel: QuickInsertListViewModelProtocol {

    @Published var viewState: QuickInsertListViewState = .initial
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
    private let crashlytics: IFirebaseCrashlytics

    init(
        createFamilyListUseCase: CreateFamilyListUseCase,
        crashlytics: IFirebaseCrashlytics
    ) {
        self.createFamilyListUseCase = createFamilyListUseCase
        self.crashlytics = crashlytics
    }

    @MainActor
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
        viewState = .loading

        do {
            _ = try await asyncFunction(for: createFamilyListUseCase.execute(items: listOfItem))
            viewState = .success
            goToFamilyListPage()
        } catch {
            showError(e: error)
            return
        }
    }

    func showError(e: Error) {
        crashlytics.record(error: e)
        viewState = .error(message: e.localizedDescription, retryAction: {
            self.viewState = .initial
        })
    }
}
