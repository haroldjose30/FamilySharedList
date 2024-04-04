import Foundation
import SwiftUI

struct QuickInsertListPage<ViewModel>: View where ViewModel: QuickInsertListViewModelProtocol {
    @ObservedObject var viewModel: ViewModel

    var body: some View {
        ZStack {
            VStack {
                Text(viewModel.textFieldTitle)
                    .frame(maxWidth: .infinity, alignment: .leading)

                TextEditorView(text: $viewModel.text, placeHolder: "Ex: 1 refrigerante \nnutela 5\n")


            }.padding()

            if viewModel.loading {
                ProgressView()
            }
        }
        .navigationTitle("Inclusão rápida")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                Button {
                    viewModel.goToFamilyListPage()
                } label: {
                    Image(systemName: "chevron.left")
                }
            }

            ToolbarItem(placement: .topBarTrailing) {
                Button {
                    Task { await viewModel.quickInsertItem() }
                } label: {
                    Image(systemName: "plus.circle")
                }
            }
        }
    }
}

#Preview {
    NavigationView {
        QuickInsertListPage(
            viewModel: QuickInsertListViewModelMocked()
        )
    }
}
