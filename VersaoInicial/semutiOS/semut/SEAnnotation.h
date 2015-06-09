//
//  SEAnnotation.h
//  semut
//
//  Created by Julio Rocha on 26/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <MapKit/MapKit.h>

@interface SEAnnotation : NSObject <MKAnnotation> {
    CLLocationCoordinate2D coordinate;
}



-(id)initWithCoordinate:(CLLocationCoordinate2D)coordinate;

@end
