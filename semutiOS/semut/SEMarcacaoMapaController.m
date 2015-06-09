//
//  SEMarcacaoMapaController.m
//  semut
//
//  Created by Julio Rocha on 26/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEMarcacaoMapaController.h"
#import "SEAppDelegate.h"
#import "SEConclusaoController.h"
#define  kLatitudeSalvador -12.952368
#define  kLongitudeSalvador -38.516114
#import "SEAnnotation.h"
#import "MBProgressHUD.h"
@interface SEMarcacaoMapaController () {
    MBProgressHUD *progress;
}

@end

@implementation SEMarcacaoMapaController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    MKCoordinateRegion region;
    
    _mapa.delegate=self;
    
    _mapa.mapType = MKMapTypeSatellite;
    
    region.span.longitudeDelta = 0.069;
    
    region.span.latitudeDelta = 0.069;
    
    SEAppDelegate *delegate = (SEAppDelegate *)[[UIApplication sharedApplication] delegate];
    
    if(delegate.latitude==0) {
        
        region.center.latitude=kLatitudeSalvador;
        
        region.center.longitude=kLongitudeSalvador;

    } else {
    
        region.center.latitude=delegate.latitude;
  
        region.center.longitude=delegate.longitude;
        
    }
    
    _mapa.showsUserLocation=YES;
    
    [_mapa setRegion:region animated:NO];
    
    UILongPressGestureRecognizer *pressGesture = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(handlePressGesture:)];

    [_mapa addGestureRecognizer:pressGesture];
    
    UIBarButtonItem *button = [[UIBarButtonItem alloc]initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(continuar)];
    
    self.navigationItem.rightBarButtonItem = button;
    
    progress = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    
    progress.mode = MBProgressHUDModeText;
    
    progress.labelText=@"Marque com um longo clique";
    
    [progress show:YES];
    
    [progress hide:YES afterDelay:2];
	
}

-(void) continuar {
    [self performSegueWithIdentifier:@"sg_conclusao" sender:nil];
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    SEConclusaoController *controller = segue.destinationViewController;
    
    controller.ocorrencia = _ocorrencia;
    
}

-(void)handlePressGesture:(UIGestureRecognizer*)sender {

    {
        CGPoint point = [sender locationInView:_mapa];

        CLLocationCoordinate2D locCoord = [_mapa convertPoint:point toCoordinateFromView:_mapa];
        
        [_mapa removeAnnotations:_mapa.annotations];
        
        SEAnnotation *dropPin = [[SEAnnotation alloc] initWithCoordinate:CLLocationCoordinate2DMake(locCoord.latitude, locCoord.longitude)];

        [_mapa addAnnotation:dropPin];
        
        _ocorrencia.latitude = locCoord.latitude;
        _ocorrencia.longitude = locCoord.longitude;
    }
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];

}

@end
