import SwiftUI
import shared

struct FamilyListView<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    @StateObject var viewModel: ViewModel
    let items: [FamilyListModel]
    let refreshData: (_ showLoading: Bool) -> Void
    var body: some View {
        Group {
            if items.isEmpty {
                FamilyListEmptyState(action: {
                    refreshData(true)
                })
            } else {
                List(items) { item in
                    FamilyListRowItemView(
                        viewModel: viewModel,
                        item: item
                    )
                    .background(Color.white)
                    .cornerRadius(10)
                    .shadow(radius: 1)
                    .swipeActions(edge: .trailing) {
                        Button(role: .destructive) {
                            Task { await viewModel.remove(uuid: item.uuid) }
                        } label: {
                            Label("Excluir", systemImage: SystemName.trash.rawValue)

                        }.background(.red)
                    }
                    .hideRowSeparator()
                }
                .refreshable {
                    refreshData(false)
                }
                .backgroundClear()
            }
        }
    }
}

#Preview {
    FamilyListView(
        viewModel: FamilyListViewModelMocked(),
        items: [],
        refreshData: { _ in}
    )
}
