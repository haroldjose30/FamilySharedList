import Foundation
import SwiftUI

struct SettingsItemWithInputTextView: View {
    let title: String
    let subtitle: String
    var placeholder: String = "ex: XY75K"
    var textLimit: Int = 5
    let onClick: (String) -> Void

    @State private var textFieldText = ""

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
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
            TextField(placeholder, text: $textFieldText)
                .padding(.horizontal, 16)
                .padding(.bottom, 8)
                .padding(.top, 0)
                .frame(maxWidth: .infinity, alignment: .leading)
                .onChange(of: textFieldText) { newValue in
                    if newValue.count > textLimit {
                        textFieldText = String(newValue.prefix(textLimit))
                    }
                }
            Divider()
        }
        .padding(.vertical, 16)
        .background(Color.clear)
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.leading, 16)
        .padding(.trailing, 8)
        .overlay(
            Button(action: {
                onClick(textFieldText)
            }) {
                Image(systemName: SystemName.chevronRight.rawValue)
            }
            .padding(.trailing, 16)
            .foregroundColor(.black)
            .padding(.top, 16)
            .padding(.bottom, 16)
            .frame(alignment: .trailing),
            alignment: .trailing
        )
    }
}

#Preview {
    SettingsItemWithInputTextView(
        title: "Title",
        subtitle: "Subtitle"
    ) { _ in }
}
