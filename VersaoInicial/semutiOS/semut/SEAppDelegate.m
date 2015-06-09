//
//  SEAppDelegate.m
//  semut
//
//  Created by Julio Rocha on 22/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEAppDelegate.h"
#import <AFNetworking/AFNetworking.h>
#import "SEUtil.h"
@implementation SEAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    NSString *reqSysVer = @"7.0";
    NSString *currSysVer = [[UIDevice currentDevice] systemVersion];
    
    if ([currSysVer compare:reqSysVer options:NSNumericSearch] != NSOrderedAscending) {
        _ios7=YES;
    } else {
        _ios7=NO;
    }
    
    [[UINavigationBar appearance]setTintColor:[UIColor whiteColor]];
    
    [self initLocation];
    
    [self initDatabase];
    
    [[[SECategoriaBS alloc]init]downloadCategoria];
    
    UILocalNotification *localNotif = [launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey];
    
    if (localNotif) {
        
        [[UIApplication sharedApplication]cancelAllLocalNotifications];
        [SEUtil setBool:YES forKey:kBoolPushReceived];
    }
    
    
    [self requireTokenPush];
    
    
    
    
    return YES;
}


-(void) initLocation {
    _locationManager = [[CLLocationManager alloc] init];
    _locationManager.delegate = self;
    _locationManager.distanceFilter = kCLDistanceFilterNone;
    _locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    [_locationManager startUpdatingLocation];
}

-(void) requireTokenPush {
    [[UIApplication sharedApplication]
     registerForRemoteNotificationTypes:
     (UIRemoteNotificationTypeBadge |
      UIRemoteNotificationTypeSound |
      UIRemoteNotificationTypeAlert)];
}



- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation {
    _latitude= newLocation.coordinate.latitude;
    _longitude = newLocation.coordinate.longitude;
    
}

-(void) initDatabase {
    
    self.databaseName = @"semut.sqlite";
    
    NSArray *documentPaths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask, YES);
    
    NSString *documentDir = [documentPaths objectAtIndex:0];
    
    self.databasePath = [documentDir stringByAppendingPathComponent:self.databaseName];
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    if(![fileManager fileExistsAtPath:_databasePath]) {
        
        NSString *path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:self.databaseName];
        
        [fileManager copyItemAtPath:path toPath:_databasePath error:nil];
        
    }
    
    
}

- (void)application:(UIApplication*)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData*)deviceToken {
    
    NSString *tokenStr = [deviceToken description];
    
    tokenStr = [[[tokenStr stringByReplacingOccurrencesOfString:@"<" withString:@""] stringByReplacingOccurrencesOfString:@">" withString:@""] stringByReplacingOccurrencesOfString:@" " withString:@""];
    
    NSDictionary *parameters = @{@"token": tokenStr};
    
    if(![tokenStr isEqualToString:[SEUtil defaultForKey:kToken]]) {
        
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        
        [manager POST:[kUrlCadastroToken stringByAppendingFormat:@"&key_servlet=%@",[SEUtil servletKey]] parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
            
            NSDictionary *resultados = responseObject;
            
            NSString *resultado =[resultados objectForKey:@"status"];
            
            if([resultado isEqualToString:@"ok"]) {
                
                [SEUtil setDefault:tokenStr forKey:kToken];
                
            }
            
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            
        }];
        
    }
    
    
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
    
    if(application.applicationState == UIApplicationStateInactive) {
        
        if(_homeController) {
            
            [self pushToAlertController];
            
        }
    }
    
}


- (void)applicationWillResignActive:(UIApplication *)application
{
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    [[UIApplication sharedApplication] cancelAllLocalNotifications];
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    
}

-(void) pushToAlertController {
    
    [_homeController.navigationController popToRootViewControllerAnimated:NO];
    
    [_homeController performSegueWithIdentifier:@"sg_alerta" sender:nil];
    
}


@end
