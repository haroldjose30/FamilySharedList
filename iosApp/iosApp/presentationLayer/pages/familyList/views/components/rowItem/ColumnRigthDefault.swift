import SwiftUI
import shared

struct ColumnRigthDefault<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @Binding var item: FamilyListModel
    @Binding var nameInEditMode: Bool
    @Binding var priceInEditMode: Bool
    var viewModel: ViewModel
    @Binding var isBusy: Bool

    var body: some View {
        VStack {
            RowItemTopOptions(
                item: $item,
                nameInEditMode: $nameInEditMode, 
                isBusy: $isBusy
            )
            RowItemMiddleOptions(
                item: $item, 
                priceInEditMode: $priceInEditMode
            )
            ItemRowBottomOptions(
                item: $item, 
                isBusy: $isBusy,
                viewModel: viewModel
            )
        }
    }
}

private struct RowItemTopOptions: View {
    @Binding var item: FamilyListModel
    @Binding var nameInEditMode: Bool
    @Binding var isBusy: Bool
    var body: some View {
        HStack {
            Text(item.name)
                .font(.body)
                .lineLimit(3)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(10)
                .onLongPressGesture {
                    nameInEditMode = true
                }
            if isBusy {
                ProgressView()
                    .frame(width: 20, height: 20)
            }
        }
    }
}

private struct RowItemMiddleOptions: View {
    @Binding var item: FamilyListModel
    @Binding var priceInEditMode: Bool
    var body: some View {
        HStack {
            Text(item.price.toCurrencyFormat())
                .font(.body)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(10)
                .onLongPressGesture {
                    priceInEditMode = true
                }
        }
    }
}

private struct ItemRowBottomOptions<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    @Binding var item: FamilyListModel
    @Binding var isBusy: Bool
    var viewModel: ViewModel
    var body: some View {
        HStack {
            QuantitySelectionView(value: item.quantity.toInt()) { newValue in
                item.quantity = newValue.toInt32()
                processUpdate { await viewModel.updateQuantity(uuid: item.uuid, quantity: newValue) }
            }

            Spacer()

            Button(action: {
                viewModel.selectedItemUuid = item.uuid
                viewModel.isShowingBarcodeBottomSheet = true
            }) {
                Image(systemName: SystemName.qrcodeViewfinder.rawValue)
                    .resizable()
                    .frame(width: 24, height: 24)
                    .padding(.horizontal,4)
            }

            Button(action: {
                item.isPrioritized = !item.isPrioritized
                processUpdate { await viewModel.updateIsPrioritized(uuid: item.uuid, isPrioritized: item.isPrioritized) }
            }) {
                Image(systemName: SystemName.cart.rawValue)
                    .resizable()
                    .frame(width: 24, height: 24)
                    .padding(.horizontal,4)
            }

            Button(action: {
                item.isCompleted = !item.isCompleted
                processUpdate { await viewModel.updateIsCompleted(uuid: item.uuid, isCompleted: item.isCompleted) }
            }) {
                Image(systemName: SystemName.checkmarkCircle.rawValue)
                    .resizable()
                    .frame(width: 24, height: 24)
                    .padding(.horizontal,4)
            }
        }
        .padding(.bottom,8)
    }

    func processUpdate(block: @escaping () async -> Void ) {
        Task {
            isBusy = true
            await block()
            isBusy = false
        }
    }
}

#Preview {
    ColumnRigthDefault(
        item: .constant(Samples.FamilyList.companion.nutella),
        nameInEditMode: .constant(false),
        priceInEditMode: .constant(false),
        viewModel: FamilyListViewModelMocked(), 
        isBusy: .constant(false)
    ).background(Color.gray)
}
