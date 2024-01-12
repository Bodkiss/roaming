import SwiftUI
import shared

@main
struct AppMain: App {
    init() {
        KoinKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}