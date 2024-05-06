import Foundation
import SwiftUI
import shared

struct ColumnRigthEditingPrice<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @Binding var item: FamilyListModel
    @State private var priceTextFieldValue: String = ""
    @Binding var priceInEditMode: Bool
    var viewModel: ViewModel
    @Binding var isBusy: Bool

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            RowItemTopOptions(
                priceTextFieldValue: $priceTextFieldValue
            )
            ItemRowBottomOptions(
                item: $item,
                priceTextFieldValue: $priceTextFieldValue,
                priceInEditMode: $priceInEditMode,
                viewModel: viewModel,
                isBusy: $isBusy
            )
        }
        .padding(10)
    }
}

private struct RowItemTopOptions: View {
    @Binding var priceTextFieldValue: String

    var body: some View {
        VStack {
            Text("Alterando preço")
                .font(.caption2)
                .frame(maxWidth: .infinity, alignment: .leading)
            //TODO: create currency input
            TextEditor(text: $priceTextFieldValue)
                .textFieldStyle(.roundedBorder)
                .keyboardType(.default)
                .disableAutocorrection(true)
                .multilineTextAlignment(.leading)
                .frame(maxWidth: .infinity, minHeight: 20, maxHeight: 80)
                .lineLimit(3)
                .border(.gray.opacity(0.2), width: 0.5)
        }
    }
}

private struct ItemRowBottomOptions<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @Binding var item: FamilyListModel
    @Binding var priceTextFieldValue: String
    @Binding var priceInEditMode: Bool
    var viewModel: ViewModel
    @Binding var isBusy: Bool

    var body: some View {
        HStack {
            Spacer()
            Button(action: {
                item.price = Double(priceTextFieldValue) ?? 0
                priceInEditMode = false
                processUpdate { await viewModel.updatePrice(uuid: item.uuid, price: item.price)}
            }) {
                Image(systemName: SystemName.checkmark.rawValue)
                    .frame(width: 20, height: 20)
                    .foregroundColor(.blue)
                    .padding(8)
            }

            Button(action: {
                priceInEditMode = false
            }) {
                Image(systemName: SystemName.xmark.rawValue)
                    .frame(width: 20, height: 20)
                    .foregroundColor(.blue)
                    .padding(8)
            }
        }
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
    ColumnRigthEditingName(
        item: .constant(Samples.FamilyList.companion.nutella),
        nameInEditMode: .constant(false),
        viewModel: FamilyListViewModelMocked(),
        isBusy: .constant(false)
    )
}


