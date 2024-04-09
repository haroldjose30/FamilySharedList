import Foundation
import SwiftUI
import UIKit

///Modifier to clear Background color for SwiftUI.List for IOS14 and IOS16+
public extension View {
    func backgroundClear() -> some View {
        modifier(ListBackgroundClearModifier())
    }
}

private struct ListBackgroundClearModifier: ViewModifier {

    @ViewBuilder
    func body(content: Content) -> some View {
        if #available(iOS 16.0, *) {
            content
                .scrollContentBackground(.hidden)
        } else {
            content
                .onAppear {
                    UICollectionView.appearance().backgroundColor = .clear
                    UITableView.appearance().backgroundColor = .clear
                    UITableViewHeaderFooterView.appearance().tintColor = UIColor.clear
                }
        }
    }
}
