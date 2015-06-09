//
//  SEViewController.m
//  semut
//
//  Created by Julio Rocha on 22/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEViewController.h"
#import "SEAppDelegate.h"
#import "SEUtil.h"
@interface SEViewController ()

@end

@implementation SEViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    SEAppDelegate *delegate = (SEAppDelegate *) [[UIApplication sharedApplication]delegate];
   
    delegate.homeController = self;
    
}

-(void) viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    
    [self.navigationController setNavigationBarHidden:YES animated:YES];
    
    [SEUtil setBool:NO forKey:kBoolSucom];
    
    if([SEUtil boolForKey:kBoolPushReceived]) {
        
        [SEUtil setBool:NO forKey:kBoolPushReceived];
        
        [self performSegueWithIdentifier:@"sg_alerta" sender:nil];
        
    }
}

-(void) viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}


-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    SEAppDelegate *delegate =  (SEAppDelegate *)[[UIApplication sharedApplication]delegate];
    
    BOOL ios7 = delegate.ios7;
    
    
    if([@"sg_transalvador" isEqualToString:segue.identifier] || [@"sg_alerta" isEqualToString:segue.identifier]) {
        
        if(ios7) {
        
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_transalvador_320x64.png"] forBarMetrics:UIBarMetricsDefault];
            
        } else {
            
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_transalvador_320x44.png"] forBarMetrics:UIBarMetricsDefault];
            
        }
        
        
    } else if([@"sg_sucom" isEqualToString:segue.identifier]) {
        
        if(ios7) {
            
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_topo_sucom_320x64.png"] forBarMetrics:UIBarMetricsDefault];
            
        } else {
            
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_topo_sucom_320x44.png"] forBarMetrics:UIBarMetricsDefault];
            
        }
        
        [SEUtil setBool:YES forKey:kBoolSucom];
        
    } else if([@"sg_configuracoes" isEqualToString:segue.identifier]) {
        
        if(ios7) {
            
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_tel_configuracoes_320x64.png"] forBarMetrics:UIBarMetricsDefault];
            
        } else {
            
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_tel_configuracoes_320x44.png"] forBarMetrics:UIBarMetricsDefault];
            
        }
        
    } else if([@"sg_tel_uteis" isEqualToString:segue.identifier]) {
       
        if(ios7) {
            
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_tel_uteis_320x64.png"] forBarMetrics:UIBarMetricsDefault];
            
        } else {
            
            [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"bar_tel_uteis_320x44.png"] forBarMetrics:UIBarMetricsDefault];
            
        }
        
    }
    
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];

}

@end
