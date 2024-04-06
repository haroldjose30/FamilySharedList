import SwiftUI

struct FamilyListEmptyState: View {
    let action: () -> Void
    var body: some View {
        VStack {
            Image(systemName: SystemName.zzz.rawValue)
                .resizable(resizingMode: .tile)
                .frame(width: 48, height: 48)
                .foregroundColor(.gray)
            Spacer().frame(height: 48)
            Text("nenhum item nesta lista")
            Spacer().frame(height: 48)

            Button(
                action: action,
                label: {
                    VStack {
                        Image(systemName: SystemName.arrowTriangle2CirclepathIcloud.rawValue)
                            .padding(.bottom,2)
                        Text("Atualizar")
                    }
                }
            )
        }
    }
}

#Preview {
    FamilyListEmptyState(action: {})
}
