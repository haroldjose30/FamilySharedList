import Foundation
import SwiftUI

struct QuickInsertListPage<ViewModel>: View where ViewModel: QuickInsertListViewModelProtocol {
    @StateObject var viewModel: ViewModel

    var body: some View {
        VStack {
            switch viewModel.viewState {
            case .initial:
                QuickInsertListView()
            case .loading:
                ProgressView()
            case .success:
                Text("dados incluidos...")
            case let .error(message, retryAction):
                ErrorPage(message: message, retryAction: retryAction)
            }
        }
        .navigationTitle("Inclusão rápida")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                Button {
                    viewModel.goToFamilyListPage()
                } label: {
                    Image(systemName: SystemName.chevronLeft.rawValue)
                }
            }

            ToolbarItem(placement: .topBarTrailing) {
                Button {
                    Task { await viewModel.quickInsertItem() }
                } label: {
                    Image(systemName: SystemName.plusCircle.rawValue)
                }
            }
        }
    }

    func QuickInsertListView() -> some View {
        VStack {
            Text(viewModel.textFieldTitle)
                .frame(maxWidth: .infinity, alignment: .leading)

            TextEditorView(text: $viewModel.text, placeHolder: "Ex: 1 refrigerante \nnutela 5\n")


        }.padding()
    }
}

#Preview {
    NavigationView {
        QuickInsertListPage(
            viewModel: QuickInsertListViewModelMocked()
        )
    }
}
