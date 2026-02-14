//
//  ProfileTabView.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI
import UIKit
import shared

struct ProfileTabView: View {

    @StateObject private var viewModel = ProfileScreenViewModel()
    @State private var showImagePicker = false
    @State private var showActionSheet = false
    @State private var pickerSourceType: UIImagePickerController.SourceType = .photoLibrary
    @State private var localPhotoPath: String = ""

    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                Text("Profile")
                    .font(.title2)
                    .bold()

                Group {
                    let currentPhotoPath = localPhotoPath.isEmpty ? viewModel.state.photoPath : localPhotoPath
                    if let image = loadProfileImage(path: currentPhotoPath) {
                        Image(uiImage: image)
                            .resizable()
                            .scaledToFill()
                    } else {
                        Image(systemName: "person.crop.circle.fill")
                            .resizable()
                            .scaledToFit()
                            .foregroundStyle(.gray)
                            .padding(8)
                    }
                }
                .frame(width: 120, height: 120)
                .clipShape(Circle())
                .onTapGesture {
                    viewModel.onEvent(event: .AvatarTapped())
                }

                if viewModel.state.isMockData {
                    Text("Showing mock profile (database is empty)")
                        .font(.caption)
                        .foregroundStyle(.blue)
                }

                VStack(alignment: .leading, spacing: 8) {
                    profileRow(title: "Name", value: viewModel.state.fullName)
                    profileRow(title: "Gender", value: viewModel.state.gender)
                    profileRow(title: "Birth date", value: viewModel.state.birthDate)
                    profileRow(title: "Location", value: viewModel.state.location)
                    profileRow(title: "Email", value: viewModel.state.email)
                    profileRow(title: "Phone", value: viewModel.state.phone)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(16)
                .background(Color(.secondarySystemBackground))
                .clipShape(RoundedRectangle(cornerRadius: 12))

                Button("Logout") {
                    viewModel.onEvent(event: .LogoutTapped())
                }
                .buttonStyle(.borderedProminent)
                .frame(maxWidth: .infinity)
            }
            .padding(16)
        }
        .confirmationDialog("Select Image Source", isPresented: $showActionSheet) {
            Button("Camera") {
                guard UIImagePickerController.isSourceTypeAvailable(.camera) else { return }
                pickerSourceType = .camera
                showImagePicker = true
            }
            Button("Gallery") {
                pickerSourceType = .photoLibrary
                showImagePicker = true
            }
            Button("Cancel", role: .cancel) {}
        }
        .sheet(isPresented: $showImagePicker) {
            PhotoPicker(sourceType: pickerSourceType) { image in
                guard let path = persistImage(image) else { return }
                localPhotoPath = path
                viewModel.onEvent(event: .PhotoChanged(photoPath: path))
            }
        }
        .onChange(of: viewModel.state.photoPath) { updatedPath in
            guard !updatedPath.isEmpty else { return }
            localPhotoPath = updatedPath
        }
        .onAppear {
            if !viewModel.state.photoPath.isEmpty {
                localPhotoPath = viewModel.state.photoPath
            }
            viewModel.sendViewAppearedEvent()
            viewModel.observeEffects { effect in
                if effect is ProfileEffect.ShowImageSourceDialog {
                    showActionSheet = true
                }
            }
        }
    }

    private func profileRow(title: String, value: String) -> some View {
        VStack(alignment: .leading, spacing: 2) {
            Text(title)
                .font(.caption)
                .foregroundStyle(.blue)
            Text(value)
                .font(.body)
        }
    }

    private func persistImage(_ image: UIImage) -> String? {
        guard let data = image.jpegData(compressionQuality: 0.9) else { return nil }
        guard let documentsURL = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first else {
            return nil
        }
        let url = documentsURL.appendingPathComponent("profile_photo_\(UUID().uuidString).jpg")
        do {
            try data.write(to: url, options: .atomic)
            return url.path
        } catch {
            return nil
        }
    }

    private func loadProfileImage(path: String) -> UIImage? {
        guard !path.isEmpty else { return nil }
        return UIImage(contentsOfFile: path)
    }
}

private final class ProfileScreenViewModel: BaseViewModel<shared.ProfileViewModel, ProfileState> {

    required override init() {
        super.init()
    }

    func observeEffects(callback: @escaping (ProfileEffect) -> Void) {
        guard let viewModel = mViewModel else { return }
        viewModel.effectFlow.watch { effect in
            guard let effect = effect else { return }
            callback(effect)
        }
    }

    func onEvent(event: ProfileEvents) {
        mViewModel?.pushEvent(event: event)
    }
}

private struct PhotoPicker: UIViewControllerRepresentable {

    let sourceType: UIImagePickerController.SourceType
    let onImagePicked: (UIImage) -> Void

    func makeCoordinator() -> Coordinator {
        Coordinator(onImagePicked: onImagePicked)
    }

    func makeUIViewController(context: Context) -> UIImagePickerController {
        let picker = UIImagePickerController()
        picker.sourceType = sourceType
        picker.delegate = context.coordinator
        picker.allowsEditing = false
        return picker
    }

    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {
        // no-op
    }

    final class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
        private let onImagePicked: (UIImage) -> Void

        init(onImagePicked: @escaping (UIImage) -> Void) {
            self.onImagePicked = onImagePicked
        }

        func imagePickerController(
            _ picker: UIImagePickerController,
            didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey: Any]
        ) {
            if let image = info[.originalImage] as? UIImage {
                onImagePicked(image)
            }
            picker.dismiss(animated: true)
        }

        func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
            picker.dismiss(animated: true)
        }
    }
}
