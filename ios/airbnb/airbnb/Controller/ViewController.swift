//
//  ViewController.swift
//  airbnb
//
//  Created by 양준혁 on 2021/05/17.
//

import UIKit
import Combine

final class ViewController: UIViewController {
    
    @IBOutlet weak var searchView: UIView!
    @IBOutlet weak var curationImage: UIImageView!
    @IBOutlet weak var spotTitle: UILabel!
    @IBOutlet weak var curationTitle: UILabel!
    @IBOutlet weak var subtitle: UILabel!
    
    private var locationManager: LocationManager!
    private var cancelable = Set<AnyCancellable>()
    @IBOutlet weak var cityCollectionView: UICollectionView!
    @IBOutlet weak var spotCollectionView: UICollectionView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUILabelText()
        let networkManager = NetworkManager()
        locationManager = LocationManager(getDataManager: networkManager)
        locationManager.fetchCitiesLocation()
        addTapGesture()
        bind()
        registerNib()
        cityCollectionView.dataSource = self
        spotCollectionView.dataSource = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.isHidden = true
    }
    
    private func registerNib() {
        let cityNib = UINib(nibName: CityCollectionViewCell.nibName, bundle: nil)
        let spotNib = UINib(nibName: SpotCollectionViewCell.nibName, bundle: nil)
        cityCollectionView.register(cityNib, forCellWithReuseIdentifier: CityCollectionViewCell.identifier)
        spotCollectionView.register(spotNib, forCellWithReuseIdentifier: SpotCollectionViewCell.identifier)
    }
    
    private func setUILabelText() {
        spotTitle.text = "어디에서나, 여행은\n살아보는거야"
        curationTitle.text = "슬기로운\n자연생활"
        subtitle.text = "에어비앤비가 엄선한\n위시리스트를 만나보세요"
    }
    
    private func addTapGesture() {
        let gesture = UITapGestureRecognizer(target: self, action: #selector(tapped))
        searchView.addGestureRecognizer(gesture)
    }
    
    @objc private func tapped() {
        performSegue(withIdentifier: "local", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let locationController = segue.destination as? LocationViewController else { return }
        locationController.getLocationManager(manager: self.locationManager)
    }
    
    private func bind() {
        locationManager.$mainLayout
            .receive(on: DispatchQueue.main)
            .sink { [weak self] mainLayout in
                guard let mainLayout = mainLayout else { return }
                self?.curationImage.load(with: mainLayout.mainImage)
                self?.cityCollectionView.reloadData()
                self?.spotCollectionView.reloadData()
            }
            .store(in: &self.cancelable)
    }
}

extension ViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if collectionView == self.cityCollectionView {
            return locationManager.mainLayout?.cities.count ?? 0
        } else {
            return locationManager.mainLayout?.extraImages.count ?? 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if collectionView == self.cityCollectionView {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CityCollectionViewCell.identifier, for: indexPath) as! CityCollectionViewCell
            guard let url = locationManager.mainLayout?.cities[indexPath.row].cityImage else { return UICollectionViewCell() }
            cell.locationImage.load(with: url)
            cell.location.text = locationManager.mainLayout?.cities[indexPath.row].cityName
            return cell
        } else {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: SpotCollectionViewCell.identifier, for: indexPath) as! SpotCollectionViewCell
            guard let url = locationManager.mainLayout?.extraImages[indexPath.row] else
            { return UICollectionViewCell() }
            cell.spotImage.load(with: url)
            return cell
        }
    }
}