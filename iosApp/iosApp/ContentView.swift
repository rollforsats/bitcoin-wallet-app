import SwiftUI
import shared


struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea() // let the Compose background fill behind the status bar + home indicator; content stays inset via the Scaffold's safe-area insets
    }
}


struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        // Return MyViewController instance
        return MainViewControllerKt.MainViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Updates the state of the specified view controller with new information from SwiftUI.
    }
}
