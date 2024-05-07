import SwiftUI
import shared

struct ColumnRigthEditingName<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @Binding var item: FamilyListModel
    @Binding var nameTextFieldValue: String
    @Binding var nameInEditMode: Bool
    var viewModel: ViewModel
    @Binding var isBusy: Bool

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            RowItemTopOptions(
                nameTextFieldValue: $nameTextFieldValue
            )
            ItemRowBottomOptions(
                item: $item,
                nameTextFieldValue: $nameTextFieldValue,
                nameInEditMode: $nameInEditMode,
                viewModel: viewModel, 
                isBusy: $isBusy
            )
        }
        .padding(10)
    }
}

private struct RowItemTopOptions: View {
    @Binding var nameTextFieldValue: String
    
    var body: some View {
        VStack {
            Text("Alterando descrição")
                .font(.caption2)
                .frame(maxWidth: .infinity, alignment: .leading)
            TextEditor(text: $nameTextFieldValue)
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
    @Binding var nameTextFieldValue: String
    @Binding var nameInEditMode: Bool
    var viewModel: ViewModel
    @Binding var isBusy: Bool

    var body: some View {
        HStack {
            Spacer()

            Image(systemName: SystemName.checkmark.rawValue)
                .frame(width: 20, height: 20)
                .padding(8)
                .foregroundColor(.blue)
                .onTapGesture {
                    item.name = nameTextFieldValue
                    nameInEditMode = false
                    processUpdate { await viewModel.updateName(uuid: item.uuid, name: nameTextFieldValue)}
                }

            Image(systemName: SystemName.xmark.rawValue)
                .frame(width: 20, height: 20)
                .padding(8)
                .foregroundColor(.blue)
                .onTapGesture {
                    nameInEditMode = false
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
        nameTextFieldValue: .constant("description preview"),
        nameInEditMode: .constant(false),
        viewModel: FamilyListViewModelMocked(),
        isBusy: .constant(false)
    )
}
