import Foundation
import SwiftUI

struct SettingsItemWithTitleAndSubtitleView: View {
    let title: String
    let subtitle: String
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading, spacing: 8) {
                Text(title)
                    .font(.body)
                    .padding(.horizontal, 16)
                    .padding(.top, 8)
                    .padding(.bottom, 0)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .lineLimit(1)
                Text(subtitle)
                    .font(.subheadline)
                    .padding(.horizontal, 16)
                    .padding(.bottom, 8)
                    .padding(.top, 0)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .lineLimit(1)
                Divider()
            }
        }
        .buttonStyle(PlainButtonStyle())
    }
}

#Preview {
    SettingsItemWithTitleAndSubtitleView(
        title: "Title",
        subtitle: "Subtitle"
    ) {}
}

