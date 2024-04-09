import Foundation
import SwiftUI

struct SettingsItemWithTitleView: View {
    let title: String
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading, spacing: 8) {
                Text(title)
                    .font(.body)
                    .padding(16)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .lineLimit(1)
                Divider()
            }
        }
        .buttonStyle(PlainButtonStyle())
    }
}


#Preview {
    SettingsItemWithTitleView(title: "Title") {}
}
