import SwiftUI
import shared
import Foundation

struct ColumnLeftDefault<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    @Binding var item: FamilyListModel
    @StateObject var viewModel: ViewModel

    var body: some View {
        if let imageFrontSmallUrlStr = item.product?.imageFrontSmallUrl,
           let imageFrontSmallUrl = URL(string: imageFrontSmallUrlStr) {

            VStack(alignment: .center, spacing: 8) {
                AsyncImage(url: imageFrontSmallUrl) { phase in
                    if let image = phase.image {
                        // Display the loaded image
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .padding(4)
                            .onTapGesture {
                                viewModel.openImage(item: item)
                            }
                    } else if phase.error != nil {
                        // Display a placeholder when loading failed
                        Image(systemName: SystemName.questionmarkDiamond.rawValue)
                            .imageScale(.large)
                    } else {
                        // Display a placeholder while loading
                        ProgressView()
                    }
                }
            }
            .frame(width: 100, height: 140)
            .background(Color.white)
            .clipShape(RoundedRectangle(cornerRadius: 16))
            .overlay(RoundedRectangle(cornerRadius: 16)
                .stroke(lineWidth: 1)
                .foregroundColor(Color.gray.opacity(0.1))
            )
            .padding(8)
        } else {
            EmptyView()
        }
    }
}

#Preview {
    let viewModel = FamilyListViewModelMocked()
    return ColumnLeftDefault(
        item: .constant(Samples.FamilyList.companion.nutella),
        viewModel: viewModel
    )
}
