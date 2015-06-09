//
//  SEUsuario.h
//  semut
//
//  Created by Julio Rocha on 27/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SEUsuario : NSObject

@property(nonatomic,assign) int codigo;
@property(nonatomic,strong) NSString *email;
@property(nonatomic,strong) NSString *nome;
@property(nonatomic,strong) NSString *senha;
@property(nonatomic,strong) NSString *telefone;

-(SEUsuario *) initWithDictionary:(NSDictionary *) dictionary;

@end
