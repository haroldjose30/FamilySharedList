import Foundation
import SwiftUI
import shared

struct FamilyListBottomSheetOpenImage<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    var viewModel: ViewModel

    var body: some View {
        AsyncImage(url: getImageUrl()) { phase in
            if let image = phase.image {
                // Display the loaded image
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(4)
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

    private func getImageUrl() -> URL {
        if let imageUrlStr = viewModel.openImageSelectedItem?.product?.imageUrl,
           let imageUrl = URL(string: imageUrlStr) {
            return imageUrl
        }

        if let imageUrlStr = viewModel.openImageSelectedItem?.product?.imageFrontUrl,
           let imageUrl = URL(string: imageUrlStr) {
            return imageUrl
        }

        if let imageUrlStr = viewModel.openImageSelectedItem?.product?.imageFrontSmallUrl,
           let imageUrl = URL(string: imageUrlStr) {
            return imageUrl
        }

        return URL(string: "https://gamestation.com.br/wp-content/themes/game-station/images/image-not-found.png")!
    }
}

#Preview {
    let viewModel = FamilyListViewModelMocked()
    viewModel.openImageSelectedItem = Samples.FamilyList.companion.nutella
    return FamilyListBottomSheetOpenImage(
        viewModel: viewModel
    )
}
