//
//  SEAppDelegate.h
//  semut
//
//  Created by Julio Rocha on 22/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import "SECategoriaBS.h"
#import "SEViewController.h"
#import "Reachability.h"
@interface SEAppDelegate : UIResponder <UIApplicationDelegate,CLLocationManagerDelegate>

@property (strong, nonatomic) UIWindow *window;
@property(nonatomic,assign) BOOL ios7;
@property(nonatomic,retain) CLLocationManager *locationManager;
@property(nonatomic) double latitude;
@property(nonatomic) double longitude;
@property(strong, nonatomic) NSString *databaseName;
@property(strong, nonatomic) NSString *databasePath;
@property(nonatomic,strong) SEViewController *homeController;
@property(nonatomic,assign) BOOL internetAvailable;
@property(nonatomic,assign) BOOL transferingData;
@property (nonatomic) Reachability *hostReachability;
@property (nonatomic) Reachability *internetReachability;
@property (nonatomic) Reachability *wifiReachability;
@end
