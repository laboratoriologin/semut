//
//  SEUsuarioBS.h
//  semut
//
//  Created by Julio Rocha on 12/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SEUsuario.h"
@interface SEUsuarioBS : NSObject <NSURLConnectionDelegate> {
    NSMutableData *mutableData;
    
}

-(void) authenticate:(SEUsuario *) usuario;
@property (nonatomic, copy) void (^completionHandler)(void);

@end
