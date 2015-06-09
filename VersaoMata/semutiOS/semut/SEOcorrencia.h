//
//  SEOcorrencia.h
//  semut
//
//  Created by Julio Rocha on 26/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SECategoria.h"
#import "SERegistro.h"
@interface SEOcorrencia : SERegistro

@property(nonatomic,retain) SECategoria *categoria;
@property(nonatomic,strong) NSString *texto;
@property(nonatomic,assign) double latitude;
@property(nonatomic,assign) double longitude;
@property(nonatomic,strong) NSData *imageData;
@property(nonatomic,strong) NSString *data;

@end
