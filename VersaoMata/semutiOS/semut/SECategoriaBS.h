//
//  SECategoriaBS.h
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SECategoriaBS : NSObject <NSURLConnectionDelegate>

-(NSMutableArray *) findCategoriasTransalvador;
-(NSMutableArray *) findCategoriasSucom;
-(void) downloadCategoria;

@end
