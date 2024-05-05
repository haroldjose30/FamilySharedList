import Foundation
import SwiftUI

struct ErrorPage: View {
    let message: String
    let retryAction: (() -> Void)?

    var body: some View {
        VStack {
            Spacer()
            Image(systemName: "exclamationmark.triangle")
                .resizable()
                .frame(width: 48, height: 48)
                .foregroundColor(.gray)
            Text("Ooops!")
                .font(.title)
                .fontWeight(.bold)
                .foregroundColor(.gray)
                .padding(.bottom,24)

            Text(message)
                .font(.headline)
                .fontWeight(.bold)
                .foregroundColor(.gray)
                .padding(.bottom,24)

            if let retryAction = retryAction {
                Button(action: retryAction) {
                    Text("Tentar Novamente")
                }
            }
            Spacer()
        }
        .padding()
    }
}

#Preview {
    ErrorPage(
        message: "Error Message - Preview",
        retryAction: {}
    )
}
