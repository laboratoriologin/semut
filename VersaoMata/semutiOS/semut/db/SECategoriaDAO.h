//
//  SECategoriaDAO.h
//  semut
//
//  Created by Julio Rocha on 04/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SECategoria.h"
@interface SECategoriaDAO : NSObject

-(BOOL) inserir:(SECategoria *) obj;
-(NSMutableArray *) getAllByGrupo:(int) grupo;
-(BOOL) clear;

@end
