//
//  SEUsuario.m
//  semut
//
//  Created by Julio Rocha on 27/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEUsuario.h"

@implementation SEUsuario

-(SEUsuario *) initWithDictionary:(NSDictionary *) dictionary {
    
    self = [[SEUsuario alloc]init];
    
    if(self) {
        
        self.codigo = [[dictionary objectForKey:@"codigo"]intValue];
        
        self.nome = [dictionary objectForKey:@"nome"];

        self.email = [dictionary objectForKey:@"email"];
        
        self.telefone = [dictionary objectForKey:@"telefone"];
        
        self.senha = [dictionary objectForKey:@"senha"];
    }
    
    return self;
    
}

@end
