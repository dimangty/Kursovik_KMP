//
//  ProgressView.swift
//  iosApp
//
//  Created by Дмитрий Быков on 22.02.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import UIKit

class ProgressView: UIView {

    let color: UIColor
    let bgColor: UIColor

    var lineWidth: CGFloat {
        didSet {
            bgLayer.lineWidth = lineWidth
            shapeLayer.lineWidth = lineWidth
        }
    }

    private var rotationAnimationKey: String?

    private lazy var bgLayer: CAShapeLayer = {
        let layer = CAShapeLayer()
        layer.strokeColor = bgColor.cgColor
        layer.lineWidth = lineWidth
        layer.fillColor = UIColor.clear.cgColor
        self.layer.insertSublayer(layer, at: 0)
        return layer
    }()

    private lazy var shapeLayer: CAShapeLayer = {
        let layer = CAShapeLayer()
        layer.strokeColor = color.cgColor
        layer.lineWidth = lineWidth
        layer.fillColor = UIColor.clear.cgColor
        layer.lineCap = .round
        self.layer.addSublayer(layer)
        return layer
    }()

    init(frame: CGRect, color: UIColor, bgColor: UIColor, lineWidth: CGFloat) {
        self.color = color
        self.bgColor = bgColor
        self.lineWidth = lineWidth
        super.init(frame: frame)
        backgroundColor = .clear
        addObservers()
    }

    convenience init(color: UIColor = .white,
                     bgColor: UIColor = .white.withAlphaComponent(0.2),
                     lineWidth: CGFloat = 6) {
        self.init(frame: .zero, color: color, bgColor: bgColor, lineWidth: lineWidth)
    }

    required init?(coder: NSCoder) {
        color = .white
        bgColor = .white.withAlphaComponent(0.2)
        lineWidth = 6
        super.init(coder: coder)
        backgroundColor = .clear
        addObservers()
    }

    override func didMoveToWindow() {
        super.didMoveToWindow()
        restartAnimation()
    }

    override func layoutSubviews() {
        super.layoutSubviews()
        layer.cornerRadius = frame.width / 2
        let path = UIBezierPath(ovalIn: CGRect(x: 0, y: 0, width: bounds.width, height: bounds.width))
        bgLayer.path = path.cgPath
        shapeLayer.path = path.cgPath
    }

    private func addObservers() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(restartAnimation),
            name: UIApplication.willEnterForegroundNotification,
            object: nil
        )
    }
    
    func stop() {
        guard window != nil else { return }
        shapeLayer.removeAllAnimations()
    }
    
    func start() {
        guard window != nil else { return }
        animateStroke()
        animateRotation()
    }

    @objc
    private func restartAnimation() {
        guard window != nil else { return }
        animateStroke()
        animateRotation()
    }

    private func animateStroke() {
        shapeLayer.removeAllAnimations()

        let startAnimation = CABasicAnimation(keyPath: "strokeStart")
        startAnimation.beginTime = 0.25
        startAnimation.fromValue = 0
        startAnimation.toValue = 1
        startAnimation.duration = 0.75
        startAnimation.timingFunction = .init(name: .easeInEaseOut)

        let endAnimation = CABasicAnimation(keyPath: "strokeEnd")
        endAnimation.beginTime = 0
        endAnimation.fromValue = 0
        endAnimation.toValue = 1
        endAnimation.duration = 0.75

        let strokeAnimationGroup = CAAnimationGroup()
        strokeAnimationGroup.duration = 1
        strokeAnimationGroup.repeatDuration = .infinity
        strokeAnimationGroup.animations = [startAnimation, endAnimation]
        shapeLayer.add(strokeAnimationGroup, forKey: nil)
    }

    private func animateRotation() {
        let key = UUID().uuidString
        rotationAnimationKey = key
        layer.removeAllAnimations()
        UIView.animate(withDuration: 0) {
            self.transform = .identity
        } completion: { _ in
            UIView.animate(withDuration: 1, delay: 0, options: .curveLinear) {
                self.transform = CGAffineTransform(rotationAngle: .pi)
            } completion: { [weak self] _ in
                guard self?.window != nil, key == self?.rotationAnimationKey else { return }
                UIView.animate(withDuration: 1, delay: 0, options: .curveLinear) {
                    self?.transform = CGAffineTransform(rotationAngle: .pi * 2)
                } completion: { [weak self] _ in
                    guard self?.window != nil, key == self?.rotationAnimationKey else { return }
                    self?.animateRotation()
                }
            }
        }
    }

}
