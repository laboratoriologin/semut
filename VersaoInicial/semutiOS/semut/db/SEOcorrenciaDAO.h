//
//  SEOcorrenciaDAO.h
//  semut
//
//  Created by Julio Rocha on 06/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SEOcorrencia.h"
@interface SEOcorrenciaDAO : NSObject


-(BOOL) inserir:(SEOcorrencia *) obj grupo:(int) grupo;
-(BOOL) excluir:(SEOcorrencia *) obj;
-(NSMutableArray *) getAllByGrupo:(int) grupo;
-(SEOcorrencia *) get:(long) codigo;

@end
