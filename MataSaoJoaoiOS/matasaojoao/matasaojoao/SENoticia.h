//
//  SENoticia.h
//  semut
//
//  Created by Julio Rocha on 27/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SERegistro.h"
@interface SENoticia : SERegistro

@property(nonatomic,strong) NSString * titulo;
@property(nonatomic,strong) NSString * data;
@property(nonatomic,strong) NSString * descricao;
@property(nonatomic,assign) BOOL educacaoTransito;
@end
