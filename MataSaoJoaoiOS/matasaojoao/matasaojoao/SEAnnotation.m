//
//  SEAnnotation.m
//  semut
//
//  Created by Julio Rocha on 26/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEAnnotation.h"

@implementation SEAnnotation

@synthesize coordinate;

- (id)initWithCoordinate:(CLLocationCoordinate2D)coord {
    
	self = [super init];
	
    if (self != nil) {
        
        coordinate = coord;

    }
    
    return self;
    
}

- (NSString *)title {
	return @"Localização da ocorrência";
}

@end
