import SwiftUI
import shared

struct FamilyListRowItemWithProduct<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    var viewModel: ViewModel
    @State var item: FamilyListModel

    @State private var nameTextFieldValue = ""
    @State private var nameInEditMode = false
    @State private var priceInEditMode = false
    @State private var isBusy = false

    var body: some View {
        HStack {
            switch (nameInEditMode,priceInEditMode) {
            case (true,_):
                ColumnRigthEditingName(
                    item: $item,
                    nameInEditMode: $nameInEditMode,
                    viewModel: viewModel,
                    isBusy: $isBusy
                )
            case (_,true):
                ColumnRigthEditingPrice(
                    item: $item,
                    priceInEditMode: $priceInEditMode,
                    viewModel: viewModel,
                    isBusy: $isBusy
                )
            default:
                ColumnLeftDefault(
                    item: $item,
                    viewModel: viewModel
                )
                ColumnRigthDefault(
                    item: $item,
                    nameInEditMode: $nameInEditMode,
                    priceInEditMode: $priceInEditMode,
                    viewModel: viewModel,
                    isBusy: $isBusy
                )
            }
        }
    }
}

#Preview {
    FamilyListRowItemWithProduct(
        viewModel: FamilyListViewModelMocked(), 
        item: Samples.FamilyList.companion.nutella
    )
    .background(Color.gray)
}
