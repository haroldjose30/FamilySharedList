import Foundation
import SwiftUI

extension View {
    func hideRowSeparator() -> some View {
        modifier(ViewHideRowSeparatorModifier())
    }
}

private struct ViewHideRowSeparatorModifier: ViewModifier {

    @ViewBuilder
    func body(content: Content) -> some View {
        if #available(iOS 15.0, *) {
            content
                .listRowSeparator(.hidden)
        } else {
            content
                .modifier(ViewHideRowSeparatorModifierIOS14())
        }
    }
}

private struct ViewHideRowSeparatorModifierIOS14: ViewModifier {
    static let defaultListRowHeight: CGFloat = 44
    var insets: EdgeInsets
    var background: Color

    init(insets: EdgeInsets = .defaultListRowInsets, background: Color = .white) {
        self.insets = insets
        var alpha: CGFloat = 0
        UIColor(background).getWhite(nil, alpha: &alpha)
        assert(alpha == 1, "Setting background to a non-opaque color will result in separators remaining visible.")
        self.background = background
    }

    func body(content: Content) -> some View {
        content
            .padding(insets)
            .frame(
                minWidth: 0, maxWidth: .infinity,
                minHeight: Self.defaultListRowHeight,
                alignment: .leading
            )
            .listRowInsets(EdgeInsets())
            .background(background)
    }
}

extension EdgeInsets {
    static let defaultListRowInsets = Self(top: 0, leading: 16, bottom: 0, trailing: 16)
}

#Preview {
    List {
        ForEach(0..<10) { _ in
            Text("Text 4")
                .hideRowSeparator()
        }
    }
}
