//
//  SENoticiaDAO.h
//  semut
//
//  Created by Julio Rocha on 19/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SENoticia.h"
#import "FMDatabase.h"
#import "SEDBUtil.h"
@interface SENoticiaDAO : NSObject

-(BOOL) inserir:(SENoticia *) obj grupo:(int) grupo;
-(BOOL) excluir:(SENoticia *) obj;
-(NSMutableArray *) getAllByGrupo:(int) grupo;
-(SENoticia *) get:(int) codigo;


@end
