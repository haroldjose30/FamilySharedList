import Foundation
import SwiftUI

struct TextEditorView: View {
    init(text: Binding<String>, placeHolder: String = "") {
        self._text = text
        self.placeHolder = placeHolder
        self.showPlaceholder = text.wrappedValue.isEmpty
    }

    @Binding var text: String
    var placeHolder: String = ""
    @State private var showPlaceholder = true

    var body: some View {
        ZStack {
            TextEditor(text: $text)
                .textFieldStyle(.roundedBorder)
                .keyboardType(.default)
                .disableAutocorrection(true)
                .multilineTextAlignment(.leading)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding()
                .background(Color.white)
                .cornerRadius(8)
                .shadow(radius: 4)
                .onChange(of: text) { newValue in
                    showPlaceholder = newValue.isEmpty
                }

            if showPlaceholder {
                VStack {
                    Text(placeHolder)
                        .foregroundColor(Color.gray)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding()
                    Spacer()
                }
            }
        }
    }
}
