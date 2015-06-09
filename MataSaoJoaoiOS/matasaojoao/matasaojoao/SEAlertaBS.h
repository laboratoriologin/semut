//
//  SEAlertaBS.h
//  semut
//
//  Created by Julio Rocha on 10/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SEAlertaBS : NSObject <NSURLConnectionDelegate> {
    NSMutableData *mutableData;
    NSMutableArray *resultado;
}

-(void) findAlertas:(NSMutableArray *) _resultado;
@property (nonatomic, copy) void (^completionHandler)(void);

@end
