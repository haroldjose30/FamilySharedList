import SwiftUI
import shared

struct FamilyListTabItemView<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    @ObservedObject var viewModel: ViewModel
    let tabIndex: FamilyListPageTabEnum

    @State private var isRefreshing = false

    var body: some View {
        List(viewModel.familyListModelsFiltered, id: \.uuid) { item in
            FamilyListRowItem(item: item, viewModel: viewModel)
        }
        .onAppear {
            Task {  await viewModel.loadData(tabIndex: tabIndex, fromNetwork: true) }
        }
    }
}


#Preview {
    FamilyListTabItemView(
        viewModel: FamilyListViewModelMocked(),
        tabIndex: FamilyListPageTabEnum.pending
    )
}
