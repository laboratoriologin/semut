//
//  SENoticiaBS.h
//  semut
//
//  Created by Julio Rocha on 27/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SENoticiaBS : NSObject <NSURLConnectionDelegate> {
    NSMutableData *mutableData;
    NSMutableArray *resultado;
}

- (void)findNoticiasTransalvador:(NSMutableArray *) resultado;
- (void)findNoticiasTransalvadorEducacao:(NSMutableArray *) resultado;
- (void)findNoticiasSucom:(NSMutableArray *) resultado;
@property (nonatomic, copy) void (^completionHandler)(void);


@end
