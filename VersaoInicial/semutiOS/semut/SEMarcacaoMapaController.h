//
//  SEMarcacaoMapaController.h
//  semut
//
//  Created by Julio Rocha on 26/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "SEOcorrencia.h"
@interface SEMarcacaoMapaController : UIViewController <MKMapViewDelegate>
@property (weak, nonatomic) IBOutlet MKMapView *mapa;
@property(nonatomic,retain) SEOcorrencia *ocorrencia;

@end
