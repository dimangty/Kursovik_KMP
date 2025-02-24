import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}


class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool  {
//        let mNetworkService: NewsService = inject()
//        do {
//            let model: shared.NewsListViewModel = inject()
//        } catch let error {
//            print(error.localizedDescription)
//        }
        return true
    }
}
