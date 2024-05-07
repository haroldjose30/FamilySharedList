import SwiftUI
import shared

struct ColumnRigthDefault<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @Binding var item: FamilyListModel
    @Binding var nameTextFieldValue: String
    @Binding var nameInEditMode: Bool
    @Binding var priceTextFieldValue: String
    @Binding var priceInEditMode: Bool
    var viewModel: ViewModel
    @Binding var isBusy: Bool

    var body: some View {
        VStack {
            RowItemTopOptions(
                item: $item, 
                nameTextFieldValue: $nameTextFieldValue,
                nameInEditMode: $nameInEditMode,
                isBusy: $isBusy
            )
            RowItemMiddleOptions(
                item: $item, 
                priceTextFieldValue: $priceTextFieldValue,
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
    @Binding var nameTextFieldValue: String
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
                    nameTextFieldValue = item.name
                    nameInEditMode = true
                }
            if isBusy {
                ProgressView()
                    .frame(width: 20, height: 20)
                    .padding(8)
            }
        }
    }
}

private struct RowItemMiddleOptions: View {
    @Binding var item: FamilyListModel
    @Binding var priceTextFieldValue: String
    @Binding var priceInEditMode: Bool
    var body: some View {
        HStack {
            Text(item.price.toCurrencyFormat())
                .font(.body)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(10)
                .onLongPressGesture {
                    priceTextFieldValue = "\(item.price)"
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

            Image(systemName: SystemName.qrcodeViewfinder.rawValue)
                .resizable()
                .frame(width: 24, height: 24)
                .padding(.horizontal,4)
                .foregroundColor(.blue)
                .onTapGesture {
                    viewModel.selectedItemUuid = item.uuid
                    viewModel.isShowingBarcodeBottomSheet = true
                }

            if !item.isCompleted {
                Image(systemName: item.isPrioritized ? SystemName.cartFill.rawValue : SystemName.cart.rawValue)
                    .resizable()
                    .frame(width: 24, height: 24)
                    .padding(.horizontal,4)
                    .foregroundColor(.blue)
                    .onTapGesture {
                        item.isPrioritized = !item.isPrioritized
                        processUpdate { await viewModel.updateIsPrioritized(uuid: item.uuid, isPrioritized: item.isPrioritized) }
                    }
            }

            Image(systemName: item.isCompleted ? SystemName.listBullet.rawValue : SystemName.checkmarkCircle.rawValue)
                .resizable()
                .frame(width: 24, height: 24)
                .padding(.horizontal,4)
                .foregroundColor(.blue)
                .onTapGesture {
                    item.isCompleted = !item.isCompleted
                    processUpdate { await viewModel.updateIsCompleted(uuid: item.uuid, isCompleted: item.isCompleted) }
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
        nameTextFieldValue: .constant(""),
        nameInEditMode: .constant(false),
        priceTextFieldValue: .constant(""),
        priceInEditMode: .constant(false),
        viewModel: FamilyListViewModelMocked(), 
        isBusy: .constant(false)
    )
}
