//
//  SEOcorrenciaBS.h
//  semut
//
//  Created by Julio Rocha on 09/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SEOcorrencia.h"
#import "SEOcorrenciaDAO.h"
#import <AFNetworking/AFNetworking.h>
@interface SEOcorrenciaBS : NSObject

@property SEOcorrencia *ocorrencia;
@property SEOcorrenciaDAO *ocorrenciaDAO;

-(void) createOcorrencia;
-(void) enviarOcorrenciasPendentes;

@end
